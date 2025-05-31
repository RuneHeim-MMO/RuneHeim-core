package org.zuma.examplePlugin.core.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemDataKey;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerUtils {

    public static ItemStack getItemStackInHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    public static boolean hasItems(RunePlayer player, List<ItemRecord> requiredItems) {
        Map<Integer, Integer> itemCounts = new HashMap<>();
        NamespacedKey itemIdKey = new NamespacedKey(RuneQuestPlugin.getInstance(), ItemDataKey.ITEM_ID.asString());

        // Count all item IDs in the player's main inventory (excluding armor/offhand)
        for (ItemStack stack : player.getPlayer().getInventory().getStorageContents()) {
            if (stack == null || stack.getType().isAir()) continue;

            ItemMeta meta = stack.getItemMeta();
            if (meta == null) continue;

            PersistentDataContainer container = meta.getPersistentDataContainer();
            if (!container.has(itemIdKey, PersistentDataType.INTEGER)) continue;

            Integer id = container.get(itemIdKey, PersistentDataType.INTEGER);
            if (id == null) continue;

            int amount = stack.getAmount();
            itemCounts.put(id, itemCounts.getOrDefault(id, 0) + amount);
        }

        // Check if all required items are available in sufficient quantity
        for (ItemRecord record : requiredItems) {
            int requiredId = record.getItemId();
            int requiredAmount = record.getAmount();

            if (itemCounts.getOrDefault(requiredId, 0) < requiredAmount) {
                return false;
            }
        }

        return true;
    }

    public static boolean removeItems(RunePlayer player, List<ItemRecord> requiredItems) {
        if (!hasItems(player, requiredItems)) {
            return false;
        }

        NamespacedKey itemIdKey = new NamespacedKey(RuneQuestPlugin.getInstance(), ItemDataKey.ITEM_ID.asString());
        PlayerInventory inventory = player.getPlayer().getInventory();
        ItemStack[] contents = inventory.getStorageContents();

        for (ItemRecord req : requiredItems) {
            int remainingToRemove = req.getAmount();
            int requiredId = req.getItemId();

            if (remainingToRemove <= 0) continue; // skip invalid records

            for (int i = 0; i < contents.length; i++) {
                ItemStack stack = contents[i];
                if (stack == null || stack.getType().isAir()) continue;

                ItemMeta meta = stack.getItemMeta();
                if (meta == null) continue;

                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (!container.has(itemIdKey, PersistentDataType.INTEGER)) continue;

                Integer id = container.get(itemIdKey, PersistentDataType.INTEGER);
                if (id == null || id != requiredId) continue;

                int stackAmount = stack.getAmount();

                if (stackAmount <= remainingToRemove) {
                    remainingToRemove -= stackAmount;
                    contents[i] = null;
                } else {
                    stack.setAmount(stackAmount - remainingToRemove);
                    remainingToRemove = 0;
                    break;
                }

                if (remainingToRemove <= 0) break;
            }
        }

        inventory.setStorageContents(contents);
        player.getPlayer().updateInventory();

        return true;
    }
}
