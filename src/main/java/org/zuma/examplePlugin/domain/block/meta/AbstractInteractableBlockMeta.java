package org.zuma.examplePlugin.domain.block.meta;

import org.bukkit.Location;
import org.zuma.examplePlugin.domain.block.constants.BlockType;

import javax.annotation.Nullable;

public record AbstractInteractableBlockMeta(BlockType type, Location location, @Nullable Location hologramLocation) {

    public AbstractInteractableBlockMeta(BlockType type, Location location) {
        this(type, location, computeHologramLocation(location));
    }

    private static Location computeHologramLocation(Location location) {
        return location.clone().add(0.5, 2, 0.5);
    }
}