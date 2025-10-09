package org.zuma.examplePlugin.domain.gui.impl.individual.recipe;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.gui.base.RecipeInventory;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipe.constants.RecipeType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class FletchingTableInventory extends RecipeInventory {
    public FletchingTableInventory(String title, int id) {
        super(title, id, 6, "Craft", 1, 5, new HashSet<>(Arrays.asList(0, 1, 9, 10)));
    }

    @Override
    public List<AbstractRecipe> getRecipes() {
        List<AbstractRecipe> recipes = RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.FLETCHING_TABLE_RECIPE, false);
        List<AbstractRecipe> inventoryRecipes = RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.INVENTORY_RECIPE, false);

        Stream<AbstractRecipe> stream = Stream.concat(
                // Show fletching recipes from inventory recipes
                inventoryRecipes.stream().filter(recipe -> recipe.getSkillRequirements().stream().allMatch(s -> s.type() == SkillType.FLETCHING)),
                recipes.stream());

        return stream.sorted(Comparator.comparingInt(recipe ->
                        recipe.getSkillRequirements().stream()
                                .mapToInt(SkillRequirement::minimumLevel)
                                .sum()))
                .toList();
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }
}
