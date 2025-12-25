package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.quest.Quest;

import java.util.ArrayList;
import java.util.List;

public class EquipableLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public EquipableLore(EquipableItemType equipable) {
        if (!equipable.getSkillRequirements().isEmpty()) {
            lore.add(Component.empty());
            lore.add(Component.text("Requirements: ", Color.ITEM_CATEGORY_LABEL.asColor()));

            equipable.getSkillRequirements().forEach(requirement -> lore.add(
                    Component.text(requirement.type().getSymbol(), NamedTextColor.WHITE)
                            .append(Component.text(" " + requirement.minimumLevel() + " " + requirement.type().getLabel(), Color.ITEM_REQUIREMENT.asColor()))));

        }

        if (!equipable.getRequiredCompletedQuests().isEmpty()) {
            lore.add(Component.empty());
            lore.add(Component.text("Quest Requirements: ", Color.ITEM_CATEGORY_LABEL.asColor()));

            equipable.getRequiredCompletedQuests().forEach(questId -> {
                Quest quest = RuneQuestPlugin.getQuestHandler().getRegistry().getQuestById(questId);
                lore.add(Component.text("• " + (quest != null ? quest.getName() : "INVALID: " + questId), Color.ITEM_REQUIREMENT.asColor()));
            });

        }

        if (!equipable.getModifiers().isEmpty() || !equipable.getSkillModifiers().isEmpty()) {
            lore.add(Component.empty());
            lore.addAll(new ModifierLore(equipable.getModifiers(), equipable.getSkillModifiers(), false).getLore());
        }
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
