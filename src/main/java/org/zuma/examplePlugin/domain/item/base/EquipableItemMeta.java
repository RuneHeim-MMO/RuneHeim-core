package org.zuma.examplePlugin.domain.item.base;

import org.zuma.examplePlugin.domain.item.SkillRequirement;
import org.zuma.examplePlugin.domain.modifier.Modifier;

import java.util.List;

public record EquipableItemMeta(List<Modifier> modifiers, List<SkillRequirement> requirements) {
}
