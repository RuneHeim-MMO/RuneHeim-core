package org.zuma.examplePlugin.core.utils;

public class XpUtils {
    private static final int[] XP_TABLE = new int[100]; // index 1-99 used

    static {
        int experience = 0;
        for (int level = 1; level <= 99; level++) {
            if (level > 1) {
                double value = (level - 1) + 300 * Math.pow(2, (level - 1) / 7.0);
                experience += (int) Math.floor(value / 4.0);
            }
            XP_TABLE[level] = experience;
        }
    }

    public static int getXpForLevel(int level) {
        if (level < 1 || level > 99) throw new IllegalArgumentException();
        return XP_TABLE[level];
    }

    public static int getLevelFromXp(int xp) {
        if (xp < 0) throw new IllegalArgumentException();

        for (int level = 2; level <= 99; level++) {
            if (XP_TABLE[level] > xp) return level - 1;
        }
        return 99;
    }
}
