package org.zuma.examplePlugin.domain.item.impl;

import com.google.gson.Gson;
import org.bukkit.Location;
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
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.FurnitureItemMeta;

public class FurnitureItem extends AbstractItemType {
    private final FurnitureItemMeta meta;

    public FurnitureItem(AbstractItemMeta meta, FurnitureItemMeta furnitureItemMeta) {
        super(meta);
        this.meta = furnitureItemMeta;

        if (this.meta.applicableSlot().equals(HouseSlot.HouseSlotType.DECORATION) && this.meta.material() == null) {
            throw new IllegalStateException("Decoration furniture must have a material defined.");
        }

        if (this.meta.applicableSlot().equals(HouseSlot.HouseSlotType.INTERACTIVE) && (this.meta.type() == null || this.meta.json() == null)) {
            throw new IllegalStateException("Interactive furniture must have a type and json defined.");
        }

        if (this.meta.applicableSlot().equals(HouseSlot.HouseSlotType.DECORATION)) {
            super.setTypeLore("Decoration Furniture", Icon.FLOWER.get());
        } else {
            super.setTypeLore("Interactive Furniture", Icon.GEAR.get());
        }
    }


    public void placeInHouse(Location location, BlockFace face) {
        try {
            if (meta.applicableSlot().equals(HouseSlot.HouseSlotType.DECORATION)) {
                Block block = location.getBlock();
                BlockData data = block.getBlockData();

                if (data instanceof Directional directional) {
                    directional.setFacing(face);
                    block.setBlockData(directional);
                }
                block.setType(material);
                return;
            }

            if (meta.applicableSlot().equals(HouseSlot.HouseSlotType.INTERACTIVE)) {
                AbstractInteractableBlockMeta meta = new AbstractInteractableBlockMeta((int) (Math.random() * 10000000), this.meta.type(), face, location, HologramSide.TOP);
                RuneQuestPlugin.getBlockLoader().registerBlock(RuneQuestPlugin.getBlockManager(), meta, this.meta.json());
            }
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to place furniture item in house: " + e.getMessage());
        }
    }

    public FurnitureItemMeta getFurnitureMeta() {
        return meta;
    }

}
