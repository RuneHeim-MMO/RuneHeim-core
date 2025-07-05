package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.ModifierUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.impl.fairy.Fairy;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class EquipableLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public EquipableLore(EquipableItemType equipable) {
        if (!equipable.getRequirements().isEmpty()) {
            lore.add(Component.empty());
            lore.add(Component.text("Requirements: ", Color.ITEM_LABEL.asColor()));

            equipable.getRequirements().forEach(requirement -> lore.add(
                    Component.text(requirement.type().getSymbol(), NamedTextColor.WHITE)
                            .append(Component.text(" " + requirement.minimumLevel() + " " + requirement.type().getLabel(), Color.ITEM_REQUIREMENT.asColor()))));

        }

        lore.add(Component.empty());
        lore.addAll(new ModifierLore(equipable.getModifiers(), equipable.getSkillModifiers(), false).getLore());
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
