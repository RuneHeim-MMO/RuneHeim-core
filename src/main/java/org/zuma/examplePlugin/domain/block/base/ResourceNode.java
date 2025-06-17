package org.zuma.examplePlugin.domain.block.base;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.ResourceNodeSchematicHandler;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeType;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.impl.EquipableItem;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.events.block.ResourceNodeGatheredEvent;
import org.zuma.examplePlugin.events.block.ResourceNodeRespawnedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ResourceNode extends AbstractInteractableBlock {
    private ResourceNodeType blockType;
    private boolean isDepleted;
    private final BlockManager manager;
    private final Location origin;
    private ItemStack dropItemStack = new ItemStack(Material.AIR);
    private boolean isRemoved = false;

    @Nullable
    private ResourceNodeSchematicHandler schematicHandler = null;


    public ResourceNode(BlockManager manager, AbstractInteractableBlockMeta meta) {
        super(meta, null, null);
        this.isDepleted = false;
        this.origin = meta.location();
        this.manager = manager;
    }

    private void initializeHologram() {
        List<String> hologramContent = new ArrayList<>(List.of(
                Colors.INTERACTABLE_ENTITY.asSpigotHex() + blockType.getLabel()
        ));

        blockType.getRequirements().forEach(req -> hologramContent.add(Colors.ACCENT.asSpigotHex() +
                "Req. §f" + req.type().getSymbol() + Colors.ACCENT.asSpigotHex() + " " + req.minimumLevel() + " Level"));


        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation(), hologramContent));
        DHAPI.addHologramLine(this.getHologram(), dropItemStack);

    }

    public void initializeBlock(int resourceTypeId, BlockLoader loader) {
        ResourceNodeType type = loader.getResourceNodeTypes().get(resourceTypeId);

        if (type == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Could not initialize resource node! ResourceNodeType with ID " + resourceTypeId + " does not exist.");
            return;
        }

        AbstractItem drop = RuneQuestPlugin.getItemRegistry().getItem(type.getDrop().getItemId());

        if (drop == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Could not initialize resource node! ResourceNodeType with ID " + resourceTypeId + " has an invalid drop item id.");
            return;
        }


        if (schematicHandler != null) {
            schematicHandler.updateNodeModel(this);
        }

        this.blockType = type;
        this.dropItemStack = drop.asItemStack();
        super.setMarkerType(type.getMarkerType());
        super.setMarkerLabel(type.getLabel());
        this.spawn(type.getMaterial());

        initializeHologram();
    }

    private void respawnBlock() {
        ResourceNodeRespawnedEvent event = new ResourceNodeRespawnedEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }


        this.spawn(blockType.getMaterial());

    }


    private void startGatheringAction(RunePlayer player, double modifier) {
        blockType.getStartSound().play(player);
        player.useBusyState((int) (blockType.getBaseGatheringDurationMillis() / modifier), blockType.getActionVerb() + " " + blockType.getLabel(),
                "", blockType.getStepSound(), blockType.getGatherSuccessSound(), (a) -> {
                    onGather(player);
                });
    }

    private void onGather(RunePlayer player) {
        if (isDepleted) {
            return;
        }

        ResourceNodeGatheredEvent event = new ResourceNodeGatheredEvent(player, this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        depleteResource();
        player.addItemOrDrop(blockType.getDrop());
        blockType.getXpReward().forEach(reward -> player.addShareableXp(reward.type(), reward.xp()));


    }

    public void depleteResource() {
        isDepleted = true;

        this.spawn(Material.BARRIER);

        DHAPI.setHologramLine(this.getHologram(), this.getHologram().size() + 1, "");
        DHAPI.setHologramLine(this.getHologram(), 0, Colors.DEPLETED_RESOURCE.asSpigotHex() + "❌ " + blockType.getLabel());

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            isDepleted = false;
            respawnBlock();

            DHAPI.setHologramLine(this.getHologram(), this.getHologram().size() + 1, dropItemStack);
            DHAPI.setHologramLine(this.getHologram(), 0, Colors.INTERACTABLE_ENTITY.asSpigotHex() + blockType.getLabel());
        }, blockType.getRespawnDurationSeconds() * 20L);
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
        return this.isDepleted;
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

            if (material.equals(Material.BARRIER)) {
                return;
            }
        }
        super.spawn(material);
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItem itemInHand) {
        // Cancel interaction if player is busy or the resource is depleted
        if (player.isBusy() || isDepleted) {
            event.setCancelled(true);
            return;
        }

        // Check if the player meets all skill requirements
        List<SkillRequirement> missingRequirements = blockType.getRequirements().stream()
                .filter(requirement -> !requirement.playerMeetsRequirement(player))
                .toList();

        // If any requirements are missing, notify player and cancel interaction
        if (!missingRequirements.isEmpty() && player.getPlayer().getGameMode() != GameMode.CREATIVE) {
            SoundType.UI_FAIL.play(player.getPlayer());
            missingRequirements.forEach(req -> {
                player.getPlayer().sendMessage(Component.text(
                        "You need " + req.minimumLevel() + " " + req.type().getLabel() + " in order to gather from " + blockType.getLabel() + "."
                ).color(Colors.ERROR.asColor()));
            });

            return;
        }

        // Ensure the item in hand is equipable and valid for gathering
        if (!(itemInHand instanceof EquipableItem gearItemInHand)) {
            SoundType.UI_FAIL.play(player.getPlayer());
            player.getPlayer().sendMessage(Component.text(blockType.getWrongToolMessage()).color(Colors.ERROR.asColor()));
            return;
        }

        // Determine if a specific tool modifier is required and attempt to retrieve it
        boolean hasRequiredModifier = blockType.getRequiredToolModifier() != null;
        Optional<Modifier> requiredToolModifier = gearItemInHand.getModifierByType(blockType.getRequiredToolModifier());

        // If a required modifier exists but the item lacks it, fail the interaction
        if (hasRequiredModifier && requiredToolModifier.isEmpty()) {
            SoundType.UI_FAIL.play(player.getPlayer());
            player.getPlayer().sendMessage(Component.text(blockType.getWrongToolMessage()).color(Colors.ERROR.asColor()));
            return;
        }

        // Start the mining action, using the modifier amount if present
        if (hasRequiredModifier) {
            requiredToolModifier.ifPresent(modifier -> {
                startGatheringAction(player, modifier.amount());
            });
            return;
        } else {
            startGatheringAction(player, 0);
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

        if (super.getHologram() != null) {
            DHAPI.removeHologram(super.getHologram().getId());
        }

    }

}
