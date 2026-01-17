package org.zuma.examplePlugin.domain.herblore;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.world.ChunkLoadEvent;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.herblore.base.FertileBlock;
import org.zuma.examplePlugin.domain.herblore.base.PlantRecord;
import org.zuma.examplePlugin.domain.herblore.base.PopulatedBlock;
import org.zuma.examplePlugin.domain.herblore.persistence.PlantCandidatesRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HerbloreManager {
    private final static long CHUNK_REPOPULATION_INTERVAL_TICKS = 10 * 20L * 60; // 10 minutes in ticks
    public final ChunkPlantPopulator populator;

    private final Map<Long, FertileBlock[]> validGrowthBlocks;
    private final Map<Long, List<String>> chunkPopulatedBlocks; // ChunkKey to x_z key
    private final List<PlantRecord> plantCandidates;
    private final Object populationLock = new Object();

    public HerbloreManager(PlantCandidatesRepository repository, ChunkPlantPopulator populator) {
        this.populator = populator;
        this.validGrowthBlocks = new HashMap<>();
        this.chunkPopulatedBlocks = new HashMap<>();
        this.plantCandidates = repository.getAll();

        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::tryPopulateActiveChunks, 30 * 20L, CHUNK_REPOPULATION_INTERVAL_TICKS); // Every 10 minutes
    }

    private boolean canRepopulateChunk(Chunk chunk) {
        synchronized (populationLock) {
            Long lastPopTime = populator.getChunkLastPopulationTime(chunk); // stored in ticks
            long currentTimeTicks = System.currentTimeMillis() / 50L; // ms -> ticks

            if (lastPopTime == null) {
                return true;
            }

            long timeSinceLastPop = currentTimeTicks - lastPopTime;
            return timeSinceLastPop >= CHUNK_REPOPULATION_INTERVAL_TICKS;
        }
    }

    public int getPlantsInRadius(@NotNull Chunk chunk, int chunkRadius) {
        int totalPlants = 0;
        List<Long> nearbyChunks = getNearbyChunkKeys(chunk, chunkRadius);

        for (long nearbyChunkKey : nearbyChunks) {
            List<String> populatedInChunk = chunkPopulatedBlocks.get(nearbyChunkKey);
            if (populatedInChunk != null) {
                totalPlants += populatedInChunk.size();
            }
        }

        return totalPlants;
    }

    private List<Long> getNearbyChunkKeys(Chunk chunk, int radius) { // radius = 1 → 3×3 square, radius = 2 → 5×5 square, radius = 3 → 7×7 square
        int centerX = chunk.getX();
        int centerZ = chunk.getZ();

        List<Long> nearbyChunks = new ArrayList<>();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                long key = Chunk.getChunkKey(centerX + dx, centerZ + dz);
                nearbyChunks.add(key);
            }
        }
        return nearbyChunks;
    }

    public int getTotalPlants() {
        int total = 0;
        for (List<String> plants : chunkPopulatedBlocks.values()) {
            if (plants != null) {
                total += plants.size();
            }
        }
        return total;
    }

    private void tryPopulateActiveChunks() {
        int plantsBefore = getTotalPlants();
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

            AtomicInteger totalBatches = new AtomicInteger();

            for (int i = 0; i < chunks.length; i += batchSize) {
                int end = Math.min(i + batchSize, chunks.length);
                Chunk[] batch = Arrays.copyOfRange(chunks, i, end);

                Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                    for (Chunk chunk : batch) {
                        if (canRepopulateChunk(chunk)) {
                            populateChunk(chunk);
                        }
                    }

                    totalBatches.getAndIncrement();
                }, (i / batchSize) * 20L); // Spread batches over time (1 second apart)
            }

            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                int plantsAfter = getTotalPlants();
                int delta = plantsAfter - plantsBefore;

                if (delta > 0) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Herblore active chunk population run complete on world " + world.getName() + ". Plants before: " + plantsBefore + ", after: " + plantsAfter);
                }
            }, (chunks.length / batchSize + 1) * 20L + 10L); // Log after all batches are done
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

    private List<PopulatedBlock> populateChunk(Chunk chunk) {
        synchronized (populationLock) {
            FertileBlock[] validBlocks = getValidBlocksForGrowth(chunk);

            int plantAmount = chunkPopulatedBlocks.computeIfAbsent(chunk.getChunkKey(), k -> new ArrayList<>()).size();
            List<PopulatedBlock> populatedBlockList = populator.populateChunk(chunk, validBlocks, plantCandidates, this::deletePopulatedBlock, plantAmount);

            for (PopulatedBlock populatedBlock : populatedBlockList) {
                chunkPopulatedBlocks.computeIfAbsent(chunk.getChunkKey(), k -> new ArrayList<>()).add(populatedBlock.key());
            }

            return populatedBlockList;
        }
    }

    private void deletePopulatedBlock(long chunkKey, String blockKey) {
        synchronized (populationLock) {
            if (chunkPopulatedBlocks.containsKey(chunkKey)) {
                List<String> storedKeys = chunkPopulatedBlocks.computeIfAbsent(chunkKey, k -> new ArrayList<>());
                storedKeys.remove(blockKey);
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
}
