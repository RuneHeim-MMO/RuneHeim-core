package org.zuma.examplePlugin.domain.item.impl.furniture;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.HologramSide;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.FurnitureItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class DecorationFurniture extends FurnitureItem {
    private final Material material;

    public DecorationFurniture(AbstractItemMeta meta, HouseSlot.HouseSlotType applicableSlot, Material material) {
        super(meta, applicableSlot);
        this.material = material;
    }

    public void placeInHouse(Location location, BlockFace face) {
        try {
            Block block = location.getBlock();
            BlockData data = block.getBlockData();

            if (data instanceof Directional directional) {
                directional.setFacing(face);
                block.setBlockData(directional);
            }
            block.setType(material);

        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to place furniture item in house: " + e.getMessage());
        }
    }

}
