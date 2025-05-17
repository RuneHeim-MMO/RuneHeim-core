package org.zuma.examplePlugin.item.impl.pickaxe;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.zuma.examplePlugin.item.SkillRequirement;
import org.zuma.examplePlugin.item.base.GearItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.ItemRarity;
import org.zuma.examplePlugin.modifier.Modifier;
import org.zuma.examplePlugin.modifier.constants.ModifierType;
import org.zuma.examplePlugin.skill.constants.SkillType;

import java.util.List;

public class IronPickaxe extends GearItem {
    public IronPickaxe() {
        super(ItemId.IRON_PICKAXE.asInt(), Material.STONE_PICKAXE, ItemRarity.COMMON, "Iron Pickaxe", List.of(Component.text("Test description")));
        super.addModifier(new Modifier(ModifierType.MINING_BOOST, 4));
        super.addRequirement(new SkillRequirement(SkillType.MINING, 1));
    }
}
