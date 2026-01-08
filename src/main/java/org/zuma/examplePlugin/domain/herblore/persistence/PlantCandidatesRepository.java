package org.zuma.examplePlugin.domain.herblore.persistence;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.block.constants.ResourceNodeTypeId;
import org.zuma.examplePlugin.domain.herblore.base.PlantRecord;

import java.util.List;

public class PlantCandidatesRepository {


    public List<PlantRecord> getAll() {
        return List.of(
                new PlantRecord(ResourceNodeTypeId.DEWLEAF.asInt(), 0.8, 1, 0.8, 1, 2, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.SUNROOT.asInt(), 0.6, 0.8, 0.6, 0.8, 1.5, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.MOONSHADE.asInt(), 0.4, 0.6, 0.4, 0.6, 1, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.CRIMSON_THISTLE.asInt(), 0.2, 0.4, 0.2, 0.4, 0.5, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.FROSTCAP.asInt(), 0, 0.2, 0, 0.2, 0.25, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.GLOWFERN.asInt(), -0.2, 0, -0.2, 0, 0.15, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.STORMVINE.asInt(), -0.4, -0.2, -0.4, -0.2, 0.10, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.BLOODORCHID.asInt(), -0.6, -0.4, -0.6, -0.4, 0.05, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.DRAGONS_BREATH.asInt(), -0.8, -0.6, -0.8, -0.6, 0.020, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.ETHEREAL_BLOOM.asInt(), -0.8, -0.6, -1, -0.8, 0.01, Material.GRASS_BLOCK)
        );

    }
}