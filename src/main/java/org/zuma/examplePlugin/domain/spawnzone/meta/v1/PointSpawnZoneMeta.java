package org.zuma.examplePlugin.domain.spawnzone.meta.v1;

import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneMobRecord;

import java.util.List;

public record PointSpawnZoneMeta(List<SpawnZoneMobRecord> mobRecords, boolean spawnAtHighestBlock) {
}
