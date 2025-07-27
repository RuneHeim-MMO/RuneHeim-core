package org.zuma.examplePlugin.domain.skill;

import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

public record SkillRequirement(SkillType type, int minimumLevel) {
    public boolean playerMeetsRequirement(RunePlayer player) {
        return player.getSkill(this.type).getLevelWithBonuses() >= this.minimumLevel;
    }
}
