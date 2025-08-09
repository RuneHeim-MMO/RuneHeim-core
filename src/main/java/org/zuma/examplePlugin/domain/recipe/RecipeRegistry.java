package org.zuma.examplePlugin.domain.recipe;

import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipe.constants.RecipeType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class RecipeRegistry {
    private final HashMap<Integer, AbstractRecipe> recipes = new HashMap<>();

    public List<AbstractRecipe> getRecipesByType(RecipeType type, boolean sortAsc) {
        Stream<AbstractRecipe> stream = recipes.values().stream()
                .filter(recipe -> recipe.getType() == type);

        if (sortAsc) {
            stream = stream.sorted(Comparator.comparingInt(recipe ->
                    recipe.getRequirements().stream()
                            .mapToInt(SkillRequirement::minimumLevel)
                            .sum()
            ));
        }

        return stream.toList();
    }

    public @Nullable AbstractRecipe getRecipeById(int id) {
        return recipes.get(id);
    }

    public Collection<AbstractRecipe> getAllRecipes() {
        return recipes.values();
    }

    public void registerRecipe(int id, AbstractRecipe recipe) {
        AbstractRecipe existingRecipe = recipes.get(id);

        if (existingRecipe != null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot add recipe with id " + id + "! Recipe with this id already exists.");
            return;
        }
        this.recipes.put(id, recipe);
    }


}
