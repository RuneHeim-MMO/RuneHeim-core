package org.zuma.examplePlugin.domain.item.meta.v1;


import org.bukkit.inventory.EquipmentSlot;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import javax.annotation.Nullable;
import java.util.List;

public record EquipableTypeMeta(@Nullable EquipmentSlot equipmentSlot, @Nullable String equipableModel,
                                @Nullable List<SkillModifier> skillModifiers, @Nullable List<Modifier> modifiers,
                                @Nullable List<SkillRequirement> requirements, @Nullable List<String> requiredQuests) {
}
