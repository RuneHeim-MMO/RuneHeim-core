package org.zuma.examplePlugin.core.constants;

import net.kyori.adventure.text.format.TextColor;

public enum Colors {
    SERVER_BRAND("#50e6cf"),
    SUBTITLE("#d9d9d9"),
    ERROR("#d60b33"),
    SUCCESS("#0bd69d"),

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

}
