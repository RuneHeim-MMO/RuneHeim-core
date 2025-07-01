package org.zuma.examplePlugin.domain.skill;

import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

public class Skill {
    private final SkillType type;
    private double xp;
    private int bonus;

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

    public int getBaseLevel() {
        return XpUtils.getLevelFromXp(this.xp);
    }

    public int getLevelWithBonuses() {
        return Math.max(0, getBaseLevel() + bonus);
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonusLvls) {
        this.bonus = bonusLvls;
    }
}
