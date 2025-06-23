package org.zuma.examplePlugin.core.utils;

import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.EnumMap;

public class XpUtils {
    private static final int MAX_LEVEL = 99;
    private static final int[] XP_TABLE = new int[100]; // index 1-99 used

    static {
        int experience = 0;
        for (int level = 1; level <= MAX_LEVEL; level++) {
            if (level > 1) {
                double value = (level - 1) + 300 * Math.pow(2, (level - 1) / 7.0);
                experience += (int) Math.floor(value / 4.0);
            }
            XP_TABLE[level] = experience;
        }
    }

    public static int getXpForLevel(int level) {
        if (level < 1 || level > MAX_LEVEL) throw new IllegalArgumentException("Max level is " + MAX_LEVEL);
        return XP_TABLE[level];
    }

    public static int getLevelFromXp(double xp) {
        if (xp < 0) throw new IllegalArgumentException();

        for (int level = 2; level <= MAX_LEVEL; level++) {
            if (XP_TABLE[level] > xp) return level - 1;
        }
        return MAX_LEVEL;
    }

    public static int getXpIntoCurrentLevel(int totalXp, int currentLevel) {
        int xpAtCurrentLevel = getXpForLevel(currentLevel);
        return totalXp - xpAtCurrentLevel;
    }

    public static int getXpToNextLevel(int currentLevel) {
        if (isMaxLevel(currentLevel)) {
            return 0;
        }

        int xpAtCurrentLevel = getXpForLevel(currentLevel);
        int xpAtNextLevel = getXpForLevel(currentLevel + 1);
        return xpAtNextLevel - xpAtCurrentLevel;
    }

    public static int getCappedLevel(int level) {
        return isMaxLevel(level) ? MAX_LEVEL : level;
    }

    public static boolean isMaxLevel(int level) {
        return level >= MAX_LEVEL;
    }

    public static int getProgressPercent(int totalXp, int currentLevel) {
        if (isMaxLevel(currentLevel)) {
            return 100;
        }

        int xpIntoLevel = getXpIntoCurrentLevel(totalXp, currentLevel);
        int xpToNextLevel = getXpToNextLevel(currentLevel);
        return (int) Math.round((xpIntoLevel / (double) xpToNextLevel) * 100);
    }

    public static int calculateCombatLevel(int attack, int strength, int defence, int hitpoints) {
        // Defensive stats combined with a smaller weight
        double defenseScore = 0.7 * defence + 0.8 * hitpoints;
        // Offensive stats combined equally
        double offenseScore = attack + strength;

        // Weighted sum (offense weighed more)
        double weightedAverage = (offenseScore + defenseScore * 0.6) / (1.0 + 0.6);

        // Clamp input range to 1-99
        weightedAverage = Math.max(1, Math.min(99, weightedAverage));

        // Scale linearly to 1-100 combat level
        return (int) Math.round(1 + (weightedAverage - 1) * (99.0 / 98.0));
    }

    public static int getCombatLevelFromSkills(EnumMap<SkillType, Skill> skills) {
        int attack = skills.get(SkillType.ATTACK) != null ? skills.get(SkillType.ATTACK).getLevel() : 1;
        int strength = skills.get(SkillType.STRENGTH) != null ? skills.get(SkillType.STRENGTH).getLevel() : 1;
        int defence = skills.get(SkillType.DEFENCE) != null ? skills.get(SkillType.DEFENCE).getLevel() : 1;
        int hitpoints = skills.get(SkillType.HITPOINTS) != null ? skills.get(SkillType.HITPOINTS).getLevel() : 1;
        return XpUtils.calculateCombatLevel(attack, strength, defence, hitpoints);
    }
}
