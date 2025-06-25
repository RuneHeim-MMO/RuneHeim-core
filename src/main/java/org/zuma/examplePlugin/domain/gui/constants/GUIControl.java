package org.zuma.examplePlugin.domain.gui.constants;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.core.handlers.IllegalItemWatchdog;

public enum GUIControl {
    BLANK(0),
    CHEVRON_RIGHT(1),
    CHEVRON_LEFT(2),
    YES(3),
    NO(4);

    private final int modelId;

    GUIControl(int modelId) {
        this.modelId = modelId;
    }

    public ItemStack asItemstack() {
        ItemStack icon = new ItemStack(Material.POTATO);
        ItemMeta meta = icon.getItemMeta();
        meta.setCustomModelData(modelId);
        IllegalItemWatchdog.tagIllegalItem(meta);
        icon.setItemMeta(meta);
        return icon;
    }


}
