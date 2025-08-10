package org.zuma.examplePlugin.domain.gui.impl.individual.recipe;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.gui.base.RecipeInventory;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipe.constants.RecipeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class FurnaceInventory extends RecipeInventory {
    public FurnaceInventory(String title, int id) {
        super(title, id, 2, "Smelt", 1, 5, new HashSet<>(Arrays.asList(0, 1, 9, 10)));
    }

    @Override
    public List<AbstractRecipe> getRecipes() {
        return RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.SMELTING_RECIPE, true);
    }

    @Override
    public int getPaginationControlsOffset() {
        return 3;
    }
}
