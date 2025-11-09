package org.zuma.examplePlugin.domain.item.handlers;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;

public class LockedItemHandler {
    private final static NamespacedKey lockedItemKey = PersistenceDataKey.LOCKED_TO_INVENTORY.key(RuneQuestPlugin.getInstance());

    public static void lockItemStack(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(lockedItemKey, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
    }

    public static void unlockItemStack(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.remove(lockedItemKey);
        item.setItemMeta(meta);
    }

    public static boolean isItemStackLocked(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        Byte locked = container.get(lockedItemKey, PersistentDataType.BYTE);
        return locked != null;
    }

}
