package org.zuma.examplePlugin.domain.gui.impl.individual;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CookingInventory extends IndividualGUI {
    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>(Arrays.asList(0, 1, 9, 10));
    private static final int LEFT_CLICK_SMELT_AMOUNT = 1;
    private static final int SHIFT_CLICK_SMELT_AMOUNT = 5;

    public CookingInventory(String title, int id) {
        super(title, id, 3);
    }

    @Override
    public void openInventory(RunePlayer player) {
        Inventory inventory = getBaseInventory();
        List<AbstractRecipe> recipes = RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.COOKING_RECIPE, true);

        int recipeIndex = 0;
        for (int i = 0; i < super.getSlots(); i++) {
            if (IGNORED_SLOTS.contains(i)) {
                continue;
            }
            if (recipeIndex >= recipes.size()) {
                break;
            }

            AbstractRecipe recipe = recipes.get(recipeIndex);

            if (recipe == null) {
                break;
            }

            // Recipe display item stacks are already watchdog tagged.
            ItemStack item = recipe.getDisplayItemstack(player, RuneQuestPlugin.getItemRegistry());

            super.assignMenuValue(item, recipe.getId());
            super.setActionsLore(item, "Cook x" + LEFT_CLICK_SMELT_AMOUNT, "Cook x" + SHIFT_CLICK_SMELT_AMOUNT);

            inventory.setItem(i, item);
            recipeIndex++;
        }

        player.asBukkitPlayer().openInventory(inventory);
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

        int times = event.isShiftClick() ? SHIFT_CLICK_SMELT_AMOUNT : LEFT_CLICK_SMELT_AMOUNT;
        boolean craftStarted = RuneQuestPlugin.getRecipeHandler().craftRecipe(player, RuneQuestPlugin.getRecipeRegistry().getRecipeById(menuValue), times);

        if (craftStarted) {
            player.asBukkitPlayer().closeInventory();
        }

    }
}
