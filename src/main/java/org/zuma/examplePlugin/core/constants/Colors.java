package org.zuma.examplePlugin.core.constants;

import net.kyori.adventure.text.format.TextColor;

public enum Colors {
    SERVER_BRAND("#50e6cf"),
    SUBTITLE("#d9d9d9"),
    ERROR("#d60b33"),
    ERROR_LIGHT("#fc5677"),
    SUCCESS("#0bd69d"),
    HEALTH("#c20037"),

    ITEM_TYPE("#4f4f4f"),
    ITEM_DESCRIPTION("#c9c9c9"),
    ITEM_LABEL("#FFD700"),
    ITEM_REQUIREMENT("#a8a8a8"),
    ITEM_BONUS_POSITIVE("#88fc65"),
    ITEM_BONUS_NEGATIVE("#fc656d"),

    INTERACTABLE_ENTITY("#ffe6b8"),
    DEPLETED_RESOURCE("#636363"),
    ACCENT("#ebc934");

    private final TextColor color;
    private final String hex;

    Colors(String hex) {
        this.color = TextColor.fromHexString(hex);
        this.hex = hex;
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

}
