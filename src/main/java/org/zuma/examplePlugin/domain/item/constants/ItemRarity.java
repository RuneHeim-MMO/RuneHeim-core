package org.zuma.examplePlugin.domain.item.constants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.Objects;

public enum ItemRarity {
    NOT_APPLICABLE(-1, "", "#000000"),
    COMMON(0, "Common", "#d1d1d1"),
    UNCOMMON(1, "Uncommon", "#76e876"),
    RARE(2, "Rare", "#82c1ff"),
    EPIC(3, "Epic", "#800080"),
    LEGENDARY(4, "Legendary", "#ff8c00"),
    MYTHIC(5, "Mythic", "#ff1493"),
    DIVINE(6, "Divine", "#ffd700");;

    private final int key;
    private final String label;
    private final String hexColor;

    ItemRarity(int key, String label, String hexColor) {
        this.key = key;
        this.label = label;
        this.hexColor = hexColor;
    }

    public int asNumber() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public String getHexColor() {
        return hexColor;
    }

    public Component getComponent() {
        return Component.text(label).color(TextColor.fromHexString(hexColor));
    }

    public Component getComponent(String prefix) {
        return Component.text(Objects.equals(prefix, "") ? label : prefix + " " + label).color(TextColor.fromHexString(hexColor));
    }
}
