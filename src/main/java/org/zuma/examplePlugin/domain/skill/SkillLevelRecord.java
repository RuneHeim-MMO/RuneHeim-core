package org.zuma.examplePlugin.domain.skill;

import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

public record SkillLevelRecord(SkillType type, int level) {
    public int getXP() {
        return XpUtils.getXpForLevel(level);
    }
}
