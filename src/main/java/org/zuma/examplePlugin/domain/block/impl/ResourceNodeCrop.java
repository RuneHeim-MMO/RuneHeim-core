package org.zuma.examplePlugin.domain.block.impl;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Farmland;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;

public class ResourceNodeCrop extends ResourceNode {
    public ResourceNodeCrop(BlockManager manager, AbstractInteractableBlockMeta meta) {
        super(manager, meta);
    }

    @Override
    public void spawn(Material material) {
        super.spawn(material);
        Block farmLand = getLocation().getBlock().getRelative(0, -1, 0);
        Block crop = getLocation().getBlock();

        farmLand.setType(Material.FARMLAND);
        crop.setType(Material.AIR);

        Farmland farmLandData = (Farmland) farmLand.getBlockData();
        farmLandData.setMoisture(farmLandData.getMaximumMoisture());
        farmLand.setBlockData(farmLandData);

        crop.setType(material);
        BlockData blockData = crop.getBlockData();
        if (blockData instanceof Ageable ageable) {
            ageable.setAge(ageable.getMaximumAge());
            crop.setBlockData(ageable);
        }

    }

    @Override
    public void depleteResource() {
        Block blockUnder = getLocation().getBlock().getRelative(0, -1, 0);
        super.depleteResource();
        blockUnder.setType(Material.DIRT);
        getLocation().getBlock().setType(Material.AIR);
    }
}
