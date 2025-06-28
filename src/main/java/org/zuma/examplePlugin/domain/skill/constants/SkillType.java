package org.zuma.examplePlugin.domain.skill.constants;

import net.kyori.adventure.text.Component;
import org.zuma.examplePlugin.core.constants.Icon;

public enum SkillType {
    ATTACK(0, "Attack", 0, Icon.SWORD),
    DEFENCE(1, "Defence", 0, Icon.SHIELD),
    STRENGTH(2, "Strength", 0, Icon.STRENGTH),
    HITPOINTS(3, "Hitpoints", 1154, Icon.HEART),
    RANGED(4, "Ranged", 0, Icon.ARROW),
    COOKING(7, "Cooking", 0, Icon.EGGS),
    WOODCUTTING(8, "Woodcutting", 0, Icon.AXE),
    FISHING(9, "Fishing", 0, Icon.FISH),
    FIREMAKING(10, "Firemaking", 0, Icon.FIRE),

    CRAFTING(11, "Crafting", 0, Icon.CRAFTING),
    SMITHING(12, "Smithing", 0, Icon.ANVIL),
    MINING(13, "Mining", 0, Icon.PICKAXE);

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
    private final Icon symbol;

    SkillType(int key, String label, int defaultXp, Icon symbol) {
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
        return symbol.get();
    }

    public Component getSymbolComponent() {
        return symbol.asComponent();
    }
}



