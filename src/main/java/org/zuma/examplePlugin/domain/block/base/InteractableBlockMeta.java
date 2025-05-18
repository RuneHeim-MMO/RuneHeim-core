package org.zuma.examplePlugin.domain.block.base;

import org.bukkit.Location;
import org.zuma.examplePlugin.domain.block.constants.BlockType;

public record InteractableBlockMeta(BlockType type, Location location, String jsonMeta) {
}
