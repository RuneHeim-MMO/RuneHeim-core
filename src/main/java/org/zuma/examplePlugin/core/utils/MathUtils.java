package org.zuma.examplePlugin.core.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {
    public static BlockFace yawToFace(float yaw) {
        int rot = Math.round(yaw / 90f) & 0x3;
        return switch (rot) {
            case 0 -> BlockFace.SOUTH;
            case 1 -> BlockFace.WEST;
            case 2 -> BlockFace.NORTH;
            case 3 -> BlockFace.EAST;
            default -> BlockFace.NORTH;
        };
    }

    public static Location roundLocation(Location loc) {
        if (loc == null) return null;

        World world = loc.getWorld();
        int x = (int) Math.round(loc.getX());
        int y = (int) Math.round(loc.getY());
        int z = (int) Math.round(loc.getZ());

        float yaw = loc.getYaw();
        float pitch = loc.getPitch();

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static double getLocationsDistanceXZ(Location loc1, Location loc2) {
        if(loc1.getWorld() != loc2.getWorld()) {
            return Double.MAX_VALUE;
        }

        double dx = loc1.getX() - loc2.getX();
        double dz = loc1.getZ() - loc2.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    public static Location randomPointInCircle(Location center, double radius) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        // random angle 0–2π
        double theta = rnd.nextDouble(0, 2 * Math.PI);
        // random r in [0, radius), weighted so points are uniform in the circle
        double r = Math.sqrt(rnd.nextDouble()) * radius;

        double offsetX = r * Math.cos(theta);
        double offsetZ = r * Math.sin(theta);

        return center.clone().add(offsetX, 0, offsetZ);
    }

    public static Player findNearestSurvivalPlayer(LivingEntity center, double radius) {
        Player best = null;
        double bestSq = radius * radius;
        for (Player p : center.getWorld().getPlayers()) {
            if (!p.isValid() || p.isDead()) continue;
            switch (p.getGameMode()) {
                case CREATIVE:
                case SPECTATOR:
                    continue;
                default:
            }
            double dx = p.getLocation().getX() - center.getLocation().getX();
            double dy = p.getLocation().getY() - center.getLocation().getY();
            double dz = p.getLocation().getZ() - center.getLocation().getZ();
            double distSq = dx * dx + dy * dy + dz * dz;
            if (distSq <= bestSq) {
                bestSq = distSq;
                best = p;
            }
        }
        return best;
    }

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
