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

    BUCKET("⽻"),
    ANVIL("⮯"),
    ARMOR("⛨"),
    ARROW("⇶"),
    AXE("🪓"),
    COIN_HANDFUL("①"),
    COIN_PILE("◬"),
    COIN_ONE("②"),
    COIN_TWO("③"),
    CRAFTING("҂"),
    CROSSED_SWORDS("⃇"),
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
    LOGO("◾"),
    LOGO_FULL("⍗");


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
