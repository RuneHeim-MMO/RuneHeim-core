package org.zuma.examplePlugin.domain.herblore;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.world.ChunkLoadEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.herblore.base.FertileBlock;
import org.zuma.examplePlugin.domain.herblore.base.PlantRecord;
import org.zuma.examplePlugin.domain.herblore.base.PopulatedBlock;
import org.zuma.examplePlugin.domain.herblore.persistence.PlantCandidatesRepository;

import java.util.*;
import java.util.function.Function;

public class HerbloreManager {
    private final static long CHUNK_REPOPULATION_INTERVAL_TICKS = 10 * 20L * 60; // 10 minutes in ticks
    public final ChunkPlantPopulator populator;

    private final Map<Long, FertileBlock[]> validGrowthBlocks;
    private final Map<Long, List<String>> chunkPopulatedBlocks; // ChunkKey to x_z key
    private final List<PlantRecord> plantCandidates;

    public HerbloreManager(PlantCandidatesRepository repository, ChunkPlantPopulator populator) {
        this.populator = populator;
        this.validGrowthBlocks = new HashMap<>();
        this.chunkPopulatedBlocks = new HashMap<>();
        this.plantCandidates = repository.getAll();

        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::tryPopulateActiveChunks, 30 * 20L, CHUNK_REPOPULATION_INTERVAL_TICKS); // Every 10 minutes
    }

    private boolean canRepopulateChunk(Chunk chunk) {
        Long lastPopTime = populator.getChunkLastPopulationTime(chunk); // stored in ticks
        long currentTimeTicks = System.currentTimeMillis() / 50L; // ms -> ticks

        if (lastPopTime == null) {
            return true;
        }
        
        long timeSinceLastPop = currentTimeTicks - lastPopTime;
        boolean canRepopulate = timeSinceLastPop >= CHUNK_REPOPULATION_INTERVAL_TICKS;
        
        return canRepopulate;
    }

    private void tryPopulateActiveChunks() {
        for (World world : Bukkit.getWorlds()) {

            if (!world.getName().equals("world")) {
                continue;
            }

            int parts = 10;
            Chunk[] chunks = world.getLoadedChunks();
            int batchSize = (int) Math.ceil(chunks.length / (double) parts);

            if (chunks.length == 0) {
                continue;
            }

            for (int i = 0; i < chunks.length; i += batchSize) {
                int end = Math.min(i + batchSize, chunks.length);
                Chunk[] batch = Arrays.copyOfRange(chunks, i, end);

                Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                    for (Chunk chunk : batch) {
                        if (canRepopulateChunk(chunk)) populateChunk(chunk);
                    }
                }, (i / batchSize) * 20L); // Spread batches over time (1 second apart)
            }
        }
    }

    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();

        if (!chunk.getWorld().getName().equals("world")) {
            return;
        }
        if (validGrowthBlocks.containsKey(chunk.getChunkKey())) {
            if (canRepopulateChunk(chunk)) populateChunk(chunk);
            return;
        }

        // Build cache of valid growth blocks
        getValidBlocksForGrowth(chunk);

        // Initial population
        if (canRepopulateChunk(chunk)) populateChunk(chunk);
    }

    public void populateChunk(Chunk chunk) {
        FertileBlock[] validBlocks = getValidBlocksForGrowth(chunk);
        int plantAmount = chunkPopulatedBlocks.computeIfAbsent(chunk.getChunkKey(), k -> new ArrayList<>()).size();

        for (PopulatedBlock populatedBlock : populatedBlockList) {
            chunkPopulatedBlocks.computeIfAbsent(chunk.getChunkKey(), k -> new ArrayList<>()).add(populatedBlock.key());
        }
    }

    public void deletePopulatedBlock(long chunkKey, String blockKey) {
        if (chunkPopulatedBlocks.containsKey(chunkKey)) {
            List<String> storedKeys = chunkPopulatedBlocks.computeIfAbsent(chunkKey, k -> new ArrayList<>());
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
