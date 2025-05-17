package org.zuma.examplePlugin.item.impl.pickaxe;

import org.bukkit.Material;
import org.zuma.examplePlugin.item.SkillRequirement;
import org.zuma.examplePlugin.item.base.GearItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.ItemRarity;
import org.zuma.examplePlugin.modifier.Modifier;
import org.zuma.examplePlugin.modifier.constants.ModifierType;
import org.zuma.examplePlugin.skill.constants.SkillType;

public class BronzePickaxe extends GearItem {
    public BronzePickaxe() {
        super(ItemId.BRONZE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Bronze Pickaxe");
        super.addModifier(new Modifier(ModifierType.MINING_BOOST, 1));
        super.addRequirement(new SkillRequirement(SkillType.MINING, 1));
    }
}
