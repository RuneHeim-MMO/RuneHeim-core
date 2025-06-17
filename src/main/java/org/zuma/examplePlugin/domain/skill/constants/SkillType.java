package org.zuma.examplePlugin.domain.skill.constants;

import org.zuma.examplePlugin.core.constants.Icon;

public enum SkillType {
    ATTACK(0, "Attack", 0, Icon.SWORD.get()),
    DEFENCE(1, "Defence", 0, Icon.SHIELD.get()),
    STRENGTH(2, "Strength", 0, Icon.STRENGTH.get()),
    HITPOINTS(3, "Hitpoints", 1154, Icon.HEART.get()),
    RANGED(4, "Ranged", 0, Icon.ARROW.get()),
    COOKING(7, "Cooking", 0, Icon.EGGS.get()),
    WOODCUTTING(8, "Woodcutting", 0, Icon.AXE.get()),
    FISHING(9, "Fishing", 0, Icon.FISH.get()),
    FIREMAKING(10, "Firemaking", 0, Icon.FIRE.get()),

    CRAFTING(11, "Crafting", 0, Icon.CRAFTING.get()),
    SMITHING(12, "Smithing", 0, Icon.ANVIL.get()),
    MINING(13, "Mining", 0, Icon.PICKAXE.get());

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



