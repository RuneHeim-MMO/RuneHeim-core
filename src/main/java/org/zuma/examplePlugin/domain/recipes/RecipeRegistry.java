package org.zuma.examplePlugin.domain.recipes;

import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class RecipeRegistry {
    private final HashMap<Integer, AbstractRecipe> recipes = new HashMap<>();

    public List<AbstractRecipe> getRecipesByType(RecipeType type) {
        return recipes.values().stream().filter(recipe -> recipe.getType() == type).toList();
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
