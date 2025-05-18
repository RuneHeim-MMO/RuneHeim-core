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
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.constants.SoundType;
import org.zuma.examplePlugin.domain.item.SkillRequirement;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.base.EquipableItem;
import org.zuma.examplePlugin.domain.item.interfaces.WithMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;
import java.util.Optional;

public class ResourceNode extends InteractableBlock implements WithMeta {
    private ResourceNodeMeta meta;
    private boolean isDepleted;

    public ResourceNode(InteractableBlockMeta meta) {
        super(meta);
        this.isDepleted = false;
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItem itemInHand) {
        // Cancel interaction if player is busy or the resource is depleted
        if (player.isBusy() || isDepleted) {
            event.setCancelled(true);
            return;
        }

        // Check if the player meets all skill requirements
        List<SkillRequirement> missingRequirements = meta.getRequirements().stream()
                .filter(requirement -> !requirement.playerMeetsRequirement(player))
                .toList();

        // If any requirements are missing, notify player and cancel interaction
        if (!missingRequirements.isEmpty()) {
            SoundType.UI_FAIL.play(player.getPlayer());
            missingRequirements.forEach(req -> {
                player.getPlayer().sendMessage(Component.text(
                        "You need " + req.getType().getLabel() + " in order to gather " + meta.getLabel() + "."
                ).color(Colors.ERROR.asColor()));
            });

            return;
        }

        // Ensure the item in hand is equipable and valid for gathering
        if (!(itemInHand instanceof EquipableItem gearItemInHand)) {
            SoundType.UI_FAIL.play(player.getPlayer());
            player.getPlayer().sendMessage(Component.text(meta.getWrongToolMessage()).color(Colors.ERROR.asColor()));
            return;
        }

        // Determine if a specific tool modifier is required and attempt to retrieve it
        boolean hasRequiredModifier = meta.getRequiredToolModifier() != null;
        Optional<Modifier> requiredToolModifier = gearItemInHand.getModifierByType(meta.getRequiredToolModifier());

        // If a required modifier exists but the item lacks it, fail the interaction
        if (hasRequiredModifier && requiredToolModifier.isEmpty()) {
            SoundType.UI_FAIL.play(player.getPlayer());
            player.getPlayer().sendMessage(Component.text(meta.getWrongToolMessage()).color(Colors.ERROR.asColor()));
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
        player.useBusyState((int) (meta.getBaseGatheringDurationMillis() / modifier), meta.getActionVerb() + " " + meta.getLabel(),
                "Success!", meta.getStepSound(), meta.getGatherSuccessSound(), (a) -> {
                    onMine(player);
                });
    }

    private void onMine(RunePlayer player) {
        if (isDepleted) {
            return;
        }

        player.addItemOrDrop(meta.getDrop());
        spawn(Material.BEDROCK);
        SoundType.MINING_SUCCESS.play(player.getPlayer().getLocation());
        isDepleted = true;

        DHAPI.addHologramLine(this.getHologram(), new ItemStack(Material.BARRIER));
        DHAPI.setHologramLine(this.getHologram(), 0, Colors.DEPLETED_RESOURCE.asSpigotHex() + "❌ " + meta.getLabel());

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            spawn(meta.getMaterial());
            isDepleted = false;

            DHAPI.removeHologramLine(this.getHologram(), this.getHologram().size() + 1);
            DHAPI.setHologramLine(this.getHologram(), 0, Colors.INTERACTABLE_ENTITY.asSpigotHex() + meta.getLabel());
        }, meta.getRespawnDurationSeconds() * 20L);
    }

    public void onPlayerBlockBreakEvent(RunePlayer player, BlockBreakEvent event) {
        event.setCancelled(true);
    }

    public void onPlayerBlockBreakProgressUpdateEvent(RunePlayer player, BlockBreakEvent event) {
        event.setCancelled(true);
    }

    public String serializeMeta() {
        return new Gson().toJson(meta);
    }

    public void deserializeAndApplyMeta() {
        this.meta = new Gson().fromJson(this.getJsonMeta(), ResourceNodeMeta.class);
    }
}
