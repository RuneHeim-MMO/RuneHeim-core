package org.zuma.examplePlugin.core.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;

import java.util.List;
import java.util.Objects;

public class StorageUtils {
    private static final NamespacedKey storageItemStoredIdsKey = PersistenceDataKey.STORAGE_ITEM_STORED_IDS.key(RuneQuestPlugin.getInstance());
    private static final NamespacedKey storageItemStoredAmountsKey = PersistenceDataKey.STORAGE_ITEM_STORED_AMOUNTS.key(RuneQuestPlugin.getInstance());

    public static void addDisplayItemIdsPDC(ItemStack storageItem, List<ItemRecord> storedItems) {
        ItemMeta meta = storageItem.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(storageItemStoredIdsKey, PersistentDataType.INTEGER_ARRAY, storedItems.stream().filter(Objects::nonNull).mapToInt(ItemRecord::getItemId).toArray());
        pdc.set(storageItemStoredAmountsKey, PersistentDataType.INTEGER_ARRAY, storedItems.stream().filter(Objects::nonNull).mapToInt(ItemRecord::getAmount).toArray());

        storageItem.setItemMeta(meta);
    }

    public static List<ItemRecord> getDisplayItemIdsFromPDC(ItemStack storageItem) {
        ItemMeta meta = storageItem.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        int[] ids = pdc.get(storageItemStoredIdsKey, PersistentDataType.INTEGER_ARRAY);
        int[] amounts = pdc.get(storageItemStoredAmountsKey, PersistentDataType.INTEGER_ARRAY);

        if (ids == null || amounts == null || ids.length != amounts.length) {
            return List.of();
        }

        List<ItemRecord> itemRecords = new java.util.ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            itemRecords.add(new ItemRecord(ids[i], amounts[i]));
        }

        return itemRecords;
    }
}
