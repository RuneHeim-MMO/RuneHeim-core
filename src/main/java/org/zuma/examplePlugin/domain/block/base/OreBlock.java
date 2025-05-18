package org.zuma.examplePlugin.domain.block.base;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.constants.SoundType;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.constants.OreType;
import org.zuma.examplePlugin.domain.item.base.CustomItem;
import org.zuma.examplePlugin.domain.item.base.GearItem;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.Optional;

public class OreBlock extends InteractableBlock {
    private final OreType oreType;
    private boolean isDepleted;

    public OreBlock(OreType type, Location location) {
        super(BlockType.ORE, location);
        this.oreType = type;
        this.isDepleted = false;
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, CustomItem itemInHand) {

        if (player.isBusy() || isDepleted) {
            event.setCancelled(true);
            return;
        }

        if (oreType.getMinLevel() > player.getSkill(SkillType.MINING).getLevel()) {
            SoundType.UI_FAIL.play(player.getPlayer());
            player.getPlayer().sendMessage(Component.text("Your need mining level of " + oreType.getMinLevel() + " in order to mine " + oreType.getLabel() + ".").color(Colors.ERROR.asColor()));
            return;
        }

        if (!(itemInHand instanceof GearItem gearItemInHand)) {
            SoundType.UI_FAIL.play(player.getPlayer());
            player.getPlayer().sendMessage(Component.text("You need a pickaxe to mine that.").color(Colors.ERROR.asColor()));
            return;
        }

        Optional<Modifier> miningBoost = gearItemInHand.getModifierByType(ModifierType.MINING_BOOST);

        if (miningBoost.isEmpty()) {
            SoundType.UI_FAIL.play(player.getPlayer());
            player.getPlayer().sendMessage(Component.text("You need a pickaxe to mine that.").color(Colors.ERROR.asColor()));
            return;
        }


        miningBoost.ifPresent(modifier -> {
            player.useBusyState((int) (oreType.getBaseMiningDuration() / modifier.amount()), "Mining " + oreType.getLabel(),
                    "Success!", SoundType.MINING_STEP, SoundType.MINING_SUCCESS, (a) -> {
                        onMine(player);
                    });

        });
    }

    private void onMine(RunePlayer player) {
        if (isDepleted) {
            return;
        }

        player.addItemOrDrop(oreType.getDrop());
        spawn(Material.BEDROCK);
        SoundType.MINING_SUCCESS.play(player.getPlayer().getLocation());
        isDepleted = true;

        DHAPI.addHologramLine(this.getHologram(), new ItemStack(Material.BARRIER));
        DHAPI.setHologramLine(this.getHologram(), 0, Colors.DEPLETED_RESOURCE.asSpigotHex() + "❌ " + oreType.getLabel());

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            spawn(oreType.getMaterial());
            isDepleted = false;

            DHAPI.removeHologramLine(this.getHologram(), this.getHologram().size() + 1);
            DHAPI.setHologramLine(this.getHologram(), 0, Colors.INTERACTABLE_ENTITY.asSpigotHex() + oreType.getLabel());
        }, oreType.getRespawnDurationSeconds() * 20L);
    }

    public void onPlayerBlockBreakEvent(RunePlayer player, BlockBreakEvent event) {
        event.setCancelled(true);
    }

    public void onPlayerBlockBreakProgressUpdateEvent(RunePlayer player, BlockBreakEvent event) {
        event.setCancelled(true);
    }
}
