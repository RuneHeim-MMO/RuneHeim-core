package org.zuma.examplePlugin.domain.item.base;

import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class EquipableItemType extends AbstractItemType implements SkillRestrictable {
    private final List<Modifier> modifiers = new ArrayList<>();
    private final List<SkillModifier> skillModifiers = new ArrayList<>();
    private final List<SkillRequirement> requirements = new ArrayList<>();

    public EquipableItemType(AbstractItemMeta meta) {
        super(meta);
    }

    public List<Modifier> getModifiers() {
        return this.modifiers;
    }

    public List<SkillModifier> getSkillModifiers() {
        return this.skillModifiers;
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
        if (requirements == null) {
            return;
        }

        this.requirements.addAll(requirements);
    }

    public void addModifiers(List<Modifier> modifiers) {
        if (modifiers == null) {
            return;
        }

        this.modifiers.addAll(modifiers);
    }

    public void addSkillModifiers(List<SkillModifier> modifiers) {
        if (modifiers == null) {
            return;
        }

        this.skillModifiers.addAll(modifiers);
    }

    public abstract EquipableType getEquipableType();

}
