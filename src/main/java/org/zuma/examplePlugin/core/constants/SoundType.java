package org.zuma.examplePlugin.core.constants;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;

public enum SoundType {
    FAIL_ACTION("ENTITY_VILLAGER_HURT", false, 1.0f, 1.0f),
    SUCCESS_ACTION("BLOCK_BEACON_POWER_SELECT", false, 1.0f, 1.2f),
    LEVEL_UP("UI_TOAST_CHALLENGE_COMPLETE", false, 1.0f, 1.2f),
    UI_FAIL("ENTITY_VILLAGER_NO", false, 1.0f, 1.0f),
    UI_CONFIRM("ENTITY_EXPERIENCE_ORB_PICKUP", false, 1.0f, 1.5f),
    CRAFT_STEP("ITEM_ARMOR_EQUIP_GENERIC", false, 0.8f, 0.8f),
    MINING_STEP("BLOCK_STONE_STEP", false, 1.0f, 1.0f),
    MINING_SUCCESS("BLOCK_STONE_BREAK", false, 1.0f, 1.5f),
    INVENTORY_FULL("ENTITY_ITEM_PICKUP", false, 1.0f, 0.8f);

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
        play(player.getLocation(), player);
    }

    public void play(RunePlayer player) {
        play(player.getPlayer().getLocation(), player.getPlayer());
    }

    public void play(Location location) {
        play(location, null);
    }

    private void play(Location location, @Nullable Player targetPlayer) {
        SoundCategory category = SoundCategory.MASTER;

        if (isCustom) {
            playSound(location, targetPlayer, soundKey, category);
            return;
        }

        try {
            Sound builtInSound = Sound.valueOf(soundKey);
            playSound(location, targetPlayer, builtInSound, category);
        } catch (IllegalArgumentException e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Invalid built-in sound: " + soundKey);
        }
    }

    private void playSound(Location location, @Nullable Player player, Object sound, SoundCategory category) {
        if (player != null) {
            if (sound instanceof String customKey) {
                player.playSound(location, customKey, category, volume, pitch);
            } else if (sound instanceof Sound builtIn) {
                player.playSound(location, builtIn, volume, pitch);
            }
        } else {
            if (sound instanceof String customKey) {
                location.getWorld().playSound(location, customKey, category, volume, pitch);
            } else if (sound instanceof Sound builtIn) {
                location.getWorld().playSound(location, builtIn, category, volume, pitch);
            }
        }
    }
}
