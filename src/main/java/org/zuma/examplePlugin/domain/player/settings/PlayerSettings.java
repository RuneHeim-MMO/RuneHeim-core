package org.zuma.examplePlugin.domain.player.settings;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.player.PlayerSettingUpdateEvent;

import java.util.EnumMap;
import java.util.HashMap;

public class PlayerSettings {
    private final EnumMap<PlayerSetting, Boolean> settings;

    public PlayerSettings() {
        this.settings = new EnumMap<>(PlayerSetting.class);
        for (PlayerSetting setting : PlayerSetting.values()) {
            settings.put(setting, setting.getDefaultValue());
        }
    }

    public boolean getSetting(PlayerSetting setting) {
        return settings.getOrDefault(setting, setting.getDefaultValue());
    }

    public void setSetting(PlayerSetting setting, boolean value) {
        settings.put(setting, value);
    }

    public void toggleSetting(RunePlayer player, PlayerSetting setting) {
        boolean newValue = !getSetting(setting);
        settings.put(setting, newValue);
        Bukkit.getPluginManager().callEvent(new PlayerSettingUpdateEvent(player, setting, newValue));
        AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.SETTINGS_TOGGLED, 1);
    }

    public HashMap<String, Boolean> toMap() {
        HashMap<String, Boolean> map = new HashMap<>();
        for (PlayerSetting setting : PlayerSetting.values()) {
            map.put(setting.name(), getSetting(setting));
        }
        return map;
    }

    public EnumMap<PlayerSetting, Boolean> getAllSettings() {
        return settings;
    }

}
