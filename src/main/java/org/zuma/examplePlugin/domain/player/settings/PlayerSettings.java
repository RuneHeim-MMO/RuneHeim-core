package org.zuma.examplePlugin.domain.player.settings;

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

    public void toggleSetting(PlayerSetting setting) {
        settings.put(setting, !getSetting(setting));
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
