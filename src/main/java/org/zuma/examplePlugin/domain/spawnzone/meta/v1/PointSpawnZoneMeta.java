package org.zuma.examplePlugin.domain.spawnzone.meta.v1;

import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneMobRecord;

import javax.annotation.Nullable;
import java.util.List;

public record PointSpawnZoneMeta(List<SpawnZoneMobRecord> mobRecords, boolean spawnAtHighestBlock,
                                 @Nullable MarkerType marker, @Nullable String markerLabel) {
}
