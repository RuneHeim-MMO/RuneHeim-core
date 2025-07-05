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
    PLAYER_LEVEL_UP("UI_TOAST_CHALLENGE_COMPLETE", false, 1.0f, 0.9f),
    PLAYER_MAX_SKILL("ENTITY_ENDER_DRAGON_GROWL", false, 1.0f, 0.5f),
    UI_FAIL("ENTITY_VILLAGER_NO", false, 1.0f, 1.0f),
    UI_CONFIRM("ENTITY_EXPERIENCE_ORB_PICKUP", false, 1.0f, 1.5f),

    HIT_MELEE_CRITICAL("ENTITY_PLAYER_ATTACK_CRIT", false, 1.0f, 1.3f),
    HIT_MAGIC_CRITICAL("ENTITY_WITCH_THROW", false, 1.0f, 1.0f),
    HIT_RANGED_CRITICAL("ENTITY_ARROW_HIT", false, 1.0f, 0.85f),

    INVENTORY_FULL("ENTITY_ITEM_PICKUP", false, 1.0f, 0.8f),

    SMITHING_START("BLOCK_ANVIL_PLACE", false, 0.1f, 0.5f),
    SMITHING_STEP("BLOCK_ANVIL_HIT", false, 1.0f, 1.0f),
    SMITHING_SUCCESS("BLOCK_ANVIL_USE", false, 0.25f, 0.75f),

    CRAFT_START("BLOCK_WOOD_PLACE", false, 0.8f, 1.0f),
    CRAFT_STEP("ITEM_ARMOR_EQUIP_GENERIC", false, 0.8f, 0.8f),
    CRAFT_SUCCESS("ITEM_ARMOR_EQUIP_GENERIC", false, 0.8f, 0.8f),

    MINING_START("ENTITY_PLAYER_ATTACK_STRONG", false, 1.0f, 1.0f),
    MINING_STEP("BLOCK_STONE_STEP", false, 1.0f, 1.0f),
    MINING_SUCCESS("BLOCK_STONE_BREAK", false, 1.0f, 1.5f),

    WOODCUTTING_START("ITEM_AXE_STRIP", false, 1.0f, 1.0f),
    WOODCUTTING_STEP("BLOCK_WOOD_STEP", false, 1.0f, 0.5f),
    WOODCUTTING_SUCCESS("ENTITY_ZOMBIE_BREAK_WOODEN_DOOR", false, 1.0f, 0.3f),

    SMELT_START("BLOCK_FURNACE_FIRE_CRACKLE", false, 1.2f, 1.0f),
    SMELT_STEP("BLOCK_FURNACE_FIRE_CRACKLE", false, 1.0f, 1.0f),
    SMELT_SUCCESS("BLOCK_FIRE_EXTINGUISH", false, 1.0f, 1.5f),

    COOKING_START("BLOCK_FIRE_EXTINGUISH", false, 1.0f, 1.0f),
    COOKING_STEP("BLOCK_CAMPFIRE_CRACKLE", false, 1.0f, 0.5f),
    COOKING_SUCCESS("BLOCK_FIRE_EXTINGUISH", false, 1.0f, 1.3f),

    FISHING_START_ROD("ENTITY_FISHING_BOBBER_THROW", false, 0.7f, 1.0f),
    FISHING_START_REINFORCED_ROD("ENTITY_FISHING_BOBBER_THROW", false, 0.7f, 1.5f),
    FISHING_START_NET("ENTITY_PLAYER_SWIM", false, 0.7f, 1.0f),
    FISHING_START_TRAP("BLOCK_TRIPWIRE_ATTACH", false, 0.7f, 1.0f),
    FISHING_STEP("BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT", false, 0.2f, 1.0f),
    FISHING_SUCCESS("ENTITY_FISH_SWIM", false, 1.0f, 1.4f),

    CAMPFIRE_RECIPE_START("BLOCK_LAVA_POP", false, 1.0f, 1.3f),
    CAMPFIRE_RECIPE_STEP("BLOCK_BAMBOO_BREAK", false, 1.0f, 0.9f),
    CAMPFIRE_RECIPE_SUCCESS("BLOCK_BLASTFURNACE_FIRE_CRACKLE", false, 1.0f, 0.5f),
    CAMPFIRE_DESPAWN("BLOCK_FIRE_EXTINGUISH", false, 0.8f, 0.6f),

    MOUNT_EQUIP("ENTITY_HORSE_SADDLE", false, 0.45f, 1.0f),
    MOUNT_UNEQUIP("ENTITY_HORSE_SADDLE", false, 0.45f, 0.9f),

    JEWELERY_EQUIP("ITEM_ARMOR_EQUIP_CHAIN", false, 0.45f, 1.1f),
    JEWELERY_UNEQUIP("ITEM_ARMOR_EQUIP_CHAIN", false, 0.45f, 1.1f),

    FAIRY_EQUIP("ENTITY_ALLAY_AMBIENT_WITH_ITEM", false, 0.6f, 1.4f),
    FAIRY_UNEQUIP("BLOCK_AMETHYST_BLOCK_BREAK", false, 0.5f, 1.0f),

    FAIRY_FOOD_EQUIP("ENTITY_ALLAY_ITEM_GIVEN", false, 0.6f, 1.2f),
    FAIRY_FOOD_UNEQUIP("ENTITY_ALLAY_ITEM_TAKEN", false, 0.6f, 0.9f),

    FAIRY_FEED_SOUND("ENTITY_FOX_EAT", false, 0.7f, 1.2f),
    FAIRY_OUT_OF_STAMINA("ENTITY_ALLAY_DEATH", false, 0.6f, 1.0f),


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
    VILLAGER_WOMAN_YES_5("vocal_villagers:entity.villager.yes", true, 1, 1.25f),

    GOBLIN_AMBIENT("ENTITY_WITCH_AMBIENT", false, 0.8f, 1.2f),
    GOBLIN_CELEBRATE("ENTITY_EVOKER_CAST_SPELL", false, 1.0f, 1.5f),
    GOBLIN_NO("ENTITY_VILLAGER_NO", false, 1.0f, 0.9f),
    GOBLIN_HURT("ENTITY_HUSK_HURT", false, 1.0f, 0.8f),
    GOBLIN_DEATH("ENTITY_HUSK_DEATH", false, 1.0f, 0.7f),

    FUSION_MASTER_AMBIENT_1("ENTITY_WARDEN_LISTENING", false, 0.6f, 1.0f),
    FUSION_MASTER_AMBIENT_2("ENTITY_WARDEN_SNIFF", false, 0.7f, 1.1f),
    FUSION_MASTER_AMBIENT_3("ENTITY_WARDEN_AMBIENT", false, 0.8f, 0.9f),
    FUSION_MASTER_SUCCESS("ENTITY_WARDEN_ATTACK_IMPACT", false, 1.0f, 1.6f),
    FUSION_MASTER_NO("ENTITY_ELDER_GUARDIAN_CURSE", false, 0.8f, 0.7f),
    FUSION_MASTER_ROAR("ENTITY_WARDEN_ROAR", false, 0.8f, 0.7f),

    FUSION_GEM_INSERT("BLOCK_AMETHYST_BLOCK_PLACE", false, 1.0f, 1.2f),
    FUSION_SUCCESS_1("ENTITY_WITHER_SPAWN", false, 0.5f, 0.25f),
    FUSION_SUCCESS_2("ENTITY_LIGHTNING_BOLT_THUNDER", false, 1f, 1f),
    PAPER_INSERT("ITEM_BOOK_PAGE_TURN", false, 1.0f, 1.1f),
    EQUIPMENT_INSERT("ITEM_ARMOR_EQUIP_GENERIC", false, 1.0f, 0.9f);


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

    public void play(Player player, boolean global) {
        play(player.getLocation(), global ? player : null);
    }

    public void play(RunePlayer player, boolean global) {
        play(player.asBukkitPlayer().getLocation(), global ? player.asBukkitPlayer() : null);
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
