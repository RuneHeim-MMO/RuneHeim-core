package org.zuma.examplePlugin.domain.item.base;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.impl.furniture.CombatDummyFurniture;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public abstract class FurnitureItem extends AbstractItemType {
    protected final HouseSlot.HouseSlotType applicableSlot;

    public FurnitureItem(AbstractItemMeta meta, HouseSlot.HouseSlotType applicableSlot) {
        super(meta);
        this.applicableSlot = applicableSlot;

        if (applicableSlot.equals(HouseSlot.HouseSlotType.DECORATION)) {
            super.setTypeLore("Decorative Furniture", Icon.FLOWER.get());
        } else {
            super.setTypeLore("Interactive Furniture", Icon.GEAR.get());
        }
    }


    public abstract void placeInHouse(Location location, BlockFace face);

    public void rotateInHouse(Location location, BlockFace face) {
        Block block = location.getBlock();
        BlockData data = block.getBlockData();
        AbstractInteractableBlock interactableBlock = RuneQuestPlugin.getBlockManager().getAbstractInteractableBlock(location.getBlock());

        if (interactableBlock instanceof CombatDummyFurniture cdf) {
            cdf.rotate(face);
            return;
        }

        if (data instanceof Directional directional) {
            directional.setFacing(face);
            block.setBlockData(directional);
        } else if (data instanceof Rotatable rotatable) {
            rotatable.setRotation(face);
            block.setBlockData(rotatable);
        }
    }


    public HouseSlot.HouseSlotType getApplicableSlot() {
        return applicableSlot;
    }

}
