package org.zuma.examplePlugin.domain.payments;

import org.zuma.examplePlugin.core.constants.Icon;

public enum SupporterLevel {
    LVL_0(-1, 0, Icon.EMPTY, "", ""),
    LVL_1(1, 25, Icon.SUPPORTER_LVL_1, "I", "§9"),
    LVL_2(25, 50, Icon.SUPPORTER_LVL_2, "II", "§b"),
    LVL_3(50, 100, Icon.SUPPORTER_LVL_3, "III", "§a"),
    LVL_4(100, 250, Icon.SUPPORTER_LVL_4, "IV", "§6"),
    LVL_5(250, 500, Icon.SUPPORTER_LVL_5, "V", "§c"),
    LVL_6(500, 1000000, Icon.SUPPORTER_LVL_6, "VI", "§d");

    public final double minAmount;
    public final double maxAmount;
    public final Icon levelIcon;
    public final String romanNumeral, colorCode;

    SupporterLevel(double minAmount, double maxAmount, Icon levelIcon, String romanNumeral, String colorCode) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.levelIcon = levelIcon;
        this.romanNumeral = romanNumeral;
        this.colorCode = colorCode;
    }

    public static SupporterLevel getLevelByAmount(double amount) {
        for (SupporterLevel level : values()) {
            if (amount >= level.minAmount && amount < level.maxAmount) {
                return level;
            }
        }

        return SupporterLevel.LVL_0;
    }

    public int getLevelNumber() {
        return this.ordinal();
    }

    public String getRomanNumeral() {
        return this.romanNumeral;
    }

    public String getColorCode() {
        return this.colorCode;
    }
}
