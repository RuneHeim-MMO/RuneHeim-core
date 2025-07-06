package org.zuma.examplePlugin.core.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class InventoryUtils {

    public static void returnPlayerItemFromInventory(Inventory inventory, RunePlayer player) {
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);

            if (item != null && !GUIControl.isGuiItem(item)) {
                ItemStack cloned = item.clone();
                inventory.setItem(slot, null);
                player.giveItemOrDrop(cloned);
            }
        }
    }
}
