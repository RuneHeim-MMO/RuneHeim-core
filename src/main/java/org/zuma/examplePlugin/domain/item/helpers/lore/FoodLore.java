package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.impl.consumable.Food;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.ArrayList;
import java.util.List;

public class FoodLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public FoodLore(Food food) {
        lore.add(Component.empty());
        lore.add(Component.text("Heals ", Color.SUCCESS.asColor())
                .append(SkillType.HITPOINTS.getSymbolComponent())
                .append(Component.text(food.getHealAmount() + " " + SkillType.HITPOINTS.getLabel(), Color.SUCCESS.asColor())));
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
