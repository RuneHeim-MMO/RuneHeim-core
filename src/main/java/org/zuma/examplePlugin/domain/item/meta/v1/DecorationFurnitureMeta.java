package org.zuma.examplePlugin.domain.item.meta.v1;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;

// Material OR json + type must be set
public record DecorationFurnitureMeta(HouseSlot.HouseSlotType applicableSlot, Material material) {
}
