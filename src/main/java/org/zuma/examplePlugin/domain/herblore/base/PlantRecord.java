package org.zuma.examplePlugin.domain.herblore.base;


import org.bukkit.Material;

import java.util.List;

public record PlantRecord(int nodeTypeId, double noiseMin, double noiseMax, double chunkNoiseMin, double chunkNoiseMax,
                          double spawnChance,
                          List<Material> requiredSoilMaterial) {
}
