package org.zuma.examplePlugin.domain.gui.base;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class RecipeInventory extends PaginatedIndividualGUI {
    private final int LEFT_CLICK_CRAFT_AMOUNT;
    private final int SHIFT_CLICK_CRAFT_AMOUNT;
    private final String actionLabel;

    public RecipeInventory(String title, int id, int rows, String actionLabel, int leftClickCraftAmount, int shiftClickCraftAmount, HashSet<Integer> ignoredSlots) {
        super(title, id, rows, ignoredSlots);
        this.actionLabel = actionLabel;
        this.LEFT_CLICK_CRAFT_AMOUNT = leftClickCraftAmount;
        this.SHIFT_CLICK_CRAFT_AMOUNT = shiftClickCraftAmount;
    }

    public abstract List<AbstractRecipe> getRecipes();

    @Override
    public boolean attemptOpenInventory(RunePlayer player) {
        setStackContent(getRecipes().stream().map(recipe -> {
            ItemStack icon = recipe.getDisplayItemstack(player, RuneQuestPlugin.getItemRegistry());

            super.assignMenuValue(icon, recipe.getId());
            super.setActionsLore(icon, this.actionLabel + " x" + this.LEFT_CLICK_CRAFT_AMOUNT, this.actionLabel + " x" + SHIFT_CLICK_CRAFT_AMOUNT);

            return icon;
        }).toList(), true);

        player.asBukkitPlayer().openInventory(getBaseInventory());
        return true;
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

        int times = event.isShiftClick() ? SHIFT_CLICK_CRAFT_AMOUNT : LEFT_CLICK_CRAFT_AMOUNT;
        boolean craftStarted = RuneQuestPlugin.getRecipeHandler().craftRecipe(player, RuneQuestPlugin.getRecipeRegistry().getRecipeById(menuValue), times);

        if (craftStarted) {
            player.asBukkitPlayer().closeInventory();
        }

    }
}
