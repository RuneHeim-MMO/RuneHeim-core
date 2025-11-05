package org.zuma.examplePlugin.domain.block.base;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.List;

public interface MultiBlockHitbox {

    int getWidth();

    int getHeight();

    Location getLocation();

    BlockFace getFace();

    default List<Location> calculateHitboxSize() {
        List<Location> hitboxLocations = new java.util.ArrayList<>();
        Location base = getLocation();
        BlockFace face = getFace();

        int halfWidth = getWidth() / 2;

        for (int w = -halfWidth; w <= halfWidth; w++) {
            for (int h = 0; h < getHeight(); h++) {
                Location nextLocation = base.clone();

                switch (face) {
                    case NORTH:
                        nextLocation.add(w, h, 0);
                        break;
                    case SOUTH:
                        nextLocation.add(-w, h, 0);
                        break;
                    case EAST:
                        nextLocation.add(0, h, w);
                        break;
                    case WEST:
                        nextLocation.add(0, h, -w);
                        break;
                }

                hitboxLocations.add(nextLocation);
            }
        }

        return hitboxLocations;
    }

}
