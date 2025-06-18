package org.zuma.examplePlugin.core.utils;

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
}
