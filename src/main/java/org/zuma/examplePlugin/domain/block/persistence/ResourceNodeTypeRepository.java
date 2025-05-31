package org.zuma.examplePlugin.domain.block.persistence;

import org.bukkit.Material;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeType;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class ResourceNodeTypeRepository {

    public List<ResourceNodeType> getAll() {
        return List.of(new ResourceNodeType(0, "Iron ore", Material.IRON_ORE, List.of(new SkillRequirement(SkillType.MINING, 1)), ModifierType.MINING_BOOST, 3000, 5, new ItemRecord(ItemId.IRON_ORE, 1), "You need a pickaxe to mine that!", "Mining", SoundType.MINING_STEP, SoundType.MINING_SUCCESS));
    }
}
