package org.zuma.examplePlugin.domain.item.base;

import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.core.interfaces.Restrictable;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class EquipableItemType extends AbstractItemType implements Restrictable {
    private final List<Modifier> modifiers = new ArrayList<>();
    private final List<SkillModifier> skillModifiers = new ArrayList<>();
    private final List<SkillRequirement> requirements = new ArrayList<>();
    private final List<String> questRequirements = new ArrayList<>();

    @Nullable
    private EquipmentSlot equipmentSlot;

    @Nullable
    private String equipableModel;

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

    public @NotNull List<SkillRequirement> getSkillRequirements() {
        return requirements;
    }

    public List<String> getRequiredCompletedQuests() {
        return questRequirements;
    }

    public void addSkillRequirements(List<SkillRequirement> requirements) {
        if (requirements == null) {
            return;
        }

        this.requirements.addAll(requirements);
    }

    public void addQuestRequirements(List<String> questIds) {
        if (questIds == null) {
            return;
        }

        this.questRequirements.addAll(questIds);
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

    public void setEquipableData(@Nullable EquipmentSlot equipmentSlot, @Nullable String model) {
        this.equipmentSlot = equipmentSlot;
        this.equipableModel = model;
    }

    public @Nullable EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    public @Nullable String getEquipableModel() {
        return equipableModel;
    }

}
