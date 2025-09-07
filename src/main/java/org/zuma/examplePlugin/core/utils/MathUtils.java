package org.zuma.examplePlugin.core.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

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
}
