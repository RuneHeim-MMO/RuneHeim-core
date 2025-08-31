package org.zuma.examplePlugin.domain.effect.effects;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;

public class SpecialAttackSmokeEffect implements AnimatedEffect {
    private final int durationTicks = 12;       // quick effect (~0.6s)
    private final int points = 10;              // particles per ring
    private final double baseRadius = 0.2;
    private final double maxRadius = 5.0;
    private final double maxHeight = 0.25;
    private final double rotationSpeed = Math.PI * 2 / durationTicks; // one rotation over duration
    private final double phaseOffset = Math.random() * Math.PI * 2;  // vary between instances

    @Override
    public void playAt(Location location, double time) {
        if (time < 0) return;
        double progress = Math.min(1.0, time / durationTicks);

        // ease-out cubic for smoother feel
        double eased = 1 - Math.pow(1 - progress, 3);

        double radius = baseRadius + (maxRadius - baseRadius) * eased;
        double yOffset = maxHeight * Math.sin(progress * Math.PI); // rise then fall
        double rot = phaseOffset + rotationSpeed * time;

        // ring of particles around current location
        for (int i = 0; i < points; i++) {
            double angle = rot + (2 * Math.PI * i) / points;
            double x = location.getX() + Math.cos(angle) * radius;
            double z = location.getZ() + Math.sin(angle) * radius;
            double y = location.getY() + yOffset;

            location.getWorld().spawnParticle(Particle.CLOUD, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
        }

        // small central puff at the very start
        if (progress < 0.25) {
            location.getWorld().spawnParticle(Particle.LARGE_SMOKE, location.clone().add(0, 0.1, 0), 4, 0.15, 0.15, 0.15, 0.02);
        }
    }

    @Override
    public int getDurationTicks() {
        return durationTicks;
    }
}
