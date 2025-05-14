package org.zuma.examplePlugin.constants;

import net.kyori.adventure.text.format.TextColor;

public enum Colors {
    SERVER_BRAND("#50e6cf"),
    SUBTITLE("#d9d9d9"),
    ERROR("#d60b33"),
    SUCCESS("#0bd69d");

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

}
