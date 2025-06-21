package org.zuma.examplePlugin.core.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

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
}
