package org.zuma.examplePlugin.core.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
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
                    if (onArrive != null)
                        onArrive.run();
                    walkingMobs.remove(uuid);
                    cancel();
                    return;
                }


                // Timeout check
                elapsed += 10;
                if (elapsed >= maxTimeTicks) {
                    walkingMobs.remove(uuid);
                    if (onFail != null)
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

        // 1. Must be air or passable at feet and head
        boolean feetClear = blockAtFeet.isPassable();
        boolean headClear = blockAtHead.isPassable();

        return feetClear && headClear;
    }

    public static Location getGroundLocation(Location loc) {
        World world = loc.getWorld();
        int blockX = loc.getBlockX();
        int blockZ = loc.getBlockZ();
        int blockY = loc.getBlockY();

        int safeY = blockY;

        // Search downward from current Y
        for (int y = blockY; y >= world.getMinHeight(); y--) {
            Material type = world.getBlockAt(blockX, y, blockZ).getType();
            if (type.isSolid() && !type.name().endsWith("_LEAVES")) {
                safeY = y + 1; // place just above solid non-leaf block
                break;
            }
        }

        return new Location(world, loc.getX(), safeY + 0.5, loc.getZ());
    }

    public static List<ItemStack> getMobEquipment(LivingEntity mob) {
        EntityEquipment eq = mob.getEquipment();
        if (eq == null) return new ArrayList<>(); // no equipment

        List<ItemStack> equipment = new ArrayList<>();
        if (eq.getHelmet() != null) equipment.add(eq.getHelmet());
        if (eq.getChestplate() != null) equipment.add(eq.getChestplate());
        if (eq.getLeggings() != null) equipment.add(eq.getLeggings());
        if (eq.getBoots() != null) equipment.add(eq.getBoots());
        if (eq.getItemInMainHand().getType() != Material.AIR) equipment.add(eq.getItemInMainHand());
        if (eq.getItemInOffHand().getType() != Material.AIR) equipment.add(eq.getItemInOffHand());

        return equipment;
    }


    public static void pullPlayerTowardMob(Player player, Entity mob, double strength, double upwardLift) {
        // Get direction vector from player to mob
        Vector direction = mob.getLocation().toVector().subtract(player.getLocation().toVector());

        // Normalize (so it's just direction, not distance)
        direction.normalize();
        Vector velocity = direction.multiply(strength);
        velocity.setY(upwardLift);

        // Apply velocity
        player.setVelocity(velocity);
    }
}
