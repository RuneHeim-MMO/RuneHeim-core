package org.zuma.examplePlugin.domain.schematic;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public record InteractiveElement(Location location, BlockFace face, InteractiveElType type) {
}
