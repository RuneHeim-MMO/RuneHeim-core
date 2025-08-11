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
    INTERRUPT_ACTION("ENTITY_ARMOR_STAND_BREAK", false, 1.0f, 1.0f),
    FAIL_ACTION("ENTITY_VILLAGER_NO", false, 1.0f, 1.0f),
    SUCCESS_ACTION("BLOCK_BEACON_POWER_SELECT", false, 1.0f, 1.2f),
    PLAYER_LEVEL_UP("UI_TOAST_CHALLENGE_COMPLETE", false, 1.0f, 0.9f),
    PLAYER_MAX_SKILL("ENTITY_ENDER_DRAGON_GROWL", false, 1.0f, 0.5f),
    UI_FAIL("ENTITY_VILLAGER_NO", false, 1.0f, 1.0f),
    UI_CONFIRM("ENTITY_EXPERIENCE_ORB_PICKUP", false, 1.0f, 1.5f),
    UI_CLICK("UI_STONECUTTER_SELECT_RECIPE", false, 1.0f, 1.2f),

    DIALOG_TYPEWRITER_SOUND("BLOCK_TRIPWIRE_CLICK_ON", false, 0.25f, 2f),
    DIALOG_CHOICE_SOUND("BLOCK_BELL_USE", false, 0.25f, 2f),

    TERRITORY_DISCOVERY("UI_CARTOGRAPHY_TABLE_TAKE_RESULT", false, 1f, 0.9f),
    TERRITORY_ENTER("BLOCK_AMETHYST_BLOCK_CHIME", false, 1.5f, 0.5f),

    LUCKY_DROP_TABLE_SECOND_ROLL("BLOCK_ENCHANTMENT_TABLE_USE", false, 1f, 1f),
    HIT_MELEE_CRITICAL("ENTITY_PLAYER_ATTACK_CRIT", false, 1.0f, 1.3f),
    HIT_MAGIC_CRITICAL("ENTITY_WITCH_THROW", false, 1.0f, 1.0f),
    HIT_RANGED_CRITICAL("ENTITY_ARROW_HIT", false, 1.0f, 0.85f),

    HIT_MELEE_REGULAR("ENTITY_PLAYER_ATTACK_STRONG", false, 1.0f, 1.0f),
    HIT_MAGIC_REGULAR("ENTITY_EVOKER_CAST_SPELL", false, 1.0f, 1.0f),
    HIT_RANGED_REGULAR("ENTITY_ARROW_HIT", false, 1.0f, 1.0f),

    HIT_MELEE_MISS("runequest.attack.sweep", true, 1f, 1f),
    HIT_MAGIC_MISS("ENTITY_BLAZE_SHOOT", false, 0.5f, 0.8f),
    HIT_RANGED_MISS("ENTITY_SNOWBALL_THROW", false, 0.5f, 1.1f),

    BOW_SHOT_TOO_EARLY("BLOCK_CHEST_LOCKED", false, 0.6f, 2.5f),

    GUARD_TARGETED_MOB_SOUND("BLOCK_END_PORTAL_FRAME_FILL", false, 0.6f, 1.8f),
    GUARD_ENGAGE("ENTITY_PILLAGER_AMBIENT", false, 1.0f, 1.0f),
    GUARD_DISENGAGE("ENTITY_VINDICATOR_CELEBRATE", false, 0.7f, 0.75f),
    GUARD_DISENGAGE_REST("ENTITY_VILLAGER_YES", false, 0.6f, 0.6f),

    INVENTORY_FULL("ENTITY_ITEM_PICKUP", false, 1.0f, 0.8f),

    SMITHING_START("BLOCK_ANVIL_PLACE", false, 0.1f, 0.5f),
    SMITHING_STEP("BLOCK_ANVIL_HIT", false, 1.0f, 1.0f),
    SMITHING_SUCCESS("BLOCK_ANVIL_USE", false, 0.25f, 0.75f),

    COINS_DROP("runequest.coins.drop", true, 1f, 1f),
    SHOP_BUY_WITH_CUSTOM_CURRENCY("item.trident.return", true, 1f, 1f),

    CRAFT_START("BLOCK_WOOD_PLACE", false, 0.8f, 1.0f),
    CRAFT_STEP("ITEM_ARMOR_EQUIP_GENERIC", false, 0.8f, 0.8f),
    CRAFT_SUCCESS("ITEM_ARMOR_EQUIP_GENERIC", false, 0.8f, 0.8f),

    MINING_START("ENTITY_PLAYER_ATTACK_STRONG", false, 1.0f, 1.0f),
    MINING_STEP("runequest.skills.mine_step", true, 1.0f, 1.0f),
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
    FISHING_STEP("BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT", false, 1f, 1.0f),
    FISHING_SUCCESS("ENTITY_FISH_SWIM", false, 1.0f, 1.4f),

    CAMPFIRE_RECIPE_START("BLOCK_LAVA_POP", false, 1.0f, 1.3f),
    CAMPFIRE_RECIPE_STEP("BLOCK_BAMBOO_BREAK", false, 1.0f, 0.9f),
    CAMPFIRE_RECIPE_SUCCESS("BLOCK_BLASTFURNACE_FIRE_CRACKLE", false, 1.0f, 0.5f),
    CAMPFIRE_DESPAWN("BLOCK_FIRE_EXTINGUISH", false, 0.8f, 0.6f),

    SPINNING_WHEEL_RECIPE_START("BLOCK_GRINDSTONE_USE", false, 1.0f, 1.1f),
    SPINNING_WHEEL_RECIPE_STEP("BLOCK_BARREL_OPEN", false, 1.0f, 1.0f),
    SPINNING_WHEEL_RECIPE_SUCCESS("ENTITY_ITEM_FRAME_REMOVE_ITEM", false, 1.0f, 1.1f),

    MOUNT_EQUIP("ENTITY_HORSE_SADDLE", false, 0.45f, 1.0f),
    MOUNT_UNEQUIP("ENTITY_HORSE_SADDLE", false, 0.45f, 0.9f),

    JEWELERY_EQUIP("ITEM_ARMOR_EQUIP_CHAIN", false, 0.45f, 1.1f),
    JEWELERY_UNEQUIP("ITEM_ARMOR_EQUIP_CHAIN", false, 0.45f, 1.1f),

    FAIRY_EQUIP("ENTITY_ALLAY_AMBIENT_WITH_ITEM", false, 0.6f, 1.4f),
    FAIRY_UNEQUIP("BLOCK_AMETHYST_BLOCK_BREAK", false, 0.5f, 1.0f),

    FAIRY_FOOD_EQUIP("ENTITY_ALLAY_ITEM_GIVEN", false, 0.6f, 1.2f),
    FAIRY_FOOD_UNEQUIP("ENTITY_ALLAY_ITEM_TAKEN", false, 0.6f, 0.9f),

    ARROW_EQUIP("ITEM_ARMOR_EQUIP_LEATHER", false, 0.7f, 1.4f),
    ARROW_UNEQUIP("ITEM_ARMOR_EQUIP_LEATHER", false, 0.7f, 0.9f),

    FAIRY_FEED_SOUND("ENTITY_FOX_EAT", false, 0.7f, 1.2f),
    FAIRY_OUT_OF_STAMINA("ENTITY_ALLAY_DEATH", false, 0.6f, 1.0f),
    FAIRY_UPGRADE("ENTITY_ILLUSIONER_PREPARE_MIRROR", false, 0.8f, 0.5f),

    SHOP_MOVE_ITEM("ITEM_ARMOR_EQUIP_GENERIC", false, 0.6f, 1.2f),

    PICKING_UP_PLANT_START("BLOCK_ROOTED_DIRT_BREAK", false, 1.0f, 1.0f),
    PICKING_UP_PLANT_END("BLOCK_MOSS_BREAK", false, 1.0f, 0.9f),

    SCRATCH_TICKET_START("ITEM_BOOK_PAGE_TURN", false, 1.0f, 1.0f),
    SCRATCH_TICKET_FINISH("BLOCK_ANCIENT_DEBRIS_PLACE", false, 1.0f, 0.9f),

    XP_LAMP_INVENTORY_OPEN("BLOCK_ENCHANTMENT_TABLE_USE", false, 1.0f, 1.5f),
    XP_LAMP_SKILL_SELECT("ENTITY_PLAYER_LEVELUP", false, 1.0f, 1.2f),

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
    GOBLIN_ATTACK("ENTITY_VINDICATOR_AMBIENT", false, 1.0f, 1.0f),
    GOBLIN_HURT("ENTITY_HUSK_HURT", false, 1.0f, 0.8f),
    GOBLIN_DEATH("ENTITY_HUSK_DEATH", false, 1.0f, 0.7f),

    SHEEP_AMBIENT("ENTITY_SHEEP_AMBIENT", false, 1.0f, 1.0f),
    SHEEP_SUCCESS("ENTITY_SHEEP_SHEAR", false, 1.0f, 1.2f),
    SHEEP_NO("ENTITY_SHEEP_AMBIENT", false, 1.0f, 0.6f),
    SHEEP_HURT("ENTITY_SHEEP_HURT", false, 1.0f, 1.0f),
    SHEEP_DEATH("ENTITY_SHEEP_DEATH", false, 1.0f, 1.0f),

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
    EQUIPMENT_INSERT("ITEM_ARMOR_EQUIP_GENERIC", false, 1.0f, 0.9f),

    BOAR_AMBIENT_1("ENTITY_HOGLIN_AMBIENT", false, 0.9f, 0.8f),
    BOAR_HURT_1("ENTITY_HOGLIN_HURT", false, 0.9f, 0.8f),
    BOAR_DEATH_1("ENTITY_HOGLIN_DEATH", false, 0.9f, 0.8f),
    BOAR_ATTACK_1("ENTITY_HOGLIN_ATTACK", false, 0.9f, 0.8f),

    BOAR_AMBIENT_2("ENTITY_HOGLIN_AMBIENT", false, 0.9f, 0.65f),
    BOAR_HURT_2("ENTITY_HOGLIN_HURT", false, 0.9f, 0.65f),
    BOAR_DEATH_2("ENTITY_HOGLIN_DEATH", false, 0.9f, 0.65f),
    BOAR_ATTACK_2("ENTITY_HOGLIN_ATTACK", false, 0.9f, 0.65f),

    WOLF_AMBIENT_1("ENTITY_WOLF_AMBIENT", false, 0.95f, 0.95f),
    WOLF_HURT_1("ENTITY_WOLF_HURT", false, 0.95f, 0.95f),
    WOLF_DEATH_1("ENTITY_WOLF_DEATH", false, 0.95f, 0.95f),
    WOLF_ATTACK_1("ENTITY_WOLF_GROWL", false, 0.95f, 0.95f),

    WOLF_AMBIENT_2("ENTITY_WOLF_AMBIENT", false, 0.95f, 0.85f),
    WOLF_HURT_2("ENTITY_WOLF_HURT", false, 0.95f, 0.85f),
    WOLF_DEATH_2("ENTITY_WOLF_DEATH", false, 0.95f, 0.85f),
    WOLF_ATTACK_2("ENTITY_WOLF_GROWL", false, 0.95f, 0.85f),

    CHICKEN_AMBIENT("ENTITY_CHICKEN_AMBIENT", false, 1.0f, 1.2f),
    CHICKEN_ATTACK("ENTITY_CHICKEN_HURT", false, 1.0f, 1.4f),
    CHICKEN_HURT("ENTITY_CHICKEN_HURT", false, 1.0f, 1.0f),
    CHICKEN_DEATH("ENTITY_CHICKEN_DEATH", false, 1.0f, 0.9f),

    RAT_AMBIENT("ENTITY_BAT_AMBIENT", false, 1.0f, 2.0f),
    RAT_ATTACK("ENTITY_SILVERFISH_STEP", false, 1.0f, 1.4f),
    RAT_HURT("ENTITY_RABBIT_HURT", false, 1.0f, 1.3f),
    RAT_DEATH("ENTITY_RABBIT_DEATH", false, 1.0f, 0.9f),

    AXOLOTL_AMBIENT("ENTITY_AXOLOTL_IDLE_WATER", false, 0.8f, 1.6f),
    AXOLOTL_ATTACK("ENTITY_AXOLOTL_ATTACK", false, 1.0f, 1.4f),
    AXOLOTL_HURT("ENTITY_AXOLOTL_HURT", false, 1.0f, 1.2f),
    AXOLOTL_DEATH("ENTITY_AXOLOTL_DEATH", false, 1.0f, 1.0f),

    ARMADILLO_AMBIENT("ENTITY_TURTLE_AMBIENT_LAND", false, 0.9f, 1.1f),
    ARMADILLO_ATTACK("BLOCK_CHAIN_PLACE", false, 1.0f, 0.8f),
    ARMADILLO_HURT("ENTITY_TURTLE_HURT", false, 1.0f, 1.0f),
    ARMADILLO_DEATH("ENTITY_TURTLE_DEATH", false, 1.0f, 0.9f),

    BEE_AMBIENT("ENTITY_BEE_LOOP", false, 0.7f, 2.0f),
    BEE_ATTACK("ENTITY_BEE_STING", false, 1.0f, 1.6f),
    BEE_HURT("ENTITY_BEE_HURT", false, 1.0f, 1.3f),
    BEE_DEATH("ENTITY_BEE_DEATH", false, 1.0f, 1.1f),

    CAT_AMBIENT("ENTITY_CAT_AMBIENT", false, 0.8f, 1.2f),
    CAT_ATTACK("ENTITY_CAT_HISS", false, 1.0f, 1.1f),
    CAT_HURT("ENTITY_CAT_HURT", false, 1.0f, 1.0f),
    CAT_DEATH("ENTITY_CAT_DEATH", false, 1.0f, 0.9f),

    DONKEY_AMBIENT("ENTITY_DONKEY_AMBIENT", false, 1.0f, 1.0f),
    DONKEY_ATTACK("ENTITY_DONKEY_ANGRY", false, 1.0f, 0.9f),
    DONKEY_HURT("ENTITY_DONKEY_HURT", false, 1.0f, 0.95f),
    DONKEY_DEATH("ENTITY_DONKEY_DEATH", false, 1.0f, 0.9f),

    FOX_AMBIENT("ENTITY_FOX_AMBIENT", false, 0.8f, 1.6f),
    FOX_ATTACK("ENTITY_FOX_BITE", false, 1.0f, 1.4f),
    FOX_HURT("ENTITY_FOX_HURT", false, 1.0f, 1.1f),
    FOX_DEATH("ENTITY_FOX_DEATH", false, 1.0f, 1.0f),

    FROG_AMBIENT("ENTITY_FROG_AMBIENT", false, 0.9f, 0.9f),
    FROG_ATTACK("ENTITY_FROG_STEP", false, 1.0f, 1.1f),
    FROG_HURT("ENTITY_FROG_HURT", false, 1.0f, 1.0f),
    FROG_DEATH("ENTITY_FROG_DEATH", false, 1.0f, 0.95f),

    GOAT_AMBIENT("ENTITY_GOAT_AMBIENT", false, 1.0f, 1.0f),
    GOAT_ATTACK("ENTITY_GOAT_LONG_JUMP", false, 1.0f, 1.2f),
    GOAT_HURT("ENTITY_GOAT_HURT", false, 1.0f, 1.0f),
    GOAT_DEATH("ENTITY_GOAT_DEATH", false, 1.0f, 0.95f),

    HOGLIN_AMBIENT("ENTITY_HOGLIN_AMBIENT", false, 1.0f, 0.8f),
    HOGLIN_ATTACK("ENTITY_HOGLIN_ATTACK", false, 1.0f, 0.9f),
    HOGLIN_HURT("ENTITY_HOGLIN_HURT", false, 1.0f, 0.8f),
    HOGLIN_DEATH("ENTITY_HOGLIN_DEATH", false, 1.0f, 0.75f),

    HUSK_AMBIENT("ENTITY_HUSK_AMBIENT", false, 1.0f, 0.7f),
    HUSK_ATTACK("ENTITY_HUSK_STEP", false, 1.0f, 0.8f),
    HUSK_HURT("ENTITY_HUSK_HURT", false, 1.0f, 0.75f),
    HUSK_DEATH("ENTITY_HUSK_DEATH", false, 1.0f, 0.7f),

    LLAMA_AMBIENT("ENTITY_LLAMA_AMBIENT", false, 1.0f, 1.0f),
    LLAMA_ATTACK("ENTITY_LLAMA_SPIT", false, 1.0f, 1.2f),
    LLAMA_HURT("ENTITY_LLAMA_HURT", false, 1.0f, 1.0f),
    LLAMA_DEATH("ENTITY_LLAMA_DEATH", false, 1.0f, 0.95f),

    PANDA_AMBIENT("ENTITY_PANDA_AMBIENT", false, 1.0f, 0.9f),
    PANDA_ATTACK("ENTITY_PANDA_BITE", false, 1.0f, 1.0f),
    PANDA_HURT("ENTITY_PANDA_HURT", false, 1.0f, 0.9f),
    PANDA_DEATH("ENTITY_PANDA_DEATH", false, 1.0f, 0.85f),

    PIG_AMBIENT("ENTITY_PIG_AMBIENT", false, 1.0f, 1.2f),
    PIG_ATTACK("ENTITY_PIG_STEP", false, 1.0f, 1.1f),
    PIG_HURT("ENTITY_PIG_HURT", false, 1.0f, 1.0f),
    PIG_DEATH("ENTITY_PIG_DEATH", false, 1.0f, 0.95f),

    POLAR_BEAR_AMBIENT("ENTITY_POLAR_BEAR_AMBIENT", false, 1.0f, 0.8f),
    POLAR_BEAR_ATTACK("ENTITY_POLAR_BEAR_WARNING", false, 1.0f, 0.9f),
    POLAR_BEAR_HURT("ENTITY_POLAR_BEAR_HURT", false, 1.0f, 0.8f),
    POLAR_BEAR_DEATH("ENTITY_POLAR_BEAR_DEATH", false, 1.0f, 0.75f),

    RABBIT_AMBIENT("ENTITY_RABBIT_AMBIENT", false, 0.8f, 1.4f),
    RABBIT_ATTACK("ENTITY_RABBIT_JUMP", false, 1.0f, 1.6f),
    RABBIT_HURT("ENTITY_RABBIT_HURT", false, 1.0f, 1.2f),
    RABBIT_DEATH("ENTITY_RABBIT_DEATH", false, 1.0f, 1.0f),

    RAVAGER_AMBIENT("ENTITY_RAVAGER_AMBIENT", false, 1.0f, 0.7f),
    RAVAGER_ATTACK("ENTITY_RAVAGER_ATTACK", false, 1.0f, 0.8f),
    RAVAGER_HURT("ENTITY_RAVAGER_HURT", false, 1.0f, 0.75f),
    RAVAGER_DEATH("ENTITY_RAVAGER_DEATH", false, 1.0f, 0.7f),

    SNIFFER_AMBIENT("ENTITY_SNIFFER_IDLE", false, 1.0f, 1.0f),
    SNIFFER_ATTACK("BLOCK_ROOTED_DIRT_BREAK", false, 1.0f, 0.9f),
    SNIFFER_HURT("ENTITY_SNIFFER_HURT", false, 1.0f, 1.0f),
    SNIFFER_DEATH("ENTITY_SNIFFER_DEATH", false, 1.0f, 0.95f),

    SPIDER_AMBIENT("ENTITY_SPIDER_AMBIENT", false, 1.0f, 0.8f),
    SPIDER_ATTACK("ENTITY_SPIDER_STEP", false, 1.0f, 1.0f),
    SPIDER_HURT("ENTITY_SPIDER_HURT", false, 1.0f, 0.9f),
    SPIDER_DEATH("ENTITY_SPIDER_DEATH", false, 1.0f, 0.8f),

    STRIDER_AMBIENT("ENTITY_STRIDER_AMBIENT", false, 1.0f, 1.3f),
    STRIDER_ATTACK("ENTITY_STRIDER_HAPPY", false, 1.0f, 1.5f),
    STRIDER_HURT("ENTITY_STRIDER_HURT", false, 1.0f, 1.2f),
    STRIDER_DEATH("ENTITY_STRIDER_DEATH", false, 1.0f, 1.0f),

    TURTLE_AMBIENT("ENTITY_TURTLE_AMBIENT_LAND", false, 1.0f, 0.9f),
    TURTLE_ATTACK("BLOCK_SLIME_BLOCK_STEP", false, 1.0f, 0.8f),
    TURTLE_HURT("ENTITY_TURTLE_HURT", false, 1.0f, 0.95f),
    TURTLE_DEATH("ENTITY_TURTLE_DEATH", false, 1.0f, 0.9f),

    WITCH_AMBIENT("ENTITY_WITCH_AMBIENT", false, 1.0f, 1.0f),
    WITCH_ATTACK("ENTITY_WITCH_THROW", false, 1.0f, 1.1f),
    WITCH_HURT("ENTITY_WITCH_HURT", false, 1.0f, 0.95f),
    WITCH_DEATH("ENTITY_WITCH_DEATH", false, 1.0f, 0.9f);


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
