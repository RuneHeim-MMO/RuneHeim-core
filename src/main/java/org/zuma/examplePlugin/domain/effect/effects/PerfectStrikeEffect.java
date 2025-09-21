package org.zuma.examplePlugin.domain.effect.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class PerfectStrikeEffect implements AnimatedEffect {
    private final int durationTicks = 40;
    private final RunePlayer player;

    public PerfectStrikeEffect(RunePlayer player) {
        this.player = player;
    }

    @Override
    public void playAt(Location location, double time) {
        if (time == 0) {
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.empty(),
                    Component.text("Perfect Strike!", Color.SUCCESS.asColor()),
                    TextUtils.getGenericDuration()
            ));
            return;
        }

        double progress = Math.min(1.0, time / durationTicks);

        // Explosion factor (fast burst at start, then fade)
        double burst = Math.sin(progress * Math.PI); // peaks at mid-time

        // Random sparks radiating outward
        int sparks = 20;
        for (int i = 0; i < sparks; i++) {
            double angle = Math.random() * 2 * Math.PI;
            double radius = Math.random() * 2 * burst; // outward from center
            double height = Math.random() * 1.0 * burst; // floating up

            double x = location.getX() + Math.cos(angle) * radius;
            double y = location.getY() + height;
            double z = location.getZ() + Math.sin(angle) * radius;

            location.getWorld().spawnParticle(Particle.SCULK_CHARGE_POP, x, y, z, 1, 0, 0, 0, 0);
        }

        // Floating sparkles above center for "magic sparkle" effect
        if (progress < 0.5) {
            int floaters = 5;
            for (int i = 0; i < floaters; i++) {
                double x = location.getX() + (Math.random() - 0.5) * 0.5;
                double y = location.getY() + 1.0 + Math.random() * 0.5;
                double z = location.getZ() + (Math.random() - 0.5) * 0.5;

                location.getWorld().spawnParticle(Particle.END_ROD, x, y, z, 1, 0, 0, 0, 0);
            }
        }

        // Quick puff at center at start for impact
        if (progress < 0.2) {
            location.getWorld().spawnParticle(Particle.LARGE_SMOKE, location.clone().add(0, 0.2, 0), 8, 0.3, 0.3, 0.3, 0.05);
        }
    }

    @Override
    public int getDurationTicks() {
        return durationTicks;
    }
}
