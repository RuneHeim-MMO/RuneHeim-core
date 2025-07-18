package org.zuma.examplePlugin.domain.spawnzone;

import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.spawnzone.meta.AbstractSpawnZoneMeta;

public record SpawnZoneRecord(AbstractSpawnZoneMeta meta, VersionedJson json) {
}
