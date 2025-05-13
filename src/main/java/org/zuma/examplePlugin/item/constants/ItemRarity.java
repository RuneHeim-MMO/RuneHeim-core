package org.zuma.examplePlugin.item.constants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public enum ItemRarity {
    NOT_APPLICABLE(-1, "", "#000000"),
    UNCOMMON(0, "Uncommon", "#32cd32"),
    RARE(1, "Rare", "#1e90ff"),
    EPIC(2, "Epic", "#800080"),
    LEGENDARY(3, "Legendary", "#ff8c00"),
    MYTHIC(4, "Mythic", "#ff1493"),
    DIVINE(5, "Divine", "#ffd700");;

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
}
