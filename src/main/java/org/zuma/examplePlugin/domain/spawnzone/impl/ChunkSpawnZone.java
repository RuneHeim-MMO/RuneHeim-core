package org.zuma.examplePlugin.domain.spawnzone.impl;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneMobRecord;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneType;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.meta.AbstractSpawnZoneMeta;

import java.util.List;

public class ChunkSpawnZone extends AbstractSpawnZone {
    private final boolean spawnAtHighestBlock;

    public ChunkSpawnZone(AbstractSpawnZoneMeta meta, boolean spawnAtHighestBlock) {
        super(SpawnZoneType.CHUNK, meta);
        this.spawnAtHighestBlock = spawnAtHighestBlock;
    }

    @Override
    public Location getNextMobLocation() {
        Location base = super.getLocation();
        Chunk chunk = base.getChunk();
        World world = chunk.getWorld();

        // Each chunk is 16x16 blocks
        int chunkX = chunk.getX() << 4; // World X of the chunk's origin
        int chunkZ = chunk.getZ() << 4; // World Z of the chunk's origin

        // Random coordinates within chunk
        int blockX = chunkX + (int) (Math.random() * 16);
        int blockZ = chunkZ + (int) (Math.random() * 16);

        // Determine Y based on config
        int blockY = spawnAtHighestBlock
                ? world.getHighestBlockYAt(blockX, blockZ)
                : base.getBlockY(); // use the Y from the base location

        return new Location(world, blockX + 0.5, blockY, blockZ + 0.5); // Center in block
    }

}
