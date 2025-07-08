package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;

import java.util.ArrayList;
import java.util.List;

public class ModifierLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public ModifierLore(List<Modifier> modifiers, List<SkillModifier> skillModifiers, boolean hideLabel) {
        if (!hideLabel && (!skillModifiers.isEmpty() || !modifiers.isEmpty())) {
            lore.add(Component.text("Modifiers: ", Color.ITEM_CATEGORY_LABEL.asColor()));
        }

        if (!skillModifiers.isEmpty()) {
            skillModifiers.forEach(modifier -> {
                int amount = Math.toIntExact(modifier.amount());
                String sign = amount > 0 ? "+" : "";
                TextColor color = amount > 0 ? Color.ITEM_BONUS_POSITIVE.asColor() : Color.ITEM_BONUS_NEGATIVE.asColor();

                lore.add(Component.text(modifier.type().getSymbol(), NamedTextColor.WHITE)
                        .append(Component.text(" " + sign + amount + " " + modifier.type().getLabel() + " Level").color(color)));
            });

            if (!modifiers.isEmpty()) {
                lore.add(Component.empty());
            }
        }

        if (!modifiers.isEmpty()) {
            modifiers.forEach(modifier -> {
                int amount = Math.toIntExact(Math.round(modifier.amount()));
                String sign = amount > 0 ? "+" : "";
                TextColor color = amount > 0 ? Color.ITEM_BONUS_POSITIVE.asColor() : Color.ITEM_BONUS_NEGATIVE.asColor();

                lore.add(Component.text(modifier.type().getSymbol(), NamedTextColor.WHITE)
                        .append(Component.text(" " + sign + amount + " " + modifier.type().getLabel()).color(color)));
            });
        }

    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
