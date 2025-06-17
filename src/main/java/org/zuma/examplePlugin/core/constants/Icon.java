package org.zuma.examplePlugin.core.constants;

public enum Icon {
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
    SHIELD_2("▒"),
    SHIELD("▓"),
    STAB_ATTACK("ᗧ"),
    STAB_DEFENCE("◑"),
    STRENGTH("⻖"),
    SWORD("†"),
    BOOK("ↁ"),
    ROCK("⋍"),

    PROGRESS_TALL_START("┉"),
    PROGRESS_GEM_TALL_START("⢽"),
    PROGRESS_SKULL_TALL_END("ⶡ"),
    PROGRESS_TALL_END("▨"),
    PROGRESS_TALL_SEGMENT("↕"),

    PROGRESS_SHORT_START("⸉"),
    PROGRESS_SHORT_END("≖"),
    PROGRESS_SHORT_SEGMENT("⌨");


    private final String icon;

    Icon(String icon) {
        this.icon = icon;
    }

    public String get() {
        return icon;
    }
}
