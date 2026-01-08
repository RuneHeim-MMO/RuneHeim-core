package org.zuma.examplePlugin.domain.herblore;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.world.ChunkLoadEvent;
import org.zuma.examplePlugin.domain.herblore.base.FertileBlock;
import org.zuma.examplePlugin.domain.herblore.base.PlantRecord;
import org.zuma.examplePlugin.domain.herblore.base.PopulatedBlock;
import org.zuma.examplePlugin.domain.herblore.persistence.PlantCandidatesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class HerbloreManager {
    public final ChunkPlantPopulator populator;

    private final Map<Long, FertileBlock[]> validGrowthBlocks;
    private final Map<Long, String> chunkPopulatedBlocks; // ChunkKey to x_z key
    private final List<PlantRecord> plantCandidates;

    public HerbloreManager(PlantCandidatesRepository repository, ChunkPlantPopulator populator) {
        this.populator = populator;
        this.validGrowthBlocks = new HashMap<>();
        this.chunkPopulatedBlocks = new HashMap<>();
        this.plantCandidates = repository.getAll();
    }

    public void onChunkLoad(ChunkLoadEvent event) {
        if (validGrowthBlocks.containsKey(event.getChunk().getChunkKey())) {
            return;
        }

        // Build cache of valid growth blocks
        getValidBlocksForGrowth(event.getChunk());
    }

    public void populateChunk(Chunk chunk) {
        FertileBlock[] validBlocks = getValidBlocksForGrowth(chunk);
        List<PopulatedBlock> populatedBlockList = populator.populateChunk(chunk, validBlocks, plantCandidates, this::deletePopulatedBlock);

        for (PopulatedBlock populatedBlock : populatedBlockList) {
            chunkPopulatedBlocks.put(chunk.getChunkKey(), populatedBlock.key());
        }
    }

    public void deletePopulatedBlock(long chunkKey, String blockKey) {
        if (chunkPopulatedBlocks.containsKey(chunkKey)) {
            String storedKey = chunkPopulatedBlocks.get(chunkKey);
            if (storedKey.equals(blockKey)) {
                chunkPopulatedBlocks.remove(chunkKey);
            }
        }
    }

    private FertileBlock[] getValidBlocksForGrowth(Chunk chunk) {
        if (validGrowthBlocks.containsKey(chunk.getChunkKey())) {
            return validGrowthBlocks.get(chunk.getChunkKey());
        } else {
            FertileBlock[] blocks = populator.findValidGrowthBlocksInChunk(chunk);
            validGrowthBlocks.put(chunk.getChunkKey(), blocks);
            return blocks;
        }


    }

    public void printGrowthBlocksAmount() {
        int sum = 0;

        for (Map.Entry<Long, FertileBlock[]> entry : validGrowthBlocks.entrySet()) {
            FertileBlock[] blocks = entry.getValue();
            for (FertileBlock block : blocks) {
                if (block != null) {
                    sum++;
                }
            }
        }

        System.out.println("Total valid growth blocks tracked: " + sum + " in " + validGrowthBlocks.size() + " chunks.");
    }

    public static void getChunkByKeyAsync(World world, long key, Function<Chunk, Void> cb) {
        int chunkX = (int) (key >> 32);
        int chunkZ = (int) (key);
        world.getChunkAtAsync(chunkX, chunkZ).thenAccept(cb::apply);
    }
}
