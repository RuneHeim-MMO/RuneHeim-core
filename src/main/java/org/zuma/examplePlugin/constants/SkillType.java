package org.zuma.examplePlugin.constants;

public enum SkillType {
    STRENGTH(1, "Strength", 83),
    ATTACK(2, "Attack", 83),
    DEFENCE(3, "Defence", 83),
    HITPOINTS(4, "Hitpoints", 1000);


    private final int key;
    private final String label;
    private final int defaultXp;

    SkillType(int key, String label, int defaultXp) {
        this.key = key;
        this.label = label;
        this.defaultXp = defaultXp;
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

}
