package org.zuma.examplePlugin.skill;

import org.zuma.examplePlugin.skill.constants.SkillType;
import org.zuma.examplePlugin.utils.XpUtils;

public class Skill {
    private final SkillType type;
    private int xp;

    public Skill(SkillType type, int xp) {
        this.type = type;
        this.xp = xp;
    }

    public SkillType getType() {
        return type;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void addXp(int xp) {
        this.xp += xp;
    }

    public int getLevel() {
        return XpUtils.getLevelFromXp(this.xp);
    }
}
