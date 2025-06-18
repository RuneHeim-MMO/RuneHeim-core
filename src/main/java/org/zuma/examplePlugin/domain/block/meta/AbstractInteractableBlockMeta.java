package org.zuma.examplePlugin.domain.block.meta;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.zuma.examplePlugin.domain.block.constants.BlockType;

import javax.annotation.Nullable;


public record AbstractInteractableBlockMeta(int id, BlockType type, BlockFace face, Location location,
                                            @Nullable Location hologramLocation) {

    public AbstractInteractableBlockMeta(int id, BlockType type, BlockFace face, Location location, HologramSide hologramSide) {
        this(id, type, face, location, computeHologramLocation(location, hologramSide));
    }

    private static Location computeHologramLocation(Location location, HologramSide side) {
        Location hologramLocation = location.clone().add(0.5, 0.5, 0.5);

        switch (side) {
            case TOP -> hologramLocation.add(0, 2, 0);
            case BOTTOM -> hologramLocation.add(0, -1, 0);
            case FRONT -> hologramLocation.add(0, 1, -1);
            case BACK -> hologramLocation.add(0, 1, 1);
            case LEFT -> hologramLocation.add(-1, 1, 0);
            case RIGHT -> hologramLocation.add(1, 1, 0);
        }

        return hologramLocation;
    }

}