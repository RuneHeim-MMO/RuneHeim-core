package org.zuma.examplePlugin.domain.item.meta.v1;

import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public record XPLampMeta(double xpReward, List<SkillType> disabledSkills) {
}
