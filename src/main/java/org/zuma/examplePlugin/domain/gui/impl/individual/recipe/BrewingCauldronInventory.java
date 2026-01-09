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

public class BrewingCauldronInventory extends RecipeInventory {
    public BrewingCauldronInventory(String title, int id) {
        super(title, id, 5, "Process", 1, 5, new HashSet<>(Arrays.asList(
                0, 1, 2, 6, 7, 8,
                9, 10, 11, 15, 16,
                17, 18, 19, 20, 24, 25, 26, 27, 28, 29, 30, 31, 32,
                33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48,
                49, 50, 51, 52, 53)));
    }

    @Override
    public List<AbstractRecipe> getRecipes() {
        List<AbstractRecipe> brewingRecipes = RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.BREWING_RECIPE, false);
        List<AbstractRecipe> essenceRecipes = RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.ESSENCE_EXTRACTION_RECIPE, false);

        Stream<AbstractRecipe> stream = Stream.concat(brewingRecipes.stream(), essenceRecipes.stream());

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
