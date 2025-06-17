package org.zuma.examplePlugin.core.constants;

public enum Icon {
    ANVIL("⮯"),
    ARMOR("⛨"),
    ARROW("⇶"),
    AXE("🪓"),
    COIN_MANY("①"),
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
    ROCK("⋍");

    private final String icon;

    Icon(String icon) {
        this.icon = icon;
    }

    public String get() {
        return icon;
    }
}
