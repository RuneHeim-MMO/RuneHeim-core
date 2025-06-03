package org.zuma.examplePlugin.domain.modifier.constants;

public enum ModifierType {
    MINING_BOOST("Mining Boost", "⛏"),
    FISHING_BOOST("Fishing Boost", "\uD83C\uDFA3"),
    WOODCUTTING_BOOST("Woodcutting Boost", "🪓"),

    ATTACK_STAB("Stab Attack", "🗡"),
    ATTACK_SLASH("Slash Attack", "⚔"),
    ATTACK_CRUSH("Crush Attack", "🔨"),

    DEFENCE_STAB("Stab Defence", "🛡"),
    DEFENCE_SLASH("Slash Defence", "🔰"),
    DEFENCE_CRUSH("Crush Defence", "🪖"),

    STRENGTH_BONUS("Strength Bonus", "💪"),
    RANGED_STRENGTH("Ranged Strength", "🏹");

    private final String label, symbol;

    ModifierType(String label, String symbol) {
        this.label = label;
        this.symbol = symbol;
    }

    public String getLabel() {
        return this.label;
    }


    public String getSymbol() {
        return symbol;
    }
}
