package org.zuma.examplePlugin.domain.item.impl.furniture;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.HologramSide;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;
import org.zuma.examplePlugin.domain.item.base.FurnitureItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class InteractiveFurniture extends FurnitureItem {
    private final BlockType type;
    private final VersionedJson json;

    public InteractiveFurniture(AbstractItemMeta meta, HouseSlot.HouseSlotType applicableSlot, BlockType type, VersionedJson json) {
        super(meta, applicableSlot);
        this.type = type;
        this.json = json;
    }

    public void placeInHouse(Location location, BlockFace face) {
        try {
            AbstractInteractableBlockMeta meta = new AbstractInteractableBlockMeta(100000 + (int) (Math.random() * 10000000), this.type, face, location, HologramSide.TOP);
            RuneQuestPlugin.getBlockLoader().registerBlock(RuneQuestPlugin.getBlockManager(), meta, this.json);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to place furniture item in house: " + e.getMessage());
        }
    }
}
