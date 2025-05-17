package org.zuma.examplePlugin.constants;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.logger.constants.LogLevel;

public enum SoundType {
    FAIL_ACTION("ENTITY_VILLAGER_HURT", false, 1.0f, 1.0f),
    SUCCESS_ACTION("BLOCK_BEACON_POWER_SELECT", false, 1.0f, 1.2f),
    LEVEL_UP("UI_TOAST_CHALLENGE_COMPLETE", false, 1.0f, 1.2f),
    UI_FAIL("ENTITY_VILLAGER_NO", false, 1.0f, 1.0f),
    UI_CONFIRM("ENTITY_EXPERIENCE_ORB_PICKUP", false, 1.0f, 1.5f),
    CRAFT_STEP("ITEM_ARMOR_EQUIP_GENERIC", false, 0.8f, 0.8f);

    private final String soundKey;
    private final boolean isCustom;
    private final float volume;
    private final float pitch;

    SoundType(String soundKey, boolean isCustom, float volume, float pitch) {
        this.soundKey = soundKey;
        this.isCustom = isCustom;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void play(Player player) {
        if (isCustom) {
            player.playSound(player.getLocation(), soundKey, SoundCategory.MASTER, volume, pitch);
        } else {
            try {
                Sound builtInSound = Sound.valueOf(soundKey);
                player.playSound(player.getLocation(), builtInSound, volume, pitch);
            } catch (IllegalArgumentException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Invalid built-in sound: " + soundKey);
            }
        }
    }
}
