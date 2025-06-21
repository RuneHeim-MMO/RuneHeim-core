package org.zuma.examplePlugin.domain.item.constants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.zuma.examplePlugin.core.constants.Icon;

public enum ItemRarity {
    NOT_APPLICABLE(-1, "", "#8c8c8c", Icon.EMPTY),
    COMMON(0, "Common", "#d1d1d1", Icon.COMMON),
    UNCOMMON(1, "Uncommon", "#76e876", Icon.UNCOMMON),
    RARE(2, "Rare", "#82c1ff", Icon.RARE),
    EPIC(3, "Epic", "#800080", Icon.EPIC),
    LEGENDARY(4, "Legendary", "#ff8c00", Icon.LEGENDARY),
    MYTHIC(5, "Mythic", "#ff1493", Icon.MYTHIC),
    DIVINE(6, "Divine", "#ffd700", Icon.DIVINE);

    private final int key;
    private final String label, icon;
    private final String hexColor;

    ItemRarity(int key, String label, String hexColor, Icon icon) {
        this.key = key;
        this.label = label;
        this.hexColor = hexColor;
        this.icon = icon.get();
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

    public String getIcon() {
        return icon;
    }

    public Component getComponent() {
        return Component.text(icon + " ", NamedTextColor.WHITE).append(Component.text(label).color(TextColor.fromHexString(hexColor)));
    }
}
