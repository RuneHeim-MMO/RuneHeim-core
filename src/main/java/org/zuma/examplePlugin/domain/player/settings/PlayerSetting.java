package org.zuma.examplePlugin.domain.player.settings;

import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public enum PlayerSetting {
    AUTOMATICALLY_TRACK_QUESTS("Track quests automatically", "Whether to track new quests", true, "Track", "Do not track", GUIControl.SETTINGS_TRACK_QUESTS, null),
    SHOW_BASE_PREFIX("Show Base Prefix", "Visibility of your staff rank in tab/chat", true, "Show", "Hide", GUIControl.SETTINGS_BASE_PREFIX, Permission.TOGGLE_STAFF_PREFIX),
    SHOW_UPGRADED_PREFIX("Show Upgraded Prefix", "Visibility of your donator rank in tab/chat", true, "Show", "Hide", GUIControl.SETTINGS_UPGRADED_PREFIX, null),
    SHOW_SUPPORTER_PREFIX("Show Supporter Level Prefix", "Visibility of your supporter gem rank in tab/chat", true, "Show", "Hide", GUIControl.SETTINGS_SUPPORTER_PREFIX, null),
    SHOW_MEGAPHONE_MESSAGES("Show Megaphone Messages", "Visibility of global messages sent by players", true, "Show", "Hide", GUIControl.SETTINGS_MEGAPHONE_MESSAGES, null),
    SHOW_DIALOG_HOLOGRAMS("Dialog Mode", "Dialog display mode", true, "Holograms", "Chat", GUIControl.SETTINGS_DIALOG_HOLOGRAMS, null),
    FAST_DIALOG("Dialog Text Speed", "Whether pauses between dialog texts are faster or slower", true, "Faster", "Slower", GUIControl.SETTINGS_DIALOG_SPEED, null),
    SHOW_LOCATION("Show Location In Hiscores", "Whether to hide your location in hiscores", true, "Show", "Hide", GUIControl.SETTINGS_LOCATION_HISCORES, null),
    ENABLE_BOMB_TRACKER("Bomb Tracker Notifications", "Disable/enable other server bomb notifications", true, "Enabled", "Disabled", GUIControl.SETTINGS_BOMB_TRACKER, Permission.BONUS_BOMB_TRACKER);

    private final String displayName, description;
    private final boolean defaultValue;
    private final String trueDisplay;
    private final String falseDisplay;
    private final GUIControl icon;
    private final Permission visibilityPermission;

    PlayerSetting(String displayName, String description, boolean defaultValue, String trueDisplay, String falseDisplay, GUIControl icon, Permission visibilityPermission) {
        this.displayName = displayName;
        this.description = description;
        this.defaultValue = defaultValue;
        this.trueDisplay = trueDisplay;
        this.falseDisplay = falseDisplay;
        this.icon = icon;
        this.visibilityPermission = visibilityPermission;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }

    public String getTrueDisplay() {
        return trueDisplay;
    }

    public String getFalseDisplay() {
        return falseDisplay;
    }

    public GUIControl getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public boolean isVisibleTo(RunePlayer player) {
        if (visibilityPermission == null) {
            return true;
        }


        for (String permissionNode : visibilityPermission.getNodes()) {
            boolean has = player.asBukkitPlayer().hasPermission(permissionNode);
            if (has == true) {
                return true;
            }
        }


        return false;
    }
}
