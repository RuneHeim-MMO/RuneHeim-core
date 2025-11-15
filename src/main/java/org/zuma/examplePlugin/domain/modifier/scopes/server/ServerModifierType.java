package org.zuma.examplePlugin.domain.modifier.scopes.server;

import org.zuma.examplePlugin.domain.gui.constants.GUIControl;

public enum ServerModifierType {
    DOUBLE_COMBAT_XP("2x Combat XP Bomb", "double_combat_xp", 20, GUIControl.COMBAT_XP_BOMB_ICON),
    DOUBLE_SKILL_XP("2x Skill XP Bomb", "double_skill_xp", 20, GUIControl.SKILL_XP_BOMB_ICON),
    INCREASED_MOB_DROPS("Mob Loot Bomb", "mob_drops", 20, GUIControl.MOB_DROPS_BOMB_ICON),
    INCREASED_RESOURCE_DROPS("Resource Loot Bomb", "resource_drops", 20, GUIControl.RESOURCE_DROPS_BOMB_ICON),
    INCREASED_PERFECT_STRIKE("Perfect Strike Bomb", "perfect_strike", 20, GUIControl.PERFECT_STRIKE_BOMB_ICON);

    public final String label, columnName;
    public final int defaultDurationMinutes;
    public final GUIControl icon;

    ServerModifierType(String label, String columnName, int defaultDurationMinutes, GUIControl icon) {
        this.defaultDurationMinutes = defaultDurationMinutes;
        this.columnName = columnName;
        this.label = label;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public String getColumnName() {
        return columnName;
    }

    public int getDefaultDurationMinutes() {
        return defaultDurationMinutes;
    }

    public GUIControl getIcon() {
        return icon;
    }

}
