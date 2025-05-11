package org.zuma.examplePlugin.item;

import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.constants.PersistentDataKey;
import org.zuma.examplePlugin.item.items.RuneItem;
import org.zuma.examplePlugin.player.RuneQuestPlayer;

import java.util.HashMap;
import java.util.Map;

public class ItemHandler {
    private final Map<Integer, ScriptedItem> runeItems = new HashMap<>(); // ID:RuneItem (ID stored in persistent item stack data)

    public ItemHandler() {
        runeItems.put(0, new RuneItem());
    }

    public ScriptedItem getRuneItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistentDataKey.ITEM_ID.asString());
            Integer value = container.get(key, PersistentDataType.INTEGER);

            return value != null ? getRuneItem(value) : null;
        }

        return null;
    }

    public ScriptedItem getRuneItem(int id) {
        ScriptedItem item = runeItems.get(id);

        if (item == null) {
            throw new Error("Cannot get rune item. Item with id " + id + " does not exist or is not registered in the registry.");
        }

        return item;
    }


    public void onPlayerInteract(PlayerInteractEvent event, ScriptedItem itemInHand, RuneQuestPlayer player) {
        itemInHand.onPlayerInteract(event, player);
    }

    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, ScriptedItem itemInHand, RuneQuestPlayer player) {
        itemInHand.onPlayerInteractEntity(event, player);
    }

    public void onPlayerItemDrop(PlayerDropItemEvent event, ScriptedItem droppedItem, RuneQuestPlayer player) {
        droppedItem.onPlayerItemDrop(event, player);
    }

    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event, ScriptedItem itemInHand, RuneQuestPlayer player) {
        itemInHand.onPlayerTakeItemInHand(event, player);
    }

    public void onPlayerDamageEntity(EntityDamageByEntityEvent event, RuneQuestPlayer damager, ScriptedItem damagedWith) {
        damagedWith.onPlayerDamageEntity(event, damager);
    }
}
