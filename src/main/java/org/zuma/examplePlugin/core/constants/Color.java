package org.zuma.examplePlugin.core.constants;

import net.kyori.adventure.text.format.TextColor;

public enum Color {
    SERVER_BRAND("#50e6cf"),
    SUBTITLE("#d9d9d9"),
    WARNING("#ffb326"),
    INFO("#77e4f2"),
    ERROR("#d60b33"),
    ERROR_LIGHT("#fc5677"),
    SUCCESS("#0bd69d"),
    HEALTH("#c20037"),

    SHOP_DESCRIPTION("#42cfa9"),

    ITEM_TYPE_LABEL("#4f4f4f"),
    ITEM_LORE_DESCRIPTION("#c9c9c9"),
    ITEM_CATEGORY_LABEL("#FFD700"),
    ITEM_REQUIREMENT("#a8a8a8"),
    ITEM_BONUS_POSITIVE("#88fc65"),
    ITEM_BONUS_NEGATIVE("#fc656d"),

    INTERACTABLE_ENTITY("#ffe6b8"),
    DEPLETED_RESOURCE("#636363"),
    ACCENT("#ebc934"),
    FAIRY_STAMINA("#42d7f5"),
    PRAYER_ENERGY("#03cffc"),

    CURRENCY_LOW("#FFFF00"),
    CURRENCY_MID("#F5F6F7"),
    CURRENCY_HIGH("#7aff7d"),

    BUSY_BAR_EMPTY("#346a94"),
    BUSY_BAR_FILL("#43c0fa"),

    SKILL_BAR_EMPTY("#448555"),
    SKILL_BAR_FILL("#55e67b"),

    SPECIAL_BAR_EMPTY("#1F1F1F"),
    SPECIAL_BAR_FILL("#55e67b"),

    MANA_BAR_EMPTY("#2f3887"),
    MANA_BAR_FILL("#6978ff"),

    CAMPFIRE_BAR_EMPTY("#4a3312"),
    CAMPFIRE_BAR_FILL("#f7ab39"),

    ZERO_HIT("#5274ff"),
    DAMAGE_HIT("#ff2427"),
    CRITICAL_HIT("#870002");

    private final TextColor color;
    private final String hex;

    Color(String hex) {
        this.color = TextColor.fromHexString(hex);
        this.hex = hex;
    }

    public static TextColor coloredProgressValue(int current, int min, int max, boolean invert) {
        // Clamp current between min and max
        int clamped = Math.max(min, Math.min(current, max));

        // Normalize to 0.0 - 1.0
        float progress = (float) (clamped - min) / (max - min);

        // If inverted, flip the progress
        if (invert) {
            progress = 1.0f - progress;
        }

        // Linear interpolate between red and green
        int red = (int) (255 * (1 - progress));
        int green = (int) (255 * progress);

        return TextColor.color(red, green, 0);
    }

    public TextColor asColor() {
        return this.color;
    }

    public String asHex() {
        return this.hex;
    }

    public String asSpigotHex() {
        StringBuilder builder = new StringBuilder("§x");
        for (char c : hex.substring(1).toCharArray()) {
            builder.append('§').append(c);
        }
        return builder.toString();
    }

}
