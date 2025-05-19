package org.zuma.examplePlugin.domain.block.meta;

import org.bukkit.Location;
import org.zuma.examplePlugin.domain.block.constants.BlockType;

import javax.annotation.Nullable;

public record AbstractInteractableBlockMeta(BlockType type, Location location, @Nullable Location hologramLocation,
                                            String jsonMeta) {
}
