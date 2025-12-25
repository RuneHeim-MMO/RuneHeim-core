package org.zuma.examplePlugin.domain.block.impl;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.POIMarkerable;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
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

    private static double[][] precomputeRing(int points, double radius) {
        double[][] ring = new double[points][2];
        double step = 2 * Math.PI / points;

        for (int i = 0; i < points; i++) {
            ring[i][0] = i * step;   // base angle
            ring[i][1] = radius;     // constant radius (can vary if needed)
        }

        return ring;
    }

    private void initializeHologram() {
        List<String> hologramContent = new ArrayList<>(List.of(
                Color.INTERACTABLE_ENTITY.asSpigotHex() + "Altar"
        ));

        hologramContent.add("Press " + Color.ACCENT.asSpigotHex() + "[RIGHT CLICK] §fto pray");
        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation().clone().add(0, 0.5, 0), hologramContent));
    }

    @Override
    public void spawn(Material material) {
        initializeHologram();
        super.spawn(this.getType().getMaterial());

        final Block block = super.getLocation().getBlock();
        final Location center = block.getLocation().add(0.5, 0.6, 0.5);
        final World world = block.getWorld();
        final double[][] RING_POS = precomputeRing(2, 1.2);

        new BukkitRunnable() {
            private int tickCounter = 0;

            @Override
            public void run() {
                if (!block.getChunk().isLoaded() || block.isEmpty()) {
                    cancel();
                    return;
                }

                double rotation = tickCounter * 0.15;

                world.spawnParticle(Particle.CRIT, center.getX(), center.getY() + 1, center.getZ(), 1, 0, 0, 0, 0);

                for (double[] pos : RING_POS) {
                    double angle = pos[0] + rotation;
                    double x = center.getX() + Math.cos(angle) * pos[1];
                    double z = center.getZ() + Math.sin(angle) * pos[1];
                    world.spawnParticle(Particle.END_ROD, x, center.getY() - 0.5, z, 1, 0, 0, 0, 0);
                }

                tickCounter++;
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, 10L);
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
                    AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.ALTAR_PRAY_AMOUNTS, 1);
                    SoundType.ALTAR_PRAYING_LAYER_2.play(player.asBukkitPlayer().getLocation(), null, 0.05f);
                    SoundType.ALTAR_PRAYING_LAYER_2.play(player.asBukkitPlayer().getLocation(), null, 0.10f);
                    SoundType.ALTAR_PRAYING_LAYER_2.play(player.asBukkitPlayer().getLocation(), null, 0.15f);
                    SoundType.ALTAR_PRAYING_LAYER_2.play(player.asBukkitPlayer().getLocation(), null, 0.20f);
                    SoundType.ALTAR_PRAYING_LAYER_2.play(player.asBukkitPlayer().getLocation(), null, 0.25f);
                })
                .build();

        player.queueOrStartAction(new ProgressBarAction(false, false), params, false);
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
