package org.zuma.examplePlugin.domain.block.persistence;

import org.zuma.examplePlugin.constants.SoundType;
import org.zuma.examplePlugin.domain.block.base.ResourceNodeMeta;
import org.zuma.examplePlugin.domain.item.ItemDrop;
import org.zuma.examplePlugin.domain.item.SkillRequirement;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class ResourceNodeMetaRepository {

    public ResourceNodeMeta getById(int id) {
        return new ResourceNodeMeta("Iron ore", org.bukkit.Material.IRON_ORE, List.of(new SkillRequirement(SkillType.MINING, 1)), ModifierType.MINING_BOOST, 3000, 5, new ItemDrop(ItemId.IRON_ORE, 1), "You need a pickaxe to mine that!", "Mining", SoundType.MINING_STEP, SoundType.MINING_SUCCESS);
    }
}
