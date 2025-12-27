package org.zuma.examplePlugin.domain.modifier.scopes.server;

import org.zuma.examplePlugin.domain.gui.constants.GUIControl;

public enum ServerModifierType {
    DOUBLE_COMBAT_XP("2x Combat XP Bomb", "§a", "double_combat_xp", 20, GUIControl.COMBAT_XP_BOMB_ICON),
    DOUBLE_SKILL_XP("2x Skill XP Bomb", "§d", "double_skill_xp", 20, GUIControl.SKILL_XP_BOMB_ICON),
    INCREASED_MOB_DROPS("Mob Loot Bomb", "§c", "mob_drops", 20, GUIControl.MOB_DROPS_BOMB_ICON),
    INCREASED_RESOURCE_DROPS("Resource Loot Bomb", "§e", "resource_drops", 20, GUIControl.RESOURCE_DROPS_BOMB_ICON),
    INCREASED_PERFECT_STRIKE("Perfect Strike Bomb", "§b", "perfect_strike", 20, GUIControl.PERFECT_STRIKE_BOMB_ICON);

    public final String label, columnName, color;
    public final int defaultDurationMinutes;
    public final GUIControl icon;

    ServerModifierType(String label, String color, String columnName, int defaultDurationMinutes, GUIControl icon) {
        this.defaultDurationMinutes = defaultDurationMinutes;
        this.columnName = columnName;
        this.label = label;
        this.icon = icon;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public int getDefaultDurationMinutes() {
        return defaultDurationMinutes;
    }

    public GUIControl getIcon() {
        return icon;
    }

    public String getColor() {
        return color;
    }

}
