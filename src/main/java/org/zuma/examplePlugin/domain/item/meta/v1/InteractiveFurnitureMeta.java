package org.zuma.examplePlugin.domain.item.meta.v1;

import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;

// Material OR json + type must be set
public record InteractiveFurnitureMeta(HouseSlot.HouseSlotType applicableSlot, BlockType type, VersionedJson json) {
}
