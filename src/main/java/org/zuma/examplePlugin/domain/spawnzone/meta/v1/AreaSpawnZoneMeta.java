package org.zuma.examplePlugin.domain.spawnzone.meta.v1;

import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneMobRecord;

import java.util.List;

public record AreaSpawnZoneMeta(List<SpawnZoneMobRecord> mobRecords, int radius, boolean spawnAtHighestBlock,
                                boolean isBiomeSpawn) {
}
