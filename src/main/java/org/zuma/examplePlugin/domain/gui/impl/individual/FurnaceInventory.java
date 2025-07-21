package org.zuma.examplePlugin.domain.gui.impl.individual;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.base.RecipeInventory;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FurnaceInventory extends RecipeInventory {
    public FurnaceInventory(String title, int id) {
        super(title, id, 2, "Smelt", 1, 5, new HashSet<>(Arrays.asList(0, 1, 9, 10)));
    }

    @Override
    public List<AbstractRecipe> getRecipes() {
        return RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.SMELTING_RECIPE, true);
    }
}
