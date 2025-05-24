package org.zuma.examplePlugin.domain.item.meta;

import org.zuma.examplePlugin.domain.item.SkillRequirement;
import org.zuma.examplePlugin.domain.modifier.Modifier;

import java.util.List;

public record EquipableMeta(List<Modifier> modifiers, List<SkillRequirement> requirements) {
}
