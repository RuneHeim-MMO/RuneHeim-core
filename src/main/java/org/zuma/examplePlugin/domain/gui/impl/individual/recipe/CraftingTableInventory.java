package org.zuma.examplePlugin.domain.gui.impl.individual.recipe;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.gui.base.RecipeInventory;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipe.constants.RecipeType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class CraftingTableInventory extends RecipeInventory {
    public CraftingTableInventory(String title, int id) {
        super(title, id, 6, "Craft", 1, 5, new HashSet<>(Arrays.asList(0, 1, 9, 10)));
    }

    @Override
    public List<AbstractRecipe> getRecipes() {
        List<AbstractRecipe> inventoryRecipes = RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.INVENTORY_RECIPE, false);
        List<AbstractRecipe> craftingTableRecipes = RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.CRAFTING_TABLE_RECIPE, false);

        Stream<AbstractRecipe> stream = Stream.concat(inventoryRecipes.stream(), craftingTableRecipes.stream());

        return stream.sorted(Comparator.comparingInt(recipe ->
                        recipe.getRequirements().stream()
                                .mapToInt(SkillRequirement::minimumLevel)
                                .sum()))
                .toList();
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }
}
