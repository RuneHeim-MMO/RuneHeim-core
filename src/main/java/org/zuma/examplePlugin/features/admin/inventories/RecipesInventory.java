package org.zuma.examplePlugin.features.admin.inventories;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.RecipeRegistry;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;

import java.util.List;

public class RecipesInventory {

    public static void open(RecipeRegistry registry, ItemRegistry itemRegistry, RunePlayer player, int page) {
        int INVENTORY_ROWS = 6; // Max 6!
        int ITEMS_IN_ROW = 9; // Always 9

        if (!player.getPlayer().hasPermission("rune.admin.item_recipes")) {
            player.getPlayer().sendMessage(Component.text("Missing rune.admin.item_recipes permission.").color(Colors.ERROR.asColor()));
            return;
        }

        int slots = INVENTORY_ROWS * ITEMS_IN_ROW;
        int totalItems = registry.getAllRecipes().size();
        int indexStart = page * slots;
        int indexEnd = Math.min(indexStart + slots, totalItems);
        int maxPages = ((totalItems + slots - 1) / slots) - 1;

        if (indexStart >= totalItems) {
            player.getPlayer().sendMessage(Component.text("No items to display on page " + page + ". Total pages: 0-" + maxPages).color(Colors.ERROR.asColor()));
            return;
        }

        List<AbstractRecipe> paginatedRegistryItems = registry.getAllRecipes().stream().sorted((a, b) -> {
            return a.getId() - b.getId();
        }).toList().subList(indexStart, indexEnd);

        Inventory inventory = Bukkit.createInventory(null, slots, Component.text("Item Registry | Page: " + page + "-" + maxPages));

        for (int i = 0; i < paginatedRegistryItems.size(); i++) {
            inventory.setItem(i, paginatedRegistryItems.get(i).getDisplayItemstack(player, itemRegistry));
        }

        player.getPlayer().openInventory(inventory);


    }
}
