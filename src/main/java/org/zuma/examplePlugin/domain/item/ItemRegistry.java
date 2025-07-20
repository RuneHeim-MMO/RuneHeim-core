package org.zuma.examplePlugin.domain.item;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRegistry {
    private final Map<Integer, AbstractItemType> items = new HashMap<>(); // ID:RuneItem (ID stored in persistent item stack data)

    public void registerItem(AbstractItemType item) {
        AbstractItemType existingItem = items.get(item.id);

        if (existingItem != null) {
            throw new Error("Cannot register " + item.displayName + ". Item with " + item.id + " (" + existingItem.displayName + ") already exists!");
        }

        items.put(item.id, item);
    }

    @Nullable
    public AbstractItemType getItem(@Nullable ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistenceDataKey.ITEM_ID.asString());
            Integer value = container.get(key, PersistentDataType.INTEGER);

            return value != null ? getItem(value) : null;
        }

        return null;
    }

    @Nullable
    public AbstractItemType getItem(ItemId id) {
        return getItem(id.asInt());
    }

    @Nullable
    public AbstractItemType getItem(int id) {
        return items.get(id);
    }

    @NotNull
    public List<AbstractItemType> getAllItems() {
        return new ArrayList<>(items.values());
    }

}
