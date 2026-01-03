package org.zuma.examplePlugin.domain.block.impl;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.handlers.game.BotVerificationHandler;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.ResourceNodeSchematicHandler;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeType;
import org.zuma.examplePlugin.domain.cooldown.CooldownHandler;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.PerfectStrikeEffect;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.modifier.scopes.server.ServerModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.actions.ActionParams;
import org.zuma.examplePlugin.domain.player.actions.ProgressBarAction;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.events.block.ResourceNodeGatheredEvent;
import org.zuma.examplePlugin.events.block.ResourceNodeRespawnedEvent;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;

public class ResourceNode extends AbstractInteractableBlock {
    public static Double PERFECT_STRIKE_CHANCE = 2.5;

    private final Random random = new Random();
    private final BlockManager manager;
    private final Location origin;
    private final CooldownHandler perfectStrikeCooldownHandler; // To prevent interruption abuse;
    private ResourceNodeType blockType;
    private int deposit;
    private ItemStack displayItemStack;
    private boolean isRemoved, displayResultInHologram, requiresTool;
    private long lastDepositUpdateTimestamp;
    @Nullable
    private ResourceNodeSchematicHandler schematicHandler;

    private Integer holoResultIndex, holoDepositIndex;

    public ResourceNode(BlockManager manager, AbstractInteractableBlockMeta meta) {
        super(meta, null, null);
        this.displayItemStack = new ItemStack(Material.AIR);
        this.isRemoved = false;
        this.displayResultInHologram = false;
        this.schematicHandler = null;
        this.origin = meta.location();
        this.manager = manager;
        this.perfectStrikeCooldownHandler = new CooldownHandler();
    }

    @Override
    public String getDescription() {
        List<String> description = new ArrayList<>();

        if (!blockType.getRequirements().isEmpty()) {
            description.add("<br/><b>Requirements:</b>");
            blockType.getRequirements().forEach(req -> description.add(req.minimumLevel() + " " + req.type().getLabel()));
        }

        description.add(" ");
        description.add("<b>Gather: </b>" + (blockType.getBaseGatheringDurationMillis() / 1000.0) + " sec");
        description.add("<b>Max Deposit: </b> " + blockType.getMaxDeposit());
        description.add("<b>Respawn: </b>" + blockType.getRespawnDurationSeconds() + " sec");
        description.add("<b>Tool Req.: </b>" + (requiresTool ? "Yes" : "No"));

        return String.join("<br/>", description);
    }

    private void initializeHologram() {

        List<String> hologramContent = new ArrayList<>(List.of(
                Color.INTERACTABLE_ENTITY.asSpigotHex() + blockType.getLabel()
        ));

        blockType.getRequirements().forEach(req -> hologramContent.add(Color.ACCENT.asSpigotHex() +
                "Req. §f" + req.type().getSymbol() + Color.ACCENT.asSpigotHex() + " " + req.minimumLevel() + " Level"));

        if (blockType.getRequirements().isEmpty()) {
            hologramContent.add("");
        }

        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation(), hologramContent));
        int index = hologramContent.size();

        if (displayResultInHologram) {
            DHAPI.addHologramLine(this.getHologram(), displayItemStack);
            holoResultIndex = index++;
        }

        if (this.getNodeType().getMaxDeposit() > 1) {
            DHAPI.addHologramLine(this.getHologram(), "§7" + this.deposit + "x");
            holoDepositIndex = index++;
        }

    }

    public void tick() {
        long now = System.currentTimeMillis();
        if (isRemoved) return;

        if (now - lastDepositUpdateTimestamp < blockType.getRespawnDurationSeconds() * 1000L)
            return;

        if (deposit >= blockType.getMaxDeposit()) return;

        if (this.deposit == 0) {
            ResourceNodeRespawnedEvent event = new ResourceNodeRespawnedEvent(this);
            Bukkit.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }

            if (schematicHandler != null) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    schematicHandler.updateNodeModel(this);
                    this.spawn(blockType.getMaterial());
                });
            } else {
                this.spawn(blockType.getMaterial());
            }
        }

        this.lastDepositUpdateTimestamp = now;
        this.deposit++;
        updateHologram();
    }

    public void initializeBlock(int resourceTypeId, BlockLoader loader) {
        ResourceNodeType type = loader.getResourceNodeTypes().get(resourceTypeId);

        if (type == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Could not initialize resource node! ResourceNodeType with ID " + resourceTypeId + " does not exist.");
            return;
        }

        AbstractItemType drop = RuneQuestPlugin.getItemRegistry().getType(type.getDisplayItemStackID().getItemId());

        if (drop == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Could not initialize resource node! ResourceNodeType with ID " + resourceTypeId + " has an invalid drop item id.");
            return;
        }

        this.blockType = type;
        this.displayItemStack = drop.createItemStackOfType();
        this.displayResultInHologram = type.isDisplayResultInHologram();
        this.requiresTool = type.getRequiredToolModifier() != null;
        this.deposit = Math.max(1, type.getStartingDeposit());

        if (type.getStartingDeposit() <= 0) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Resource Node " + type.getLabel() + " initialized with 0 starting deposit. Setting deposit to 1 to avoid spawn issues.");
        }

        super.setMarkerType(type.getMarkerType());
        super.setMarkerLabel(type.getLabel());

        this.spawn(type.getMaterial());
        initializeHologram();
    }

    private boolean rollPerfectStrikeChange(RunePlayer player) {
        if (blockType.getBaseGatheringDurationMillis() < 1000) return false;

        // Cooldown player perfect strike rolls on roll, cd expires base duration * 1.5
        if (this.perfectStrikeCooldownHandler.isCooldownInProgress(player)) return false;
        this.perfectStrikeCooldownHandler.startCooldown(player, (long) (this.blockType.getBaseGatheringDurationMillis() * 1.5));

        // Perfect strike roll
        double randomDouble = Math.random() * 100;
        boolean isPerfectStrikeBombActive = RuneQuestPlugin.getServerModifierHandler().isModifierActive(ServerModifierType.INCREASED_PERFECT_STRIKE);
        boolean isPerfectStrike = randomDouble < (isPerfectStrikeBombActive ? PERFECT_STRIKE_CHANCE * 6 : PERFECT_STRIKE_CHANCE); // 6x chance if bomb is active

        if (isPerfectStrike) {
            SoundType stepSound = blockType.getStepSound();
            EffectHandler.playAt(this.getLocation().clone().add(0.5, 0, 0.5), new PerfectStrikeEffect(player));
            AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.PERFECT_STRIKES, 1);

            new BukkitRunnable() {
                int iterations = 0;

                public void run() {
                    if (iterations >= 3) cancel();
                    if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) cancel();

                    if (stepSound != null) stepSound.play(player, true);
                    SoundType.PERFECT_STRIKE_1.play(player, true);

                    iterations++;
                }
            }.runTaskTimer(RuneQuestPlugin.getInstance(), 0, 2);


            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) return;

                SoundType successSound = blockType.getGatherSuccessSound();
                if (successSound != null) successSound.play(player, true);
                SoundType.PERFECT_STRIKE_2.play(player, true);

                onGather(player, 50);
            }, 6L);

            return true;
        }

        return false;
    }

    private void startGatheringAction(RunePlayer player, double modifier) {
        if (!BotVerificationHandler.canStartAction(player)) {
            return;
        }

        if (blockType.getStartSound() != null) blockType.getStartSound().play(player, true);

        this.lastDepositUpdateTimestamp = System.currentTimeMillis();

        if (rollPerfectStrikeChange(player)) {
            return; // If is perfect strike, handle it in rollPerfectStrikeChange
        }

        ActionParams params = new ActionParams.Builder()
                .durationMs((int) (blockType.getBaseGatheringDurationMillis() / modifier))
                .actionLabel(blockType.getActionVerb() + " " + blockType.getLabel())
                .successActionLabel("")
                .stepSound(blockType.getStepSound())
                .successSound(blockType.getGatherSuccessSound())
                .onSuccess(() -> onGather(player, 0))
                .onFailure(null)
                .build();

        player.queueOrStartAction(new ProgressBarAction(true, true), params, false);
    }

    public void onGather(RunePlayer player, double baseGatheringLuckPercentage) {
        if (deposit <= 0) {
            return;
        }

        ResourceNodeGatheredEvent event = new ResourceNodeGatheredEvent(player, this);
        AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.NODES_GATHERED, 1);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        this.deposit--;

        if (isDepleted()) {
            this.spawn(Material.AIR);

            if (schematicHandler != null) {
                schematicHandler.updateNodeModel(this);
            }
        }

        updateHologram();
        rollDropTableLoot(player, baseGatheringLuckPercentage);
        blockType.getXpReward().forEach(reward -> player.distributeXp(reward.type(), reward.xp()));
        BotVerificationHandler.logSuccessfulAction(player);
    }

    private void rollDropTableLoot(RunePlayer player, double baseGatheringLuckPercentage) {
        Modifier gatheringLuck = player.getModifiers().get(ModifierType.GATHERING_LUCK);
        boolean isResourceNodeBombActive = RuneQuestPlugin.getServerModifierHandler().isModifierActive(ServerModifierType.INCREASED_RESOURCE_DROPS);
        double gatheringLuckPercentage = baseGatheringLuckPercentage + (gatheringLuck != null ? gatheringLuck.amount() : 0);

        if (isResourceNodeBombActive) {
            gatheringLuckPercentage += 50; // Flat 50% increase if bomb is active
        }

        List<ItemRecord> gatheringDrops = blockType.rollGatheringDrops(player);

        // Do not allow double loot for items that don't require tools.
        if (random.nextDouble() * 100 < gatheringLuckPercentage && requiresTool) {
            gatheringDrops.addAll(blockType.rollGatheringDrops(player));
            SoundType.LUCKY_DROP_TABLE_SECOND_ROLL.play(player, true);
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.text("2x Loot!", Color.ACCENT.asColor()),
                    Component.empty(),
                    Title.Times.times(Duration.ofSeconds(0, 5), Duration.ofSeconds(2), Duration.ofMillis(500))
            ));
        }

        gatheringDrops.forEach(player::giveItemOrDrop);

    }

    private void updateHologram() {


        if (isDepleted()) {
            if (holoDepositIndex != null) {
                DHAPI.setHologramLine(this.getHologram(), holoDepositIndex, "");
            }
            if (holoResultIndex != null) {
                DHAPI.setHologramLine(this.getHologram(), holoResultIndex, "");
            }
            DHAPI.setHologramLine(this.getHologram(), 0, Color.DEPLETED_RESOURCE.asSpigotHex() + "❌ " + blockType.getLabel());
        } else {
            if (holoDepositIndex != null) {
                DHAPI.setHologramLine(this.getHologram(), holoDepositIndex, "§7" + this.deposit + "x");
            }
            if (holoResultIndex != null) {
                DHAPI.setHologramLine(this.getHologram(), holoResultIndex, displayItemStack);
            }

            DHAPI.setHologramLine(this.getHologram(), 0, Color.INTERACTABLE_ENTITY.asSpigotHex() + blockType.getLabel());
        }
    }


    public void onPlayerBlockBreakEvent(RunePlayer player, BlockBreakEvent event) {
        event.setCancelled(true);
    }

    public void setSchematics(@Nullable String schematicName, @Nullable String depletedSchematic) {
        if (schematicName == null && depletedSchematic == null) {
            return;
        }
        this.schematicHandler = new ResourceNodeSchematicHandler(schematicName, depletedSchematic);
    }

    public boolean isDepleted() {
        return this.deposit == 0;
    }

    public Location getOrigin() {
        return origin;
    }

    public BlockManager getManager() {
        return manager;
    }

    public ResourceNodeType getNodeType() {
        return blockType;
    }

    @Override
    public void spawn(Material material) {
        if (isRemoved) {
            return;
        }

        if (schematicHandler != null) {
            schematicHandler.updateNodeModel(this);
        }
        super.spawn(material);
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {
        // Cancel interaction if player is busy or the resource is depleted
        if (player.hasPendingActions() || isDepleted()) {
            event.setCancelled(true);
            return;
        }

        // Check if the player meets all skill requirements
        List<SkillRequirement> missingRequirements = blockType.getRequirements().stream()
                .filter(requirement -> !requirement.playerMeetsRequirement(player))
                .toList();

        // If any requirements are missing, notify player and cancel interaction
        if (!missingRequirements.isEmpty() && player.asBukkitPlayer().getGameMode() != GameMode.CREATIVE) {
            missingRequirements.forEach(req -> {
                HudService.sendFailMessage(player, Component.text(
                        "You need " + req.minimumLevel() + " " + req.type().getLabel() + " in order to gather from " + blockType.getLabel() + "."
                ));

            });

            return;
        }


        // Check if a tool modifier is required
        ModifierType requiredModifierType = blockType.getRequiredToolModifier();

        if (requiresTool) {
            // If a tool is required, ensure the item in hand is a valid handheld equipable
            if (!(itemInHand instanceof EquipableItemType gearItemInHand &&
                    gearItemInHand.getEquipableType().equals(EquipableType.HANDHELD))) {
                HudService.sendFailMessage(player, Component.text(blockType.getWrongToolMessage()));
                return;
            }

            // Check if the held item has the required modifier
            Optional<Modifier> requiredToolModifier = gearItemInHand.getModifierByType(requiredModifierType);

            if (requiredToolModifier.isEmpty()) {
                HudService.sendFailMessage(player, Component.text(blockType.getWrongToolMessage()));
                return;
            }

            // Start actions using the modifier’s amount
            startGatheringAction(player, requiredToolModifier.get().amount());
        } else {
            // No tool is required, just start the actions with default amount
            startGatheringAction(player, 1);
        }
    }

    @Override
    public void onDisable() {
        isRemoved = true;

        if (this.schematicHandler != null && this.blockType != null) {
            this.schematicHandler.cleanUp();
        } else {
            super.getLocation().getBlock().setType(Material.AIR);
        }

        super.onDisable();
    }

}
