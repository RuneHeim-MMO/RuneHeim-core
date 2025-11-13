package org.zuma.examplePlugin.domain.modifier.scopes.server;

public enum ServerModifierType {
    DOUBLE_COMBAT_XP("2x Combat XP Bomb", "double_combat_xp", 20),
    DOUBLE_SKILL_XP("2x Skill XP Bomb", "double_skill_xp", 20),
    INCREASED_MOB_DROPS("Mob Loot Bomb", "mob_drops", 20),
    INCREASED_RESOURCE_DROPS("Resource Loot Bomb", "resource_drops", 20),
    INCREASED_PERFECT_STRIKE("Perfect Strike Bomb", "perfect_strike", 20);

    public final String label, columnName;
    public final int defaultDurationMinutes;

    ServerModifierType(String label, String columnName, int defaultDurationMinutes) {
        this.defaultDurationMinutes = defaultDurationMinutes;
        this.columnName = columnName;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getColumnName() {
        return columnName;
    }


}
