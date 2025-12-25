package org.zuma.examplePlugin.domain.spawnzone.meta;

import org.bukkit.Location;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneType;

public record AbstractSpawnZoneMeta(int id, SpawnZoneType type, Integer waveRateSeconds,
                                    Integer deadThresholdForWave,
                                    Location location) {
}
