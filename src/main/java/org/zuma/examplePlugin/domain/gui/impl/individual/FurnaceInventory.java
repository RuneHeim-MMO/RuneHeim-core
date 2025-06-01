package org.zuma.examplePlugin.domain.gui.impl.individual;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;

import java.util.List;

public class FurnaceInventory extends IndividualGUI {
    public FurnaceInventory(String title, int id, int rows) {
        super(title, id, rows);
    }

    @Override
    public void openInventory(RunePlayer player) {
        Inventory inventory = getBaseInventory();
        List<AbstractRecipe> recipes = RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.SMELTING_RECIPE);

        for (int i = 0; i < recipes.size(); i++) {

            if (i > super.getSlots()) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, title + " inventory is overflowing.");
                continue;
            }

            AbstractRecipe recipe = recipes.get(i);
            ItemStack item = recipe.getDisplayItemstack(player, RuneQuestPlugin.getItemRegistry());
            super.assignMenuValue(item, recipe.getId());

            inventory.setItem(i, item);
        }

        player.getPlayer().openInventory(inventory);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(currentItem);

        if (menuValue == null) {
            return;
        }

        boolean craftStarted = RuneQuestPlugin.getRecipeHandler().craftRecipe(player, RuneQuestPlugin.getRecipeRegistry().getRecipeById(menuValue), 1);

        if (craftStarted) {
            player.getPlayer().closeInventory();
        }

    }
}
