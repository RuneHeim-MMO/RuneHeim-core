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
import org.zuma.examplePlugin.domain.item.impl.fairy.Fairy;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FairyLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public FairyLore(Fairy fairy, ItemStack item) {
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state instanceof FairyState fairyState) {
            List<Modifier> combinedModifiers = ModifierUtils.combineModifiersByType(fairyState.getInsertedUpgrades().stream()
                    .flatMap(i -> i.getModifiers().stream())
                    .toList());

            List<SkillModifier> combinedSkillModifiers = ModifierUtils.combineSkillModifiersByType(fairyState.getInsertedUpgrades().stream()
                    .flatMap(i -> i.getSkillModifiers().stream())
                    .toList());


            lore.add(Component.empty());
            lore.add(Icon.LEVEL.asComponent().append(Component.text("Level: ", Color.ITEM_DESCRIPTION.asColor())
                    .append(Component.text(fairyState.getLevel() + " ", Color.ACCENT.asColor()))));

            String formattedCurrentXP = NumberFormat.getNumberInstance().format(fairyState.getExperience());
            String formattedNextLevelXP = NumberFormat.getNumberInstance().format(fairyState.getXPForLevel(fairyState.getLevel() + 1));

            if (fairyState.isMaxed()) {
                lore.add(Icon.EXPERIENCE.asComponent()
                        .append(Component.text("Maximum level achieved", Color.SUCCESS.asColor())));
            } else {
                lore.add(Icon.EXPERIENCE.asComponent()
                        .append(Component.text(formattedCurrentXP + " XP / " + formattedNextLevelXP + " XP", Color.SUCCESS.asColor())));
            }

            lore.add(Icon.SPROUT.asComponent()
                    .append(Component.text("I Generation", NamedTextColor.GREEN)));


            lore.add(Component.empty());

            lore.add(Icon.STAMINA.asComponent()
                    .append(Component.text(fairyState.getStamina() + "% Stamina", Color.FAIRY_STAMINA.asColor())));

            lore.add(Component.empty());

            lore.add(Icon.FAIRY.asComponent().append(Component.text("Fairy Bonuses", Color.SUCCESS.asColor())));
            lore.add(Component.text(TextUtils.repeatString("-", 16), Color.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));


            if (combinedSkillModifiers.isEmpty() && combinedModifiers.isEmpty()) {
                lore.add(Component.text("  * No upgrades * ", Color.ITEM_TYPE.asColor()));
            } else {
                lore.addAll(new ModifierLore(combinedModifiers, combinedSkillModifiers, true).getLore());
            }

            lore.add(Component.text(TextUtils.repeatString("-", 16), Color.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));

            if (!fairyState.isMaxed()) {
                lore.add(Component.empty());
                Color consumeFruitColor = fairyState.canConsumeFruit() ? Color.SUCCESS : Color.ERROR;
                lore.add((Component.text(fairyState.canConsumeFruit() ? "✓ Ready for upgrade" : "❌ Not ready for upgrade", consumeFruitColor.asColor())));
            }

            lore.add(Component.empty());
        }
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
