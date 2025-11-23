package org.zuma.examplePlugin.core.constants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public enum Icon {
    EMPTY(""),
    INFO("§bⓤ"),
    ERROR("§cⵅ"),

    COMMON("▜"),
    UNCOMMON("⏤"),
    RARE("⩰"),
    EPIC("⏇"),
    LEGENDARY("⢕"),
    MYTHIC("⛧"),
    DIVINE("⟴"),

    UPGRADE("⏭"),
    DOWNGRADE("ↇ"),
    ARROW_LEFT("⬓"),
    ARROW_RIGHT("⤊"),
    LEATHER("ⱦ"),
    CAMPFIRE("ⷚ"),
    CHAT_BUBBLE("┬"),
    DIALOG_BORDER("⹀"),
    DOLLAR("⋗"),
    FLIGHT("⊙"),
    HERBLORE("⪩"),
    CLEAVE("⣯"),
    BUCKET("⽻"),
    ANVIL("⮯"),
    BOOTS("⭀"),
    ARMOR("⛨"),
    ARROW("⇶"),
    AXE("🪓"),
    COIN_HANDFUL("①"),
    COIN_PILE("◬"),
    COIN_ONE("②"),
    COIN_TWO("③"),
    CRAFTING("҂"),
    CROSSED_SWORDS("⃇"),
    CLOCK("◥"),
    HAMMER("⼯"),
    EGGS("𓅿"),
    FIRE("𓊈"),
    FISH("𓆝"),
    FRUIT("✿"),
    HEART("❤"),
    HEART_BIG("↮"),
    HELMET("⛑"),
    MATERIAL("⑧"),
    PICKAXE("⛏"),
    POTION("☀"),
    TOOL("≜"),
    MONEY_BAG("ⓩ"),
    SWORD_2("⯴"),
    SHIELD_3("⤜"),
    SHIELD_2("▒"),
    SHIELD("▓"),
    STAB_ATTACK("ᗧ"),
    STAB_DEFENCE("◑"),
    STRENGTH("⻖"),
    SWORD("†"),
    BOOK("ↁ"),
    ROCK("⋍"),
    ENVELOPE("✉"),
    LOG("Ⓘ"),
    SWING_SPEED("⾏"),
    BOW("⾸"),
    RANGED_STR("⫨"),
    RANGED_ACCURACY("⥹"),
    WIND("ⶉ"),
    FURNACE("⌰"),
    BLUE_GEM("∉"),
    ACCESSORY_RING("⤪"),
    CLOVER("Ⅹ"),
    CONTRACT_SKULL("⥵"),
    PRAYER("⏲"),
    PRAYER_ACTIVE("⯆"),
    TRADE("⽝"),
    FISHING_HOOK("⢫"),
    ANCHOR("⏚"),
    PARTY("⾱"),
    STORAGE("⾆"),
    EXPLOSION("ⵋ"),

    SKULL_OPEN_MOUTH("₮"),
    SKULL_CLOSED_MOUTH("⺳"),

    SCROLL_RED("ⵣ"),
    SCROLL_GRAY("⁝"),
    SCROLL_GREEN("Ⅸ"),
    SCROLL_BLUE("⼷"),
    SCROLL_LIGHT_GRAY("⾟"),
    SCROLL_YELLOW("⊣"),
    SCROLL_PURPLE("⠳"),
    SCROLL_BROWN("⑬"),

    CHEST("Ⳃ"),
    SPECIAL_ATTACK("Ⱍ"),

    FAIRY("ⰱ"),
    STAMINA("ⶼ"),
    EXPERIENCE("⭮"),
    LEVEL("┚"),
    SPROUT("✦"),
    MAP("⏜"),
    AGE("⢐"),
    DISTANCE_TRAVELED("⠼"),
    MOUNT("⺲"),
    REMAINS("⊪"),
    NAME_TAG("ↈ"),

    GREEN_GEM("⮩"),

    PROGRESS_TALL_START("┉"),
    PROGRESS_SKULL_TALL_END("ⶡ"),
    PROGRESS_TALL_END("▨"),
    PROGRESS_TALL_SEGMENT("↕"),

    PROGRESS_SHORT_START("⸉"),
    PROGRESS_SHORT_END("≖"),
    PROGRESS_SHORT_SEGMENT("⌨"),

    SPECIAL_ATTACK_AVAILBLE_BADGE("↪"),
    SPECIAL_ATTACK_UNAVAILABLE_BADGE("※"),
    FAIRY_BADGE("⣮"),
    MOUNT_BADGE_WEARING("⢧"),
    MOUNT_BADGE_ACTIVE("ↀ"),
    LOGO("◾"),
    LOGO_FULL("⍗"),


    // Reaction bar segments
    REACTION_BAR_SEL_GREEN("ⲅ"),
    REACTION_BAR_SEL_YELLOW("⾼"),
    REACTION_BAR_SEL_RED("ⓜ"),
    REACTION_BAR_UNSEL_GREEN("ⱹ"),
    REACTION_BAR_UNSEL_YELLOW("⤡"),
    REACTION_BAR_UNSEL_RED("⮭"),


    RANK_BADGE_OWNER("⸋"),
    RANK_BADGE_ADMIN("⥿"),
    RANK_BADGE_MOD("⭋"),
    RANK_BADGE_HELPER("⤳"),
    RANK_BADGE_PILOT("⇦"),

    RANK_BADGE_VIP("⊹"),
    RANK_BADGE_HERO("⥤"),
    RANK_BADGE_LEGEND("⾎"),
    RANK_BADGE_MYTHIC("↴"),

    SUPPORTER_LVL_1("⇀"),
    SUPPORTER_LVL_2("ⳬ"),
    SUPPORTER_LVL_3("ⴌ"),
    SUPPORTER_LVL_4("⺕"),
    SUPPORTER_LVL_5("⾮"),
    SUPPORTER_LVL_6("◚");


    private final String icon;

    Icon(String icon) {
        this.icon = icon;
    }

    public String get() {
        return icon;
    }

    public Component asComponent() {
        return Component.text(get(), NamedTextColor.WHITE).append(Component.text(" "));
    }
}
