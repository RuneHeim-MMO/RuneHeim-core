package org.zuma.examplePlugin.domain.block.impl;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.metadata.FixedMetadataValue;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class ResourceNodeCrop extends ResourceNode {
    private Material previousMaterial;
    private boolean needsFarmland;

    public ResourceNodeCrop(BlockManager manager, AbstractInteractableBlockMeta meta) {
        super(manager, meta);
    }

    @Override
    public void spawn(Material material) {
        this.needsFarmland = super.getNodeType().getRetexturedStem() == null || super.getNodeType().getRetexturedStem().isNeedsFarmland();
        super.spawn(material);
        Block farmLand = getLocation().getBlock().getRelative(0, -1, 0);
        Block crop = getLocation().getBlock();
        previousMaterial = farmLand.getType();

        if (needsFarmland) {
            farmLand.setType(Material.FARMLAND);
            Farmland farmLandData = (Farmland) farmLand.getBlockData();
            farmLandData.setMoisture(farmLandData.getMaximumMoisture());
            farmLand.setBlockData(farmLandData);
        }

        if (super.getNodeType().getRetexturedStem() == null) {
            crop.setType(material,false);

            BlockData blockData = crop.getBlockData();
            if (blockData instanceof Ageable ageable) {
                ageable.setAge(ageable.getMaximumAge());
                crop.setBlockData(ageable);
            }
        }

    }

    @Override
    public void onGather(RunePlayer player, double baseGatheringLuckPercentage) {
        Block blockUnder = getLocation().getBlock().getRelative(0, -1, 0);
        super.onGather(player, baseGatheringLuckPercentage);

        if (super.isDepleted()) {
            if (!super.isPersistent()) {
                blockUnder.setType(this.previousMaterial);
            } else {
                if (needsFarmland) getLocation().getBlock().setType(Material.DIRT);
            }

        }
    }

    @Override
    public void onDisable() {
        if (previousMaterial != null) {
            Block blockUnder = getLocation().getBlock().getRelative(0, -1, 0);
            getLocation().getBlock().setType(Material.AIR, false);
            blockUnder.setType(previousMaterial, false);
        }

        super.onDisable();
    }
}
