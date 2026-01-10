package org.zuma.examplePlugin.domain.recipe.impl;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipe.constants.RecipeType;
import org.zuma.examplePlugin.domain.recipe.meta.AbstractRecipeMeta;

public class BrewingRecipe extends AbstractRecipe {
    public BrewingRecipe(ItemStack displayItemstack, AbstractRecipeMeta meta) {
        super(displayItemstack, new AbstractRecipeMeta(meta.id(), RecipeType.BREWING_RECIPE, meta.craftTimeMs(), meta.requirements(), meta.ingredients(), meta.successResult(), meta.failResult(), meta.successXp(), meta.cost()));
    }

    @Override
    public boolean isCraftSuccessful(RunePlayer player) {
        return true;
    }

    @Override
    public int getChanceOfSuccess(RunePlayer player) {
        return 100;
    }

}
