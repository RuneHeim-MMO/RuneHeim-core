package org.zuma.examplePlugin.item;

import org.zuma.examplePlugin.player.RunePlayer;
import org.zuma.examplePlugin.skill.constants.SkillType;

public class SkillRequirement {
    private final SkillType type;
    private final int minimumLevel;

    public SkillRequirement(SkillType type, int minimumLevel) {
        this.type = type;
        this.minimumLevel = minimumLevel;
    }

    public SkillType getType() {
        return type;
    }

    public int getMinimumLevel() {
        return minimumLevel;
    }

    public boolean playerMeetsRequirement(RunePlayer player) {
        return player.getSkill(this.type).getLevel() >= this.minimumLevel;
    }
}
