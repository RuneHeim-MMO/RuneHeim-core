package org.zuma.examplePlugin.domain.gui.impl.individual;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;

public class AnvilInventory extends PaginatedIndividualGUI {
    private static final int LEFT_CLICK_SMELT_AMOUNT = 1;
    private static final int SHIFT_CLICK_SMELT_AMOUNT = 5;

    public AnvilInventory(String title, int id) {
        super(title, id, 6);
    }


    @Override
    public void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
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

    @Override
    public void openInventory(RunePlayer player) {
        setContent(RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.SMITHING_RECIPE, true).stream().map(recipe -> {
            ItemStack icon = recipe.getDisplayItemstack(player, RuneQuestPlugin.getItemRegistry());

            super.assignMenuValue(icon, recipe.getId());
            super.setActionsLore(icon, "Smith x" + LEFT_CLICK_SMELT_AMOUNT, "Smith x" + SHIFT_CLICK_SMELT_AMOUNT);

            return icon;
        }).toList());

        player.asBukkitPlayer().openInventory(getBaseInventory());
    }
}
