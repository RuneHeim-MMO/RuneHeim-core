package org.zuma.examplePlugin.domain.spawnzone;

import org.zuma.examplePlugin.domain.mob.constants.MobType;

public record SpawnZoneMobRecord(MobType mobId, int amount, int spawnChancePercentage) {
}
