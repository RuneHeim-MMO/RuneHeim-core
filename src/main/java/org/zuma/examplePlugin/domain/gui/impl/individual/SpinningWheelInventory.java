package org.zuma.examplePlugin.domain.gui.impl.individual;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.gui.base.RecipeInventory;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class SpinningWheelInventory extends RecipeInventory {
    public SpinningWheelInventory(String title, int id) {
        super(title, id, 3, "Spin", 1, 5, new HashSet<>(Arrays.asList(0, 1, 9, 10)));
    }

    @Override
    public List<AbstractRecipe> getRecipes() {
        return RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.SPINNING_WHEEL_RECIPE, true);
    }
}
