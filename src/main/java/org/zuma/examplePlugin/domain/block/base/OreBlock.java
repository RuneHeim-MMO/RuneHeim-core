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
import org.zuma.examplePlugin.domain.item.base.CustomItem;
import org.zuma.examplePlugin.domain.item.base.EquipableItem;
import org.zuma.examplePlugin.domain.item.interfaces.WithAdditionalMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.Optional;

public class OreBlock extends InteractableBlock implements WithAdditionalMeta {
    private OreBlockMeta oreMeta;
    private boolean isDepleted;

    public OreBlock(InteractableBlockMeta meta) {
        super(meta);
        this.isDepleted = false;
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, CustomItem itemInHand) {

        if (player.isBusy() || isDepleted) {
            event.setCancelled(true);
            return;
        }

        if (oreMeta.getMinLevel() > player.getSkill(SkillType.MINING).getLevel()) {
            SoundType.UI_FAIL.play(player.getPlayer());
            player.getPlayer().sendMessage(Component.text("Your need mining level of " + oreMeta.getMinLevel() + " in order to mine " + oreMeta.getLabel() + ".").color(Colors.ERROR.asColor()));
            return;
        }

        if (!(itemInHand instanceof EquipableItem gearItemInHand)) {
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
            player.useBusyState((int) (oreMeta.getBaseMiningDuration() / modifier.amount()), "Mining " + oreMeta.getLabel(),
                    "Success!", SoundType.MINING_STEP, SoundType.MINING_SUCCESS, (a) -> {
                        onMine(player);
                    });

        });
    }

    private void onMine(RunePlayer player) {
        if (isDepleted) {
            return;
        }

        player.addItemOrDrop(oreMeta.getDrop());
        spawn(Material.BEDROCK);
        SoundType.MINING_SUCCESS.play(player.getPlayer().getLocation());
        isDepleted = true;

        DHAPI.addHologramLine(this.getHologram(), new ItemStack(Material.BARRIER));
        DHAPI.setHologramLine(this.getHologram(), 0, Colors.DEPLETED_RESOURCE.asSpigotHex() + "❌ " + oreMeta.getLabel());

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            spawn(oreMeta.getMaterial());
            isDepleted = false;

            DHAPI.removeHologramLine(this.getHologram(), this.getHologram().size() + 1);
            DHAPI.setHologramLine(this.getHologram(), 0, Colors.INTERACTABLE_ENTITY.asSpigotHex() + oreMeta.getLabel());
        }, oreMeta.getRespawnDurationSeconds() * 20L);
    }

    public void onPlayerBlockBreakEvent(RunePlayer player, BlockBreakEvent event) {
        event.setCancelled(true);
    }

    public void onPlayerBlockBreakProgressUpdateEvent(RunePlayer player, BlockBreakEvent event) {
        event.setCancelled(true);
    }

    public String serializeMeta() {
        return new Gson().toJson(oreMeta);
    }

    public void deserializeAndApplyMeta(String meta) {
        oreMeta = new Gson().fromJson(meta, OreBlockMeta.class);
    }
}
