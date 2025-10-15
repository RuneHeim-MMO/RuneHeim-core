package org.zuma.examplePlugin.domain.modifier.constants;

import org.zuma.examplePlugin.core.constants.Icon;

public enum ModifierType {
    MINING_BOOST("Mining Boost", "Increases efficiency of mining activities. (e.g., Base Level 1 allows mining; Level 2 doubles mining speed (base gathering duration / modifier))", Icon.PICKAXE.get(), "x", null, false),
    WOODCUTTING_BOOST("Woodcutting Boost", "Improves the speed of chopping down trees. (e.g., Base Level 1 allows woodcutting; Level 2 doubles woodcutting speed (base gathering duration / modifier))", Icon.AXE.get(), "x", null, false),
    GATHERING_LUCK("Gathering Luck", "Percentage chance to double-roll the resource node's drop table. (Applies only to nodes that require specialized tools)", Icon.CLOVER.get(), "%", null, false),

    BASIC_FISHING("Basic Fishing", "Enables simple fishing methods for catching common fish.", Icon.FISH.get(), null, null, false),
    REGULAR_FISHING("Regular Fishing", "Allows standard fishing techniques with improved results.", Icon.FISH.get(), null, null, false),
    ADVANCED_FISHING("Advanced Fishing", "Grants access to more efficient and advanced fishing methods.", Icon.FISH.get(), null, null, false),
    TRAP_FISHING("Trap Fishing", "Enables setting traps to catch bigger sea creatures.", Icon.FISH.get(), null, null, false),

    ATTACK_STAB("Stab Attack", "Boosts damage dealt with stabbing weapons.", Icon.STAB_ATTACK.get(), null, null, false),
    ATTACK_SLASH("Slash Attack", "Increases damage with slashing attacks.", Icon.SWORD.get(), null, null, false),
    ATTACK_CRUSH("Crush Attack", "Improves the effectiveness of crushing attacks.", Icon.ROCK.get(), null, null, false),

    DEFENCE_STAB("Stab Defence", "Reduces damage taken from stab attacks.", Icon.STAB_DEFENCE.get(), null, null, false),
    DEFENCE_SLASH("Slash Defence", "Lowers damage from slashing weapons.", Icon.ARMOR.get(), null, null, false),
    DEFENCE_CRUSH("Crush Defence", "Mitigates damage from blunt force attacks.", Icon.HELMET.get(), null, null, false),
    DEFENCE_RANGED("Ranged Defence", "Increases resistance against ranged attacks.", Icon.BOW.get(), null, null, false),
    DEFENCE_MAGIC("Magic Defence", "Boosts defence against magical spells.", Icon.BOW.get(), null, null, false),

    STRENGTH_BONUS("Strength Bonus", "Increases overall melee damage.", Icon.STRENGTH.get(), null, null, false),
    RANGED_STRENGTH("Ranged Strength", "Enhances the power of ranged weapon attacks.", Icon.RANGED_STR.get(), null, null, false),
    RANGED_ACCURACY("Ranged Accuracy", "Improves hit chance with ranged weapons.", Icon.RANGED_ACCURACY.get(), null, null, false),
    CLEAVE_DISTANCE("Cleave Distance", "Extends the range of melee cleave attacks, allowing you to hit additional enemies in front of you.", Icon.CLEAVE.get(), null, null, false),
    FLYING_SPEED("Flying Speed", "", Icon.FLIGHT.get(), "%", null, true),

    XP_MULTIPLIER("XP Boost", "Increases experience gain rate by a multiplier.", Icon.EPIC.get(), "x", null, true),
    COMBAT_XP_MULTIPLIER("Combat XP Boost", "Increases experience gain rate by a multiplier.", Icon.EPIC.get(), "x", null, true),
    SKILLING_XP_MULTIPLIER("Skilling XP Boost", "Increases experience gain rate by a multiplier.", Icon.EPIC.get(), "x", null, true);

    private final String label, description, symbol, numSuffix, numPrefix;
    private final boolean special;

    ModifierType(String label, String description, String symbol, String numSuffix, String numPrefix, boolean isSpecialModifier) {
        this.label = label;
        this.description = description;
        this.symbol = symbol;
        this.numSuffix = numSuffix;
        this.numPrefix = numPrefix;
        this.special = isSpecialModifier;
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

    public boolean isSpecial() {
        return special;
    }
}
