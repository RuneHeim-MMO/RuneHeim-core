package org.zuma.examplePlugin.domain.housing.world;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class VoidGenerator extends ChunkGenerator {
    @Override
    public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        // Do nothing, no terrain noise
    }

    @Override
    public void generateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        // No surface
    }

    @Override
    public void generateBedrock(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        // Void
    }

    @Override
    public boolean shouldGenerateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return false;
    }

    @Override
    public boolean shouldGenerateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return false;
    }
}