package org.zuma.examplePlugin.domain.modifier;

import org.zuma.examplePlugin.domain.skill.constants.SkillType;

public record SkillModifier(SkillType type, int amount) {
}
