package org.zuma.examplePlugin.domain.skill.constants;

public enum SkillType {
    ATTACK(0, "Attack", 0, "⚔"),
    DEFENCE(1, "Defence", 0, "🛡"),
    STRENGTH(2, "Strength", 0, "💪"),
    HITPOINTS(3, "Hitpoints", 1154, "❤"),
    RANGED(4, "Ranged", 0, "🏹"),
    COOKING(7, "Cooking", 0, "🍳"),
    WOODCUTTING(8, "Woodcutting", 0, "🪓"),
    FISHING(9, "Fishing", 0, "🎣"),
    FIREMAKING(10, "Firemaking", 0, "🔥"),
    CRAFTING(11, "Crafting", 0, "🛠"),
    SMITHING(12, "Smithing", 0, "⚒"),
    MINING(13, "Mining", 0, "⛏");

    /*
        HERBLORE(15, "Herblore", 0),
        AGILITY(16, "Agility", 0),
        THIEVING(17, "Thieving", 0),
        SLAYER(18, "Slayer", 0),
        FARMING(19, "Farming", 0),
        RUNECRAFT(20, "Runecraft", 0),
        HUNTER(21, "Hunter", 0),
        CONSTRUCTION(22, "Construction", 0);
    */

    private final int key;
    private final String label;
    private final int defaultXp;
    private final String symbol;

    SkillType(int key, String label, int defaultXp, String symbol) {
        this.key = key;
        this.label = label;
        this.defaultXp = defaultXp;
        this.symbol = symbol;
    }

    public int asNumber() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public int getDefaultXp() {
        return defaultXp;
    }

    public String getSymbol() {
        return symbol;
    }
}



