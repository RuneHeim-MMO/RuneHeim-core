package org.zuma.examplePlugin.domain.mob.behavior;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.mob.MobRegistry;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;

import java.util.*;

public class MobIdleBehaviorHandler {
    private final MobRegistry registry;
    private final Random random = new Random();
    private final HashMap<UUID, Location> mobLocations = new HashMap<>();
    private final List<World> worlds = Bukkit.getWorlds();

    // Stores next allowed idle sound play time per mob UUID
    private final Map<UUID, Long> nextIdleSoundTimes = new HashMap<>();

    private final long baseCooldownMillis = 10000; // 10 seconds base cooldown
    private final long jitterMillisMax = 5000;     // up to 5 seconds random jitter

    public MobIdleBehaviorHandler(MobRegistry registry) {
        this.registry = registry;
    }

    public void start() {
        final int delay = 50;

        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), () -> {
            long now = System.currentTimeMillis();

            for (World world : worlds) {
                for (Entity entity : world.getEntities()) {
                    if (!(entity instanceof LivingEntity living)) continue;
                    if (living.isDead() || (entity instanceof Player)) continue;

                    AbstractMob mob = this.registry.getMob(entity);
                    if (mob == null) continue;

                    UUID mobUUID = mob.getUUID();
                    boolean hasWalkingLimit = mob.getMaxWalkDistance() != -1; // -1 = infinite distance, 0 - no walking, x - amnt of blocks

                    if (hasWalkingLimit && !mobLocations.containsKey(mobUUID)) {
                        mobLocations.put(mobUUID, entity.getLocation());
                    } else if (hasWalkingLimit && entity.getLocation().distanceSquared(mobLocations.get(mobUUID)) > mob.getMaxWalkDistance()) {
                        entity.teleport(mobLocations.get(mobUUID));
                    }

                    var behavior = mob.getBehavior();
                    if (behavior == null) continue;

                    UUID id = entity.getUniqueId();
                    long nextAllowed = nextIdleSoundTimes.getOrDefault(id, 0L);

                    if (now < nextAllowed) {
                        continue; // cooldown active for this mob
                    }

                    if (random.nextDouble() < 0.15) {
                        behavior.playSound(living, MobBehavior.MobSoundType.IDLE);

                        // Schedule next allowed sound time with base cooldown + random jitter
                        long jitter = (long) (random.nextDouble() * jitterMillisMax);
                        nextIdleSoundTimes.put(id, now + baseCooldownMillis + jitter);
                    }
                }
            }
        }, delay, delay);
    }
}
