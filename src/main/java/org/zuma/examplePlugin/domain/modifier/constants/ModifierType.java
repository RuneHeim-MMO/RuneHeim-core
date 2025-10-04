package org.zuma.examplePlugin.domain.modifier.constants;

import org.zuma.examplePlugin.core.constants.Icon;

public enum ModifierType {
    MINING_BOOST("Mining Boost", "Increases efficiency of mining activities. (e.g., Base Level 1 allows mining; Level 2 doubles mining speed (base gathering duration / modifier))", Icon.PICKAXE.get(), "x", null),
    WOODCUTTING_BOOST("Woodcutting Boost", "Improves the speed of chopping down trees. (e.g., Base Level 1 allows woodcutting; Level 2 doubles woodcutting speed (base gathering duration / modifier))", Icon.AXE.get(), "x", null),
    GATHERING_LUCK("Gathering Luck", "Percentage chance to double-roll the resource node's drop table. (Applies only to nodes that require specialized tools)", Icon.CLOVER.get(), "%", null),

    BASIC_FISHING("Basic Fishing", "Enables simple fishing methods for catching common fish.", Icon.FISH.get(), null, null),
    REGULAR_FISHING("Regular Fishing", "Allows standard fishing techniques with improved results.", Icon.FISH.get(), null, null),
    ADVANCED_FISHING("Advanced Fishing", "Grants access to more efficient and advanced fishing methods.", Icon.FISH.get(), null, null),
    TRAP_FISHING("Trap Fishing", "Enables setting traps to catch bigger sea creatures.", Icon.FISH.get(), null, null),

    ATTACK_STAB("Stab Attack", "Boosts damage dealt with stabbing weapons.", Icon.STAB_ATTACK.get(), null, null),
    ATTACK_SLASH("Slash Attack", "Increases damage with slashing attacks.", Icon.SWORD.get(), null, null),
    ATTACK_CRUSH("Crush Attack", "Improves the effectiveness of crushing attacks.", Icon.ROCK.get(), null, null),

    DEFENCE_STAB("Stab Defence", "Reduces damage taken from stab attacks.", Icon.STAB_DEFENCE.get(), null, null),
    DEFENCE_SLASH("Slash Defence", "Lowers damage from slashing weapons.", Icon.ARMOR.get(), null, null),
    DEFENCE_CRUSH("Crush Defence", "Mitigates damage from blunt force attacks.", Icon.HELMET.get(), null, null),
    DEFENCE_RANGED("Ranged Defence", "Increases resistance against ranged attacks.", Icon.BOW.get(), null, null),
    DEFENCE_MAGIC("Magic Defence", "Boosts defence against magical spells.", Icon.BOW.get(), null, null),

    STRENGTH_BONUS("Strength Bonus", "Increases overall melee damage.", Icon.STRENGTH.get(), null, null),
    RANGED_STRENGTH("Ranged Strength", "Enhances the power of ranged weapon attacks.", Icon.RANGED_STR.get(), null, null),
    RANGED_ACCURACY("Ranged Accuracy", "Improves hit chance with ranged weapons.", Icon.RANGED_ACCURACY.get(), null, null),
    CLEAVE_DISTANCE("Cleave Distance", "Extends the range of melee cleave attacks, allowing you to hit additional enemies in front of you.", Icon.CLEAVE.get(), null, null),
    FLYING_SPEED("Flying Speed", "", Icon.CLEAVE.get(), "%", null);

    private final String label, description, symbol, numSuffix, numPrefix;

    ModifierType(String label, String description, String symbol, String numSuffix, String numPrefix) {
        this.label = label;
        this.description = description;
        this.symbol = symbol;
        this.numSuffix = numSuffix;
        this.numPrefix = numPrefix;
    }

    public String getLabel() {
        return this.label;
    }


    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

    public String getNumberSuffix() {
        return numSuffix;
    }

    public String getNumberPrefix() {
        return numPrefix;
    }
}
