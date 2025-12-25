package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.impl.consumable.Potion;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;

import java.util.ArrayList;
import java.util.List;

public class PotionLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public PotionLore(Potion potion) {
        List<Modifier> modifiers = new ArrayList<>(potion.getEffect().modifiers());
        List<SkillModifier> skillModifiers = new ArrayList<>(potion.getEffect().skillModifiers());

        lore.add(Component.empty());
        lore.addAll(new ModifierLore(modifiers, skillModifiers, false).getLore());

        lore.add(Component.empty());
        lore.add(Component.text("Duration: ", Color.ITEM_CATEGORY_LABEL.asColor()));
        lore.add(Icon.CLOCK.asComponent().append(Component.text(potion.getEffect().durationSeconds() + " seconds", Color.ITEM_LORE_DESCRIPTION.asColor())));

    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
