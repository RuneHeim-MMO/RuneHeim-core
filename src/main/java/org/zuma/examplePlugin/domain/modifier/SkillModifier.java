package org.zuma.examplePlugin.domain.modifier;

import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;

public record SkillModifier(SkillType type, int amount, @Nullable Double percent) {
}
