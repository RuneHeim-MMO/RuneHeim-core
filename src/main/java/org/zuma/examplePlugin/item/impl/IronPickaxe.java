package org.zuma.examplePlugin.item.impl;

import org.bukkit.Material;
import org.zuma.examplePlugin.item.SkillRequirement;
import org.zuma.examplePlugin.item.base.GearItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.ItemRarity;
import org.zuma.examplePlugin.modifier.Modifier;
import org.zuma.examplePlugin.modifier.constants.ModifierType;
import org.zuma.examplePlugin.skill.constants.SkillType;

public class IronPickaxe extends GearItem {
    public IronPickaxe() {
        super(ItemId.IRON_PICKAXE.asInt(), Material.STONE_PICKAXE, ItemRarity.COMMON, "Iron Pickaxe");
        super.addModifier(new Modifier(ModifierType.MINING_BOOST, 4));
        super.addRequirement(new SkillRequirement(SkillType.MINING, 1));
    }
}
