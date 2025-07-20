package org.zuma.examplePlugin.domain.modifier.constants;

import org.zuma.examplePlugin.core.constants.Icon;

public enum ModifierType {
    MINING_BOOST("Mining Boost", Icon.PICKAXE.get()),
    WOODCUTTING_BOOST("Woodcutting Boost", Icon.AXE.get()),
    BASIC_FISHING("Basic Fishing", Icon.FISH.get()),
    REGULAR_FISHING("Regular Fishing", Icon.FISH.get()),
    ADVANCED_FISHING("Advanced Fishing", Icon.FISH.get()),
    TRAP_FISHING("Trap Fishing", Icon.FISH.get()),


    ATTACK_STAB("Stab Attack", Icon.STAB_ATTACK.get()),
    ATTACK_SLASH("Slash Attack", Icon.SWORD.get()),
    ATTACK_CRUSH("Crush Attack", Icon.ROCK.get()),

    DEFENCE_STAB("Stab Defence", Icon.STAB_DEFENCE.get()),
    DEFENCE_SLASH("Slash Defence", Icon.ARMOR.get()),
    DEFENCE_CRUSH("Crush Defence", Icon.HELMET.get()),

    STRENGTH_BONUS("Strength Bonus", Icon.STRENGTH.get()),
    RANGED_STRENGTH("Ranged Strength", Icon.RANGED_STR.get()),
    RANGED_ACCURACY("Ranged Accuracy", Icon.RANGED_ACCURACY.get());

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
