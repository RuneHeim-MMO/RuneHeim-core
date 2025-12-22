package org.zuma.examplePlugin.domain.item.meta.v1;

import org.bukkit.Material;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;

import javax.annotation.Nullable;

// Material OR json + type must be set
public record FurnitureItemMeta(HouseSlot.HouseSlotType applicableSlot, @Nullable Material material,
                                @Nullable VersionedJson json, @Nullable BlockType type) {
}
