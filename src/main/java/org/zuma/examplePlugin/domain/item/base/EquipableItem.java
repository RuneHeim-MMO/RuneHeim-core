package org.zuma.examplePlugin.domain.item.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class EquipableItem extends AbstractItem implements SkillRestrictable {
    private final List<Modifier> modifiers = new ArrayList<>();
    private final List<SkillRequirement> requirements = new ArrayList<>();
    private boolean applyModifiersWhenInHand = false;

    public EquipableItem(AbstractItemMeta meta) {
        super(meta);
    }

    public boolean getApplyModifiersWhenInHand() {
        return this.applyModifiersWhenInHand;
    }

    public void applyModifiersWhenInHand(boolean apply) {
        this.applyModifiersWhenInHand = apply;
    }

    public List<Modifier> getModifiers() {
        return this.modifiers;
    }

    public Optional<Modifier> getModifierByType(ModifierType type) {
        return this.modifiers.stream()
                .filter(modifier -> modifier.type() == type)
                .findFirst();
    }

    public @NotNull List<SkillRequirement> getRequirements() {
        return requirements;
    }

    public void addRequirements(List<SkillRequirement> requirements) {
        if (requirements != null) {
            this.requirements.addAll(requirements);
            requirements.forEach(requirement -> appendToRequirementsLore(Component.text(requirement.type().getSymbol(), NamedTextColor.WHITE)
                    .append(Component.text(" " + requirement.minimumLevel() + " " + requirement.type().getLabel(), Color.ITEM_REQUIREMENT.asColor()))));
        }
    }

    public void addModifiers(List<Modifier> modifiers) {
        if (modifiers != null) {
            this.modifiers.addAll(modifiers);

            modifiers.forEach(modifier -> {
                int amount = Math.toIntExact(Math.round(modifier.amount()));
                String sign = amount > 0 ? "+" : "";
                TextColor color = amount > 0 ? Color.ITEM_BONUS_POSITIVE.asColor() : Color.ITEM_BONUS_NEGATIVE.asColor();

                appendToBonusesLore(Component.text(modifier.type().getSymbol(), NamedTextColor.WHITE)
                        .append(Component.text(" " + sign + amount + " " + modifier.type().getLabel()).color(color)));
            });
        }
    }

}
