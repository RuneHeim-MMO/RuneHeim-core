package org.zuma.examplePlugin.domain.effect.effects;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;

public class FusionThunderEffect implements AnimatedEffect {

    private final Entity entity;
    private final @Nullable Player player;
    private final int gemsInserted;

    public FusionThunderEffect(Entity entity, @Nullable Player player, int gemsInserted) {
        this.entity = entity;
        this.player = player;
        this.gemsInserted = gemsInserted;
    }

    @Override
    public void playAt(Location location, double time) {
        try {
            if (entity.isDead()) return;

            int tick = (int) time;

            // ---------------------------
            // 1. Thunder strikes section
            // ---------------------------
            // every 10 ticks, for N times based on gemsInserted
            if (tick >= 20 && (tick - 20) % 10 == 0 && (tick - 20) / 10 < gemsInserted) {
                Location strikeLoc = location.clone().add(Math.random() * 0.1, 0, Math.random() * 0.1);
                location.getWorld().strikeLightningEffect(strikeLoc);

                SoundType.FUSION_SUCCESS_2.play(entity.getLocation());
                SoundType.FUSION_MASTER_ROAR.play(entity.getLocation());
            }

            // ---------------------------
            // 2. Particle swirl section
            // ---------------------------
            Location particleLoc = entity.getLocation().add(
                    (Math.random() - 0.5) * 3.5, // x offset
                    Math.random() * 3.5,         // y offset
                    (Math.random() - 0.5) * 3.5  // z offset
            );
            entity.getWorld().spawnParticle(Particle.CRIMSON_SPORE, particleLoc, 1, 0, 0, 0, 0);
            entity.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, particleLoc, 1, 0, 0, 0, 0);

            // ---------------------------
            // 3. Entity pitch animation
            // ---------------------------
            if (player != null && player.isOnline()) {
                final int holdTicks = 60;
                final float startPitch = location.getPitch();
                final float targetPitch = -80f;

                Location loc = entity.getLocation();

                if (tick <= 20) {
                    // look up smoothly
                    float pitch = startPitch + ((targetPitch - startPitch) * tick / 20f);
                    loc.setPitch(pitch);
                    entity.teleport(loc);

                } else if (tick <= 20 + holdTicks) {
                    // hold pitch up
                    loc.setPitch(targetPitch);
                    entity.teleport(loc);

                } else if (tick <= 40 + holdTicks) {
                    // return to start pitch
                    float pitch = targetPitch + ((startPitch - targetPitch) * (tick - 20 - holdTicks) / 20f);
                    loc.setPitch(pitch);
                    entity.teleport(loc);
                }
            }

        } catch (Exception ignored) {
            // swallow safely
        }
    }

    @Override
    public int getDurationTicks() {
        // animation runs until pitch returns to normal
        // 20 up + 60 hold + 20 down = 100 ticks
        return 40 + 60; // adjust if gemsInserted thunder duration should extend
    }
}
