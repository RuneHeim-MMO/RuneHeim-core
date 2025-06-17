package org.zuma.examplePlugin.features.admin.inventories;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;

import java.util.List;

public class ItemRegistryInventory {

    public static void open(ItemRegistry registry, Player player, int page) {
        int INVENTORY_ROWS = 6; // Max 6!
        int ITEMS_IN_ROW = 9; // Always 9

        if (!player.hasPermission("rune.admin.item_registry")) {
            player.sendMessage(Component.text("Missing rune.admin.item_registry permission.").color(Color.ERROR.asColor()));
            return;
        }

        int slots = INVENTORY_ROWS * ITEMS_IN_ROW;
        int totalItems = registry.getAllItems().size();
        int indexStart = page * slots;
        int indexEnd = Math.min(indexStart + slots, totalItems);
        int maxPages = ((totalItems + slots - 1) / slots) - 1;

        if (indexStart >= totalItems) {
            player.sendMessage(Component.text("No items to display on page " + page + ". Total pages: 0-" + maxPages).color(Color.ERROR.asColor()));
            return;
        }

        List<AbstractItem> paginatedRegistryItems = registry.getAllItems().stream().sorted((a, b) -> {
            return a.id - b.id;
        }).toList().subList(indexStart, indexEnd);

        Inventory inventory = Bukkit.createInventory(null, slots, Component.text("Item Registry | Page: " + page + "-" + maxPages));

        for (int i = 0; i < paginatedRegistryItems.size(); i++) {
            inventory.setItem(i, paginatedRegistryItems.get(i).asItemStack());
        }

        player.openInventory(inventory);


    }
}
