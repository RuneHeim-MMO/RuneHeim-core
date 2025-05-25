package org.zuma.examplePlugin.domain.block.base;

import com.google.gson.Gson;
import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.Serializable;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeMetaId;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeType;
import org.zuma.examplePlugin.domain.item.SkillRequirement;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.base.EquipableItem;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.block.ResourceNodeGatheredEvent;
import org.zuma.examplePlugin.events.block.ResourceNodeRespawnedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ResourceNode extends AbstractInteractableBlock implements Serializable {
    private ResourceNodeType blockType;
    private ResourceNodeMetaId meta;
    private boolean isDepleted;

    public ResourceNode(AbstractInteractableBlockMeta meta) {
        super(meta);
        this.isDepleted = false;
    }

    private void initializeHologram() {
        List<String> hologramContent = new ArrayList<>(List.of(
                Colors.INTERACTABLE_ENTITY.asSpigotHex() + blockType.getLabel()
        ));

        blockType.getRequirements().forEach(req -> hologramContent.add(Colors.ACCENT.asSpigotHex() +
                "Req. " + req.getType().getSymbol() + " " + req.getMinimumLevel() + " Level"));

        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation(), hologramContent));
    }

    public void initializeBlockTypeFromManager(BlockLoader loader) {
        ResourceNodeType type = loader.getResourceNodeTypes().get(meta.resourceMetaId());

        if (type == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Could not initialize resource node! ResourceNodeType with ID " + meta.resourceMetaId() + " does not exist.");
            return;
        }

        this.blockType = type;
        initializeHologram();
        respawnBlock();
    }

    private void respawnBlock() {
        ResourceNodeRespawnedEvent event = new ResourceNodeRespawnedEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        spawn(blockType.getMaterial());
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
        if (!missingRequirements.isEmpty()) {
            SoundType.UI_FAIL.play(player.getPlayer());
            missingRequirements.forEach(req -> {
                player.getPlayer().sendMessage(Component.text(
                        "You need " + req.getType().getLabel() + " in order to gather " + blockType.getLabel() + "."
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
                startPlayerMiningAction(player, modifier.amount());
            });
            return;
        } else {
            startPlayerMiningAction(player, 0);
        }
    }


    private void startPlayerMiningAction(RunePlayer player, double modifier) {
        player.useBusyState((int) (blockType.getBaseGatheringDurationMillis() / modifier), blockType.getActionVerb() + " " + blockType.getLabel(),
                "Success!", blockType.getStepSound(), blockType.getGatherSuccessSound(), (a) -> {
                    onMine(player);
                });
    }

    private void onMine(RunePlayer player) {
        if (isDepleted) {
            return;
        }

        ResourceNodeGatheredEvent event = new ResourceNodeGatheredEvent(player, this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        spawn(Material.BEDROCK);
        player.addItemOrDrop(blockType.getDrop());
        SoundType.MINING_SUCCESS.play(player.getPlayer().getLocation());
        isDepleted = true;

        DHAPI.addHologramLine(this.getHologram(), new ItemStack(Material.BARRIER));
        DHAPI.setHologramLine(this.getHologram(), 0, Colors.DEPLETED_RESOURCE.asSpigotHex() + "❌ " + blockType.getLabel());

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            respawnBlock();
            isDepleted = false;

            DHAPI.removeHologramLine(this.getHologram(), this.getHologram().size() + 1);
            DHAPI.setHologramLine(this.getHologram(), 0, Colors.INTERACTABLE_ENTITY.asSpigotHex() + blockType.getLabel());
        }, blockType.getRespawnDurationSeconds() * 20L);
    }

    public void onPlayerBlockBreakEvent(RunePlayer player, BlockBreakEvent event) {
        event.setCancelled(true);
    }

    public void onPlayerBlockBreakProgressUpdateEvent(RunePlayer player, BlockBreakEvent event) {
        event.setCancelled(true);
    }

    public String serializeMeta() {
        return new Gson().toJson(this.getJsonMeta());
    }

    public void deserializeAndApplyMeta() {
        this.meta = new Gson().fromJson(this.getJsonMeta(), ResourceNodeMetaId.class);
    }
}
