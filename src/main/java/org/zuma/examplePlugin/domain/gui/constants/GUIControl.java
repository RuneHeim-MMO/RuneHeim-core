package org.zuma.examplePlugin.domain.gui.constants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.core.handlers.IllegalItemWatchdog;

import java.util.ArrayList;
import java.util.List;

public enum GUIControl {
    BLANK(0),
    CHEVRON_RIGHT(1),
    CHEVRON_LEFT(2),
    YES(3),
    NO(4),
    TRANSPARENT(5),
    INFO(6);

    private final int modelId;

    GUIControl(int modelId) {
        this.modelId = modelId;
    }

    public ItemStack asItemstack() {
        return asItemstack(Component.empty());
    }

    public ItemStack asItemstack(Component displayName) {
        return asItemstack(displayName, new ArrayList<>());
    }

    public ItemStack asItemstack(Component displayName, List<Component> lore) {
        ItemStack icon = new ItemStack(Material.POTATO);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(displayName.decoration(TextDecoration.ITALIC, false));
        meta.lore(lore.stream().map(comp -> comp.decoration(TextDecoration.ITALIC, false)).toList());
        meta.setCustomModelData(modelId);
        IllegalItemWatchdog.tagIllegalItem(meta);
        icon.setItemMeta(meta);
        return icon;
    }


}
