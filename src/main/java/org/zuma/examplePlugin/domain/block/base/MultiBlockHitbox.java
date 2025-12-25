package org.zuma.examplePlugin.domain.block.base;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public interface MultiBlockHitbox {

    int getWidth();

    int getHeight();

    default int getDepth() {
        return 1;
    }

    Location getLocation();

    BlockFace getFace();

    default List<Location> calculateHitboxSize() {
        List<Location> hitboxLocations = new ArrayList<>();
        Location base = getLocation();
        BlockFace face = getFace();

        int halfWidth = getWidth() / 2;

        int depth = getDepth();

        if (depth < 1) {
            depth = 1;
        }

        for (int w = -halfWidth; w <= halfWidth; w++) {
            for (int h = 0; h < getHeight(); h++) {
                for (int d = 0; d < depth; d++) {
                    Location nextLocation = base.clone();

                    switch (face) {
                        case NORTH:
                            // depth goes negative Z, width along X
                            nextLocation.add(w, h, d);
                            break;
                        case SOUTH:
                            // depth goes positive Z
                            nextLocation.add(-w, h, -d);
                            break;
                        case EAST:
                            // depth goes positive X, width along Z
                            nextLocation.add(-d, h, w);
                            break;
                        case WEST:
                            // depth goes negative X
                            nextLocation.add(d, h, -w);
                            break;
                        default:
                            // If you're facing something like UP/DOWN, no offsets defined here
                            break;
                    }

                    hitboxLocations.add(nextLocation);
                }
            }
        }

        return hitboxLocations;
    }


}
