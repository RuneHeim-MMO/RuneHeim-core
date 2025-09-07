package org.zuma.examplePlugin.domain.block.impl;

import com.sk89q.worldedit.bukkit.BukkitPlayer;
import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Lectern;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.POIMarkerable;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.actions.ActionParams;
import org.zuma.examplePlugin.domain.player.actions.ProgressBarAction;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Altar extends AbstractInteractableBlock implements POIMarkerable {
    public Altar(AbstractInteractableBlockMeta meta) {
        super(meta, null, null);
    }

    private void initializeHologram() {
        List<String> hologramContent = new ArrayList<>(List.of(
                Color.INTERACTABLE_ENTITY.asSpigotHex() + "Altar"
        ));

        hologramContent.add("Press " + Color.ACCENT.asSpigotHex() + "[RIGHT CLICK] §fto pray");
        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation(), hologramContent));
    }

    @Override
    public void spawn(Material material) {
        initializeHologram();
        super.spawn(this.getType().getMaterial());

        final Block block = super.getLocation().getBlock();
        final Location center = block.getLocation().add(0.5, 0.6, 0.5);
        final World world = block.getWorld();
        // Ring parameters
        double radius = 1.2;
        int points = 2;

        new BukkitRunnable() {
            private int tickCounter = 0;

            @Override
            public void run() {
                if (!block.getChunk().isLoaded()) {
                    return;
                }

                if (tickCounter > 1_000_000) {
                    tickCounter = 0;
                }

                if (block.isEmpty()) {
                    cancel();
                    return;
                }

                // rotating ring of END_ROD particles
                for (int i = 0; i < points; i++) {
                    double angle = (2 * Math.PI * i / points) + (tickCounter * 0.15); // rotating effect
                    double x = center.getX() + Math.cos(angle) * radius;
                    double z = center.getZ() + Math.sin(angle) * radius;
                    double y = center.getY() - 0.5;

                    world.spawnParticle(Particle.END_ROD, x, y, z, 1, 0, 0, 0, 0);
                }

                // some random sparkles above the portal
                for (int i = 0; i < 6; i++) {
                    double rx = center.getX() + (Math.random() - 0.5) * 1.6;
                    double rz = center.getZ() + (Math.random() - 0.5) * 1.6;
                    double ry = center.getY() + Math.random() * 1.2;
                    world.spawnParticle(Particle.CRIT, rx, ry, rz, 1, 0, 0, 0, 0);
                }

                tickCounter++;
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, 5L);
    }


    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {
        event.setCancelled(true);
        int maxPrayerLevel = player.getSkill(SkillType.PRAYER).getLevelWithBonuses();

        if (player.getPrayerEnergy() == maxPrayerLevel) {
            HudService.sendFailMessage(player, Component.text("Your prayer is already full!"));
            return;
        }

        ActionParams params = new ActionParams.Builder()
                .durationMs(3000)
                .actionLabel("Praying...")
                .successActionLabel("Prayer restored!")
                .stepSound(SoundType.ALTAR_PRAYING_LAYER_1)
                .stepSoundEveryNth(3)
                .successSound(SoundType.ALTAR_PRAYING_LAYER_2)
                .onSuccess(() -> {
                    player.setPrayerEnergy(maxPrayerLevel);

                    SoundType.ALTAR_PRAYING_LAYER_2.play(player.asBukkitPlayer().getLocation(), null, 0.05f);
                    SoundType.ALTAR_PRAYING_LAYER_2.play(player.asBukkitPlayer().getLocation(), null, 0.10f);
                    SoundType.ALTAR_PRAYING_LAYER_2.play(player.asBukkitPlayer().getLocation(), null, 0.15f);
                    SoundType.ALTAR_PRAYING_LAYER_2.play(player.asBukkitPlayer().getLocation(), null, 0.20f);
                    SoundType.ALTAR_PRAYING_LAYER_2.play(player.asBukkitPlayer().getLocation(), null, 0.25f);
                })
                .build();

        player.startAction(new ProgressBarAction(false, false), params);
    }

    public Location getLocation() {
        return super.getLocation();
    }

    @Nullable
    public MarkerType getMarkerType() {
        return MarkerType.ALTAR;
    }

    @Nullable
    public String getMarkerLabel() {
        return "Altar";
    }
}
