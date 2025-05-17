package org.zuma.examplePlugin.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.item.base.CustomItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.PersistentDataKey;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRegistry {
    private final Map<Integer, CustomItem> items = new HashMap<>(); // ID:RuneItem (ID stored in persistent item stack data)

    public void registerItem(CustomItem item) {
        CustomItem existingItem = items.get(item.id);

        if (existingItem != null) {
            throw new Error("Cannot register " + item.displayName + ". Item with " + item.id + " (" + existingItem.displayName + ") already exists!");
        }

        items.put(item.id, item);
    }

    @Nullable
    public CustomItem getItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistentDataKey.ITEM_ID.asString());
            Integer value = container.get(key, PersistentDataType.INTEGER);

            return value != null ? getItem(value) : null;
        }

        return null;
    }

    @Nullable
    public CustomItem getItem(ItemId id) {
        return getItem(id.asInt());
    }

    @Nullable
    public CustomItem getItem(int id) {
        CustomItem item = items.get(id);

        if (item == null) {
            throw new Error("Cannot get rune item. Item with id " + id + " does not exist or is not registered in the registry.");
        }

        return item;
    }

    @NotNull
    public List<CustomItem> getAllItems() {
        return new ArrayList<>(items.values());
    }

}
