package org.zuma.examplePlugin.domain.block.impl;

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
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.ResourceNodeSchematicHandler;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeType;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
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
    private ItemStack displayItemStack;
    private boolean isRemoved;
    private boolean displayResultInHologram;

    @Nullable
    private ResourceNodeSchematicHandler schematicHandler;


    public ResourceNode(BlockManager manager, AbstractInteractableBlockMeta meta) {
        super(meta, null, null);
        this.displayItemStack = new ItemStack(Material.AIR);
        this.isRemoved = false;
        this.isDepleted = false;
        this.displayResultInHologram = false;
        this.schematicHandler = null;
        this.origin = meta.location();
        this.manager = manager;
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
        if (displayResultInHologram) DHAPI.addHologramLine(this.getHologram(), displayItemStack);

    }

    public void initializeBlock(int resourceTypeId, BlockLoader loader) {
        ResourceNodeType type = loader.getResourceNodeTypes().get(resourceTypeId);

        if (type == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Could not initialize resource node! ResourceNodeType with ID " + resourceTypeId + " does not exist.");
            return;
        }

        AbstractItemType drop = RuneQuestPlugin.getItemRegistry().getType(type.getDrop().getItemId());

        if (drop == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Could not initialize resource node! ResourceNodeType with ID " + resourceTypeId + " has an invalid drop item id.");
            return;
        }


        if (schematicHandler != null) {
            schematicHandler.updateNodeModel(this);
        }

        this.blockType = type;
        this.displayItemStack = drop.createItemStackOfType();
        this.displayResultInHologram = type.isDisplayResultInHologram();
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
        if (blockType.getStartSound() != null) blockType.getStartSound().play(player, true);

        player.useBusyState((int) (blockType.getBaseGatheringDurationMillis() / modifier), blockType.getActionVerb() + " " + blockType.getLabel(),
                "", blockType.getStepSound(), blockType.getGatherSuccessSound(), () -> onGather(player), null);
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
        player.giveItemOrDrop(blockType.getDrop());
        blockType.getXpReward().forEach(reward -> player.distributeXp(reward.type(), reward.xp()));


    }

    public void depleteResource() {
        isDepleted = true;

        this.spawn(Material.AIR);

        if (displayResultInHologram) DHAPI.setHologramLine(this.getHologram(), this.getHologram().size() + 1, "");
        DHAPI.setHologramLine(this.getHologram(), 0, Color.DEPLETED_RESOURCE.asSpigotHex() + "❌ " + blockType.getLabel());

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            isDepleted = false;
            respawnBlock();

            if (displayResultInHologram)
                DHAPI.setHologramLine(this.getHologram(), this.getHologram().size() + 1, displayItemStack);

            DHAPI.setHologramLine(this.getHologram(), 0, Color.INTERACTABLE_ENTITY.asSpigotHex() + blockType.getLabel());
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

            if (material.equals(Material.AIR)) {
                return;
            }
        }
        super.spawn(material);
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {
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
        boolean requiresTool = requiredModifierType != null;

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

            // Start action using the modifier’s amount
            startGatheringAction(player, requiredToolModifier.get().amount());
        } else {
            // No tool is required, just start the action with default amount
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

        if (super.getHologram() != null) {
            DHAPI.removeHologram(super.getHologram().getId());
        }

    }

}
