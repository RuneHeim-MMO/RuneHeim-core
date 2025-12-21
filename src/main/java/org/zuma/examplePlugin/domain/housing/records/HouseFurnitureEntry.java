package org.zuma.examplePlugin.domain.housing.records;

import org.bukkit.block.BlockFace;

import java.util.UUID;

public record HouseFurnitureEntry(UUID uuid, int itemId, int slotIndex, BlockFace face, HouseSlot.HouseSlotType type,
                                  String json) {
}
