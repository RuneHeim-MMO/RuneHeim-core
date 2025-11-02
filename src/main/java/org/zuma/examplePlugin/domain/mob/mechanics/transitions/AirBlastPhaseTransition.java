package org.zuma.examplePlugin.domain.mob.mechanics.transitions;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;

public class AirBlastPhaseTransition implements MobMechanic {
    private CombatantMob mob;
    private final double height;

    public AirBlastPhaseTransition(int height) {
        this.height = height;
    }

    public void setParent(AbstractMob mob) {
        if (!(mob instanceof CombatantMob combatantMob)) {
            throw new IllegalArgumentException("AirBlastPhaseTransition can only be applied to CombatantMob");
        }

        this.mob = combatantMob;
    }

    @Override
    public void execute(LivingEntity target) {
        if (mob == null || mob.getEntity() == null) return;

        Entity entity = mob.getEntity();
        mob.setInvulnerable(true);

        final double targetHeight = height + Math.random() * 5; // desired 10–15 blocks
        final Location startLoc = entity.getLocation().clone();
        final int durationTicks = 40; // ~2 seconds to rise
        final double risePerTick = targetHeight / durationTicks;

        entity.setGravity(false); // disable gravity while flying

        // ✅ Find the actual maximum height we can go before hitting a block
        double maxRise = 0;
        for (int i = 1; i <= targetHeight; i++) {
            Location check = startLoc.clone().add(0, i, 0);
            if (!check.getBlock().isPassable()) {
                maxRise = i - 1; // stop 1 block below ceiling
                break;
            }
        }
        if (maxRise == 0) maxRise = targetHeight; // no obstruction found

        final double safeTargetHeight = maxRise;
        final double safeRisePerTick = safeTargetHeight / durationTicks;

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (!entity.isValid() || mob == null) {
                    cancel();
                    return;
                }

                ticks++;
                double newY = startLoc.getY() + safeRisePerTick * ticks;
                Location loc = entity.getLocation();
                loc.setY(newY);
                entity.getWorld().spawnParticle(Particle.TRIAL_OMEN, loc, 10, 3, 3, 3, 0);
                entity.teleport(loc);

                if (ticks >= durationTicks) {
                    cancel();

                    Location locFinal = entity.getLocation();
                    entity.getWorld().spawnParticle(Particle.EXPLOSION, locFinal, 1);
                    entity.getWorld().spawnParticle(Particle.FLAME, locFinal, 50, 6, 6, 6, 0.1);
                    entity.getWorld().playSound(locFinal, Sound.ENTITY_GENERIC_EXPLODE, 2f, 1f);

                    entity.setGravity(true);
                    mob.setInvulnerable(false);
                }
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, 3L); // every 3 ticks (~0.15s)
    }

    @Override
    public void cleanup() {
        this.mob = null;
    }

}
