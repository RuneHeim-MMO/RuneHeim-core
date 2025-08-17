package org.zuma.examplePlugin.domain.gui.constants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;

import java.util.ArrayList;
import java.util.List;

public enum GUIControl {
    BLANK(0),
    CHEVRON_RIGHT(1),
    CHEVRON_LEFT(2),
    YES(3),
    NO(4),
    TRANSPARENT(5),
    INFO(6),

    NUM_1(7),
    NUM_2(8),
    NUM_3(9),
    NUM_4(10),
    NUM_5(11),
    NUM_6(12),
    NUM_7(13),
    NUM_8(14),
    NUM_9(15),
    NUM_10(16),
    NUM_PLUS10(17),
    NUM_0(18),
    NUM_UNKNOWN(19),

    CHEVRON_RIGHT_DISABLED(20),
    CHEVRON_LEFT_DISABLED(21);


    private final int modelId;

    GUIControl(int modelId) {
        this.modelId = modelId;
    }

    public ItemStack asItemstack() {
        return asItemstack(Component.empty());
    }

    public ItemStack asItemstack(Component displayName, boolean bypassItemWatchdog) {
        return asItemstack(displayName, new ArrayList<>(), !bypassItemWatchdog);
    }

    public ItemStack asItemstack(Component displayName) {
        return asItemstack(displayName, new ArrayList<>(), true);
    }


    public ItemStack asItemstack(Component displayName, List<Component> lore) {
        return this.asItemstack(displayName, lore, true);
    }

    public ItemStack asItemstack(Component displayName, List<Component> lore, boolean tagIllegal) {
        ItemStack icon = new ItemStack(Material.POTATO);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(displayName.decoration(TextDecoration.ITALIC, false));
        meta.lore(lore.stream().map(comp -> comp.decoration(TextDecoration.ITALIC, false)).toList());
        meta.setCustomModelData(modelId);

        if (tagIllegal) {
            IllegalItemWatchdog.tagIllegalItem(meta);
        }

        icon.setItemMeta(meta);
        return icon;
    }

    public static boolean isGuiItem(ItemStack item) {
        if (item == null) {
            return false;
        }
        return IllegalItemWatchdog.getItemStackStatus(item) != IllegalItemWatchdog.LegalityType.LEGAL;
    }

    public static GUIControl fromNumber(int number) {
        if (number < 0) {
            return NUM_UNKNOWN;
        }

        return switch (number) {
            case 0 -> NUM_0;
            case 1 -> NUM_1;
            case 2 -> NUM_2;
            case 3 -> NUM_3;
            case 4 -> NUM_4;
            case 5 -> NUM_5;
            case 6 -> NUM_6;
            case 7 -> NUM_7;
            case 8 -> NUM_8;
            case 9 -> NUM_9;
            case 10 -> NUM_10;
            default -> NUM_PLUS10;
        };
    }
}
