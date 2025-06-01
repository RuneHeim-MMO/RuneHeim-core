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
    FAIL_ACTION("ENTITY_VILLAGER_NO", false, 1.0f, 1.0f),
    SUCCESS_ACTION("BLOCK_BEACON_POWER_SELECT", false, 1.0f, 1.2f),
    LEVEL_UP("UI_TOAST_CHALLENGE_COMPLETE", false, 1.0f, 1.2f),
    UI_FAIL("ENTITY_VILLAGER_NO", false, 1.0f, 1.0f),
    UI_CONFIRM("ENTITY_EXPERIENCE_ORB_PICKUP", false, 1.0f, 1.5f),
    CRAFT_STEP("ITEM_ARMOR_EQUIP_GENERIC", false, 0.8f, 0.8f),
    MINING_STEP("BLOCK_STONE_STEP", false, 1.0f, 1.0f),
    MINING_SUCCESS("BLOCK_STONE_BREAK", false, 1.0f, 1.5f),
    INVENTORY_FULL("ENTITY_ITEM_PICKUP", false, 1.0f, 0.8f),
    SMELT_STEP("BLOCK_FURNACE_FIRE_CRACKLE", false, 1.0f, 1.0f),
    SMELT_SUCCESS("BLOCK_FIRE_EXTINGUISH", false, 1.0f, 1.5f),

    // AMBIENT
    VILLAGER_MAN_AMBIENT_1("vocal_villagers:entity.villager.ambient", true, 1, 0.75f),
    VILLAGER_MAN_AMBIENT_2("vocal_villagers:entity.villager.ambient", true, 1, 0.80f),
    VILLAGER_MAN_AMBIENT_3("vocal_villagers:entity.villager.ambient", true, 1, 0.90f),
    VILLAGER_MAN_AMBIENT_4("vocal_villagers:entity.villager.ambient", true, 1, 0.95f),
    VILLAGER_MAN_AMBIENT_5("vocal_villagers:entity.villager.ambient", true, 1, 1.00f),

    VILLAGER_WOMAN_AMBIENT_1("vocal_villagers:entity.villager.ambient", true, 1, 1.05f),
    VILLAGER_WOMAN_AMBIENT_2("vocal_villagers:entity.villager.ambient", true, 1, 1.10f),
    VILLAGER_WOMAN_AMBIENT_3("vocal_villagers:entity.villager.ambient", true, 1, 1.15f),
    VILLAGER_WOMAN_AMBIENT_4("vocal_villagers:entity.villager.ambient", true, 1, 1.20f),
    VILLAGER_WOMAN_AMBIENT_5("vocal_villagers:entity.villager.ambient", true, 1, 1.25f),

    // CELEBRATE
    VILLAGER_MAN_CELEBRATE_1("vocal_villagers:entity.villager.celebrate", true, 1, 0.75f),
    VILLAGER_MAN_CELEBRATE_2("vocal_villagers:entity.villager.celebrate", true, 1, 0.80f),
    VILLAGER_MAN_CELEBRATE_3("vocal_villagers:entity.villager.celebrate", true, 1, 0.90f),
    VILLAGER_MAN_CELEBRATE_4("vocal_villagers:entity.villager.celebrate", true, 1, 0.95f),
    VILLAGER_MAN_CELEBRATE_5("vocal_villagers:entity.villager.celebrate", true, 1, 1.00f),

    VILLAGER_WOMAN_CELEBRATE_1("vocal_villagers:entity.villager.celebrate", true, 1, 1.05f),
    VILLAGER_WOMAN_CELEBRATE_2("vocal_villagers:entity.villager.celebrate", true, 1, 1.10f),
    VILLAGER_WOMAN_CELEBRATE_3("vocal_villagers:entity.villager.celebrate", true, 1, 1.15f),
    VILLAGER_WOMAN_CELEBRATE_4("vocal_villagers:entity.villager.celebrate", true, 1, 1.20f),
    VILLAGER_WOMAN_CELEBRATE_5("vocal_villagers:entity.villager.celebrate", true, 1, 1.25f),

    // DEATH
    VILLAGER_MAN_HURT_1("ENTITY_VILLAGER_HURT", false, 1, 0.75f),
    VILLAGER_MAN_HURT_2("ENTITY_VILLAGER_HURT", false, 1, 0.80f),
    VILLAGER_MAN_HURT_3("ENTITY_VILLAGER_HURT", false, 1, 0.90f),
    VILLAGER_MAN_HURT_4("ENTITY_VILLAGER_HURT", false, 1, 0.95f),
    VILLAGER_MAN_HURT_5("ENTITY_VILLAGER_HURT", false, 1, 1.00f),

    VILLAGER_WOMAN_HURT_1("ENTITY_VILLAGER_HURT", false, 1, 1.05f),
    VILLAGER_WOMAN_HURT_2("ENTITY_VILLAGER_HURT", false, 1, 1.10f),
    VILLAGER_WOMAN_HURT_3("ENTITY_VILLAGER_HURT", false, 1, 1.15f),
    VILLAGER_WOMAN_HURT_4("ENTITY_VILLAGER_HURT", false, 1, 1.20f),
    VILLAGER_WOMAN_HURT_5("ENTITY_VILLAGER_HURT", false, 1, 1.25f),

    // NO
    VILLAGER_MAN_NO_1("ENTITY_VILLAGER_NO", false, 1, 0.75f),
    VILLAGER_MAN_NO_2("ENTITY_VILLAGER_NO", false, 1, 0.80f),
    VILLAGER_MAN_NO_3("ENTITY_VILLAGER_NO", false, 1, 0.90f),
    VILLAGER_MAN_NO_4("ENTITY_VILLAGER_NO", false, 1, 0.95f),
    VILLAGER_MAN_NO_5("ENTITY_VILLAGER_NO", false, 1, 1.00f),

    VILLAGER_WOMAN_NO_1("ENTITY_VILLAGER_NO", false, 1, 1.05f),
    VILLAGER_WOMAN_NO_2("ENTITY_VILLAGER_NO", false, 1, 1.10f),
    VILLAGER_WOMAN_NO_3("ENTITY_VILLAGER_NO", false, 1, 1.15f),
    VILLAGER_WOMAN_NO_4("ENTITY_VILLAGER_NO", false, 1, 1.20f),
    VILLAGER_WOMAN_NO_5("ENTITY_VILLAGER_NO", false, 1, 1.25f),

    // Villager Man Death variants
    VILLAGER_MAN_DEATH_1("ENTITY_VILLAGER_DEATH", true, 1, 0.75f),
    VILLAGER_MAN_DEATH_2("ENTITY_VILLAGER_DEATH", true, 1, 0.80f),
    VILLAGER_MAN_DEATH_3("ENTITY_VILLAGER_DEATH", true, 1, 0.90f),
    VILLAGER_MAN_DEATH_4("ENTITY_VILLAGER_DEATH", true, 1, 0.95f),
    VILLAGER_MAN_DEATH_5("ENTITY_VILLAGER_DEATH", true, 1, 1.00f),

    // Villager Woman Death variants
    VILLAGER_WOMAN_DEATH_1("ENTITY_VILLAGER_DEATH", true, 1, 1.05f),
    VILLAGER_WOMAN_DEATH_2("ENTITY_VILLAGER_DEATH", true, 1, 1.10f),
    VILLAGER_WOMAN_DEATH_3("ENTITY_VILLAGER_DEATH", true, 1, 1.15f),
    VILLAGER_WOMAN_DEATH_4("ENTITY_VILLAGER_DEATH", true, 1, 1.20f),
    VILLAGER_WOMAN_DEATH_5("ENTITY_VILLAGER_DEATH", true, 1, 1.25f),

    // TRADE
    VILLAGER_MAN_TRADE_1("vocal_villagers:entity.villager.trade", true, 1, 0.75f),
    VILLAGER_MAN_TRADE_2("vocal_villagers:entity.villager.trade", true, 1, 0.80f),
    VILLAGER_MAN_TRADE_3("vocal_villagers:entity.villager.trade", true, 1, 0.90f),
    VILLAGER_MAN_TRADE_4("vocal_villagers:entity.villager.trade", true, 1, 0.95f),
    VILLAGER_MAN_TRADE_5("vocal_villagers:entity.villager.trade", true, 1, 1.00f),

    VILLAGER_WOMAN_TRADE_1("vocal_villagers:entity.villager.trade", true, 1, 1.05f),
    VILLAGER_WOMAN_TRADE_2("vocal_villagers:entity.villager.trade", true, 1, 1.10f),
    VILLAGER_WOMAN_TRADE_3("vocal_villagers:entity.villager.trade", true, 1, 1.15f),
    VILLAGER_WOMAN_TRADE_4("vocal_villagers:entity.villager.trade", true, 1, 1.20f),
    VILLAGER_WOMAN_TRADE_5("vocal_villagers:entity.villager.trade", true, 1, 1.25f),

    // YES
    VILLAGER_MAN_YES_1("vocal_villagers:entity.villager.yes", true, 1, 0.75f),
    VILLAGER_MAN_YES_2("vocal_villagers:entity.villager.yes", true, 1, 0.80f),
    VILLAGER_MAN_YES_3("vocal_villagers:entity.villager.yes", true, 1, 0.90f),
    VILLAGER_MAN_YES_4("vocal_villagers:entity.villager.yes", true, 1, 0.95f),
    VILLAGER_MAN_YES_5("vocal_villagers:entity.villager.yes", true, 1, 1.00f),

    VILLAGER_WOMAN_YES_1("vocal_villagers:entity.villager.yes", true, 1, 1.05f),
    VILLAGER_WOMAN_YES_2("vocal_villagers:entity.villager.yes", true, 1, 1.10f),
    VILLAGER_WOMAN_YES_3("vocal_villagers:entity.villager.yes", true, 1, 1.15f),
    VILLAGER_WOMAN_YES_4("vocal_villagers:entity.villager.yes", true, 1, 1.20f),
    VILLAGER_WOMAN_YES_5("vocal_villagers:entity.villager.yes", true, 1, 1.25f);


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
