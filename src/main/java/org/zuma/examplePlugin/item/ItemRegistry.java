package org.zuma.examplePlugin.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.constants.PersistentDataKey;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {
    private final Map<Integer, ScriptedItem> items = new HashMap<>(); // ID:RuneItem (ID stored in persistent item stack data)

    public void registerItem(ScriptedItem item) {
        items.put(item.id, item);
    }

    public ScriptedItem getItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistentDataKey.ITEM_ID.asString());
            Integer value = container.get(key, PersistentDataType.INTEGER);

            return value != null ? getItem(value) : null;
        }

        return null;
    }

    public ScriptedItem getItem(int id) {
        ScriptedItem item = items.get(id);

        if (item == null) {
            throw new Error("Cannot get rune item. Item with id " + id + " does not exist or is not registered in the registry.");
        }

        return item;
    }

}
