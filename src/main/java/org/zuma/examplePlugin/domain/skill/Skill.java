package org.zuma.examplePlugin.domain.skill;

import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

public class Skill {
    private final SkillType type;
    private double xp;

    public Skill(SkillType type, int xp) {
        this.type = type;
        this.xp = xp;
    }

    public SkillType getType() {
        return type;
    }

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }

    public void addXp(double xp) {
        this.xp += xp;
    }

    public int getLevel() {
        return XpUtils.getLevelFromXp(this.xp);
    }
}
