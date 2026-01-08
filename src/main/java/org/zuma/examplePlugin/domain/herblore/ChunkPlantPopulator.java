package org.zuma.examplePlugin.domain.herblore;

import com.google.gson.Gson;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.impl.ResourceNode;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.HologramSide;
import org.zuma.examplePlugin.domain.block.meta.v1.ResourceNodeMeta;
import org.zuma.examplePlugin.domain.herblore.base.FertileBlock;
import org.zuma.examplePlugin.domain.herblore.base.PlantRecord;
import org.zuma.examplePlugin.domain.herblore.base.PopulatedBlock;

import java.util.List;
import java.util.function.BiConsumer;

public class ChunkPlantPopulator {
    private final FastNoiseLite noiseGen;

    public ChunkPlantPopulator() {
        noiseGen = new FastNoiseLite(2);
        noiseGen.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
    }

    public FertileBlock[] findValidGrowthBlocksInChunk(Chunk chunk) {
        World world = chunk.getWorld();

        int baseX = chunk.getX() << 4;
        int baseZ = chunk.getZ() << 4;

        FertileBlock[] blocks = new FertileBlock[256];
        int i = 0;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int finalX = baseX + x;
                int finalZ = baseZ + z;
                int finalY = world.getHighestBlockYAt(finalX, finalZ, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                Block block = world.getBlockAt(finalX, finalY, finalZ);

                if (FertileBlock.isMaterialValid(block.getType())) {
                    blocks[i++] = new FertileBlock(block.getType(), finalX, finalY, finalZ);
                } else {
                    blocks[i++] = null;
                }
            }
        }


        return blocks;
    }

    // Returns x_z keys of populated plants
    public List<PopulatedBlock> populateChunk(Chunk chunk, FertileBlock[] validBlocks, List<PlantRecord> candidates, BiConsumer<Long, String> handleDelete) {
        List<PopulatedBlock> populatedBlocks = new java.util.ArrayList<>();
        double chunkNoise = noiseGen.GetNoise(chunk.getX() * 5f, chunk.getZ() * 5f);

        for (FertileBlock fertileBlock : validBlocks) {
            if (fertileBlock == null) {
                continue;
            }

            int x = fertileBlock.x;
            int z = fertileBlock.z;
            double noiseValue = noiseGen.GetNoise(x * 15f, z * 15f);

            for (PlantRecord candidate : candidates) {
                if (PlayerUtils.getRandom().nextDouble() > candidate.spawnChance() / 100) {
                    continue;
                }

                if (chunkNoise < candidate.chunkNoiseMin() || chunkNoise > candidate.chunkNoiseMax()) {
                    System.out.println("Skipping chunk " + chunk.getX() + "," + chunk.getZ() + " due to noise value: " + chunkNoise);
                    continue;
                }

                if (noiseValue < candidate.noiseMin() || noiseValue > candidate.noiseMax()) {
                    System.out.println("Skipping plant at " + x + "," + z + " due to noise value: " + noiseValue + " not in range: " + candidate.noiseMin() + " - " + candidate.noiseMax());
                    continue;
                }

                int type = candidate.nodeTypeId();
                Location soilLocation = new Location(chunk.getWorld(), fertileBlock.x, fertileBlock.y, fertileBlock.z);
                Location plantLocation = soilLocation.clone().add(0, 1, 0);
                BlockType blockType = BlockType.CropResourceNode;
                Material topBlockMaterial = plantLocation.getBlock().getType();
                Material soilBlockMaterial = soilLocation.getBlock().getType();

                if (!topBlockMaterial.equals(Material.AIR)) {
                    break;
                }

                if (!soilBlockMaterial.equals(candidate.requiredSoilMaterial())) {
                    break;
                }

                AbstractInteractableBlock block = RuneQuestPlugin.getBlockLoader().registerBlock(org.zuma.examplePlugin.RuneQuestPlugin.getBlockManager(),
                        new AbstractInteractableBlockMeta(0, blockType, BlockFace.UP, plantLocation, HologramSide.TOP), new VersionedJson(new Gson().toJson(new ResourceNodeMeta(type, null, null)), 1));

                if (block instanceof ResourceNode node) {
                    node.setOnDepleteCallback(() -> {
                        handleDelete.accept(chunk.getChunkKey(), x + "_" + z);
                    });

                    node.setPersistent(false);
                    populatedBlocks.add(new PopulatedBlock(node, x + "_" + z));
                }

                continue;

            }
        }

        return populatedBlocks;
    }
}
