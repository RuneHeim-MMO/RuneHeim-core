package org.zuma.examplePlugin.domain.herblore.persistence;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.block.constants.ResourceNodeTypeId;
import org.zuma.examplePlugin.domain.herblore.base.PlantRecord;

import java.util.List;

public class PlantCandidatesRepository {


    public List<PlantRecord> getAll() {
        return List.of(
                new PlantRecord(ResourceNodeTypeId.DEWLEAF.asInt(), 0, 1.0, 0.8, 0.9, 10, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.SUNROOT.asInt(), 0, 1.0, 0.8, 1, 20, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.MOONSHADE.asInt(), 0, 1.0, 0.8, 0.9, 10, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.CRIMSON_THISTLE.asInt(), 0, 1.0, 0.7, 0.8, 10, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.FROSTCAP.asInt(), 0, 1.0, 0.6, 0.7, 10, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.GLOWFERN.asInt(), 0, 1.0, 0.5, 0.6, 10, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.STORMVINE.asInt(), 0, 1.0, 0.4, 0.5, 10, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.BLOODORCHID.asInt(), 0, 1.0, 0.3, 0.4, 10, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.DRAGONS_BREATH.asInt(), 0, 1.0, 0.2, 0.3, 10, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.ETHEREAL_BLOOM.asInt(), 0, 1.0, 0.1, 0.2, 10, Material.GRASS_BLOCK)
        );

    }
}


/*     new PlantRecord(ResourceNodeTypeId.DEWLEAF.asInt(), 0.7, 1.0, 0, 0.5, 80, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.SUNROOT.asInt(), 0.6, 0.9, 0, 0.5, 70, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.MOONSHADE.asInt(), 0.4, 0.7, 0, 0.5, 50, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.CRIMSON_THISTLE.asInt(), 0.3, 0.6, 0, 0.5, 40, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.FROSTCAP.asInt(), 0.2, 0.4, 0, 0.5, 30, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.GLOWFERN.asInt(), 0.15, 0.35, 0, 0.5, 25, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.STORMVINE.asInt(), 0.08, 0.2, 0, 0.5, 15, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.BLOODORCHID.asInt(), 0.05, 0.15, 0, 0.5, 10, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.DRAGONS_BREATH.asInt(), 0.02, 0.08, 0, 0.5, 5, Material.GRASS_BLOCK),
                new PlantRecord(ResourceNodeTypeId.ETHEREAL_BLOOM.asInt(), 0.005, 0.02, 0, 0.5, 2, Material.GRASS_BLOCK)*/