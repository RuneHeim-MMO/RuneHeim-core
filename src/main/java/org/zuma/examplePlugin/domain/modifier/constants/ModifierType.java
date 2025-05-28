package org.zuma.examplePlugin.domain.modifier.constants;

public enum ModifierType {
    MINING_BOOST("Mining Boost"),
    WOODCUTTING_BOOST("Woodcutting Boost"),

    ATTACK_STAB("Stab Attack"),
    ATTACK_SLASH("Slash Attack"),
    ATTACK_CRUSH("Crush Attack"),

    DEFENCE_STAB("Stab Defence"),
    DEFENCE_SLASH("Slash Defence"),
    DEFENCE_CRUSH("Crush Defence"),

    STRENGTH_BONUS("Strength Bonus"),
    RANGED_STRENGTH("Ranged Strength");


    private final String label;

    ModifierType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }


}
