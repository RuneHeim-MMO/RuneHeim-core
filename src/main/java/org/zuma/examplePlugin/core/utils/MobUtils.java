package org.zuma.examplePlugin.core.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MobUtils {
    private static final List<UUID> walkingMobs = new ArrayList<>();

    /**
     * Moves the mob to a location and runs callbacks based on arrival or timeout.
     *
     * @param mob          The mob to move
     * @param target       The destination
     * @param maxTimeTicks Max time (in ticks) before failing
     * @param onArrive     Callback if mob reaches destination
     * @param onFail       Callback if mob doesn't reach in time
     */
    public static void moveToWithCallback(Mob mob, Location target, long maxTimeTicks, Runnable onArrive, Runnable onFail) {
        UUID uuid = mob.getUniqueId();

        if (walkingMobs.contains(uuid)) {
            return;
        }

        walkingMobs.add(uuid);

        Location clonedTarget = target.clone();
        new BukkitRunnable() {
            long elapsed = 0;

            public void run() {

                if (!mob.isValid()) {
                    walkingMobs.remove(uuid);
                    cancel();
                    return;
                }

                mob.getPathfinder().moveTo(clonedTarget);

                // Reached destination
                if (mob.getLocation().distanceSquared(clonedTarget) < 1.0) {
                    onArrive.run();
                    walkingMobs.remove(uuid);
                    cancel();
                    return;
                }


                // Timeout check
                elapsed += 10;
                if (elapsed >= maxTimeTicks) {
                    walkingMobs.remove(uuid);
                    onFail.run();
                    cancel();
                }
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0, 10); // check every 10 ticks

    }

    public static boolean isSafeMobLocation(Location location) {
        World world = location.getWorld();
        if (world == null) return false;

        Block blockAtFeet = world.getBlockAt(location);
        Block blockAtHead = world.getBlockAt(location.clone().add(0, 1, 0));
        Block blockBelow = world.getBlockAt(location.clone().add(0, -1, 0));

        // 1. Must be air or passable at feet and head
        boolean feetClear = blockAtFeet.isPassable();
        boolean headClear = blockAtHead.isPassable();

        // 2. Block below must be solid (so mob can stand)
        boolean hasGround = blockBelow.getType().isSolid();

        return feetClear && headClear && hasGround;
    }
}
