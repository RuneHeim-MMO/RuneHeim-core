package org.zuma.examplePlugin.domain.mob.behavior;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.core.utils.MobUtils;
import org.zuma.examplePlugin.domain.mob.MobRegistry;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;

import java.util.*;

public class MobIdleBehaviorHandler {
    private final MobRegistry registry;
    private final Random random = new Random();
    private final List<World> worlds = Bukkit.getWorlds();

    // Stores next allowed idle sound play time per mob UUID
    private final Map<UUID, Long> nextIdleSoundTimes = new HashMap<>();

    public MobIdleBehaviorHandler(MobRegistry registry) {
        this.registry = registry;
    }

    private void teleportMobHome(Entity entity, AbstractMob mobInstance) {
        if (mobInstance.getSpawnLocation() == null) {
            return;
        }

        entity.teleport(mobInstance.getSpawnLocation());
    }

    private void walkMobHome(Mob entity, AbstractMob mobInstance) {
        Location homeLocation = mobInstance.getSpawnLocation();
        boolean isFrozen = mobInstance.isFrozen();

        if (isFrozen) {
            mobInstance.unfreezeMob();
        }

        MobUtils.moveToWithCallback(entity, homeLocation, 10 * 20L,
                mobInstance::freezeMob,
                () -> {
                    mobInstance.unfreezeMob();
                    teleportMobHome(entity, mobInstance);
                });


    }

    private void maybeReturnHome(Entity entity, AbstractMob mobInstance) {
        boolean hasWalkingLimit = mobInstance.getMaxWalkDistance() != -1; // -1 = infinite distance, 0 - no walking, x - amnt of blocks

        if (mobInstance.getSpawnLocation() == null) {
            return;
        }

        if (!hasWalkingLimit) {
            return;
        }

        Location homeLocation = mobInstance.getSpawnLocation().clone();
        Location entityLocation = entity.getLocation().clone();

        homeLocation.setY(0);
        entityLocation.setY(0);

        if (entityLocation.distanceSquared(homeLocation) > mobInstance.getMaxWalkDistance()) {
            // Is too far away

            if (entity instanceof Mob mobEntity) {
                walkMobHome(mobEntity, mobInstance);
                return;
            }

            // Not a mob, teleport
            teleportMobHome(entity, mobInstance);
        }
    }

    private void maybePlayIdleSound(AbstractMob mob, LivingEntity entity, Long now) {
        MobBehavior behavior = mob.getBehavior();
        if (behavior == null) return;

        UUID id = entity.getUniqueId();
        long nextAllowed = nextIdleSoundTimes.getOrDefault(id, 0L);

        if (now < nextAllowed) {
            return; // cooldown active for this mob
        } else {
            nextIdleSoundTimes.remove(id);
        }

        if (random.nextDouble() < 0.15) {
            behavior.playSound(entity, MobBehavior.MobSoundType.IDLE);

            // Schedule next allowed sound time with base cooldown + random jitter
            // up to 5 seconds random jitter
            long jitterMillisMax = 5000;
            long jitter = (long) (random.nextDouble() * jitterMillisMax);
            // 10 seconds base cooldown
            long baseCooldownMillis = 10000;
            nextIdleSoundTimes.put(id, now + baseCooldownMillis + jitter);
        }
    }

    private void tickOneMob(LivingEntity entity, Long now) {
        AbstractMob mob = this.registry.getMob(entity);
        if (mob == null) return;

        maybeReturnHome(entity, mob);
        maybePlayIdleSound(mob, entity, now);
    }

    public void start() {
        final int delay = 50;

        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), () -> {
            long now = System.currentTimeMillis();

            for (World world : worlds) {
                for (Entity entity : world.getEntities()) {
                    if (!(entity instanceof LivingEntity living)) continue;
                    if (living.isDead() || (entity instanceof Player)) continue;

                    tickOneMob(living, now);
                }
            }
        }, 0L, delay);
    }
}
