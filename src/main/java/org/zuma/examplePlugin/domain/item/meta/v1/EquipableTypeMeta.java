package org.zuma.examplePlugin.domain.item.meta.v1;

import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import javax.annotation.Nullable;
import java.util.List;

public record EquipableTypeMeta(@Nullable List<SkillModifier> skillModifiers, @Nullable List<Modifier> modifiers,
                                @Nullable List<SkillRequirement> requirements) {
}
