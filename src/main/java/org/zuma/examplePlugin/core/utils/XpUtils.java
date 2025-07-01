package org.zuma.examplePlugin.core.utils;

import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.EnumMap;

public class XpUtils {
    private static final int MAX_COMBAT_LEVEL = 110; // Can be up to 113, capped by formula.
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
        if (level < 1 || level > MAX_LEVEL)
            throw new IllegalArgumentException("Level should be between 1- " + MAX_LEVEL);
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
        // Defensive base including defence and hitpoints (no prayer)
        double base = 0.25 * (defence + hitpoints);

        // Melee offense calculation
        double melee = 0.325 * (attack + strength);

        // Combat level is base plus melee offense
        double combatLevel = base + melee;

        combatLevel = Math.max(3, Math.min(MAX_COMBAT_LEVEL, combatLevel));

        return (int) Math.floor(combatLevel);
    }

    public static int getCombatLevelFromSkills(EnumMap<SkillType, Skill> skills) {
        int attack = skills.get(SkillType.ATTACK) != null ? skills.get(SkillType.ATTACK).getBaseLevel() : 1;
        int strength = skills.get(SkillType.STRENGTH) != null ? skills.get(SkillType.STRENGTH).getBaseLevel() : 1;
        int defence = skills.get(SkillType.DEFENCE) != null ? skills.get(SkillType.DEFENCE).getBaseLevel() : 1;
        int hitpoints = skills.get(SkillType.HITPOINTS) != null ? skills.get(SkillType.HITPOINTS).getBaseLevel() : 1;
        return XpUtils.calculateCombatLevel(attack, strength, defence, hitpoints);
    }
}
