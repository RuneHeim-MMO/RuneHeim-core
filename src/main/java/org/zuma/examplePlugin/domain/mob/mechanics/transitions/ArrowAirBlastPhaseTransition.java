package org.zuma.examplePlugin.domain.mob.mechanics.transitions;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.combat.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.combat.constants.ProjectileType;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;

import java.util.Random;

public class ArrowAirBlastPhaseTransition implements MobMechanic {
    public static final NamespacedKey projectileType = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistenceDataKey.PROJECTILE_TYPE.asString());
    private CombatantMob mob;
    private final double height;

    public ArrowAirBlastPhaseTransition(int height) {
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
        SoundType.ENDER_DRAGON_GROWL.play(entity.getLocation());
        EnderCrystal crystal = (EnderCrystal) entity.getLocation().getWorld().spawnEntity(entity.getLocation(), EntityType.END_CRYSTAL);
        crystal.setShowingBottom(false);
        crystal.setBeamTarget(entity.getLocation().clone().add(0, 30, 0));

        final double targetHeight = height + Math.random() * 5; // desired 10–15 blocks
        final Location startLoc = entity.getLocation().clone();
        final int durationTicks = 40; // ~2 seconds to rise
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
        final Random random = new Random();
        final Vector downward = new Vector(0, -1, 0);

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (!entity.isValid() || mob == null) {
                    crystal.remove();
                    cancel();
                    return;
                }

                ticks++;
                double newY = startLoc.getY() + safeRisePerTick * ticks;
                Location loc = entity.getLocation();
                loc.setY(newY);
                entity.getWorld().spawnParticle(Particle.TRIAL_OMEN, loc, 10, 3, 3, 3, 0);
                entity.getWorld().spawnParticle(Particle.DRAGON_BREATH, loc, 50, 1, 20, 1, 0.05);
                entity.teleport(loc);
                SoundType.GRASS_STEP.play(entity.getLocation());

                if (ticks >= durationTicks) {
                    cancel();

                    Location locFinal = entity.getLocation();
                    crystal.remove();

                    for (int i = 0; i < 100; i++) {
                        // Small random horizontal spread
                        double spreadX = (random.nextDouble() - 0.5) * 0.5; // smaller numbers = tighter cone
                        double spreadZ = (random.nextDouble() - 0.5) * 0.5;

                        // Combine into a direction vector pointing mostly downward
                        Vector direction = new Vector(spreadX, -1, spreadZ).normalize();

                        // Optionally, vary spawn position a bit for visual randomness
                        Location arrowLoc = entity.getLocation().clone().add(
                                (random.nextDouble() - 0.5) * 2.0, // X offset
                                5.0,                               // start 5 blocks above
                                (random.nextDouble() - 0.5) * 2.0  // Z offset
                        );

                        Arrow arrow = entity.getWorld().spawnArrow(
                                arrowLoc,
                                direction,
                                1.8f,  // speed
                                12.0f  // spread (higher = more random pitch/yaw)
                        );

                        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                        arrow.setShooter((ProjectileSource) entity);
                        arrow.setGravity(true);
                        arrow.setPersistent(false);
                        arrow.getPersistentDataContainer().set(projectileType, PersistentDataType.STRING, ProjectileType.RANGED.name());
                    }

                    entity.getWorld().spawnParticle(Particle.EXPLOSION, locFinal, 1);
                    entity.getWorld().spawnParticle(Particle.FLAME, locFinal, 50, 2, 2, 2, 0.1);
                    entity.getWorld().playSound(locFinal, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
                    entity.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_DEATH, 2f, 1f);
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
