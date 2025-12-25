package org.zuma.examplePlugin.domain.recipe;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.handlers.game.BotVerificationHandler;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class RecipeListener implements Listener {
    private final RecipeHandler recipeHandler;

    public RecipeListener(RecipeHandler recipeHandler) {
        this.recipeHandler = recipeHandler;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            return;
        }

        Inventory inventory = event.getClickedInventory();

        if (inventory == null || !inventory.equals(player.getInventory())) {
            return;
        }

        ItemStack clicked = inventory.getItem(event.getSlot());
        ItemStack cursor = player.getItemOnCursor();

        if (clicked == null) {
            return;
        }

        if (clicked.isSimilar(cursor)) {
            return;
        }

        if (cursor.getType().isAir()) {
            return;
        }

        AbstractItemType customClicked = RuneQuestPlugin.getItemRegistry().getType(clicked);
        AbstractItemType customCursor = RuneQuestPlugin.getItemRegistry().getType(cursor);

        if (customClicked == null || customCursor == null) {
            return;
        }

        boolean craftingStarted = recipeHandler.handleInventoryRecipeClick(runePlayer, event.getInventory(), customClicked, customCursor);

        if (craftingStarted) {
            event.setCancelled(true);
        }
    }
}
