package org.zuma.examplePlugin.domain.recipes;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.impl.CookingRecipe;
import org.zuma.examplePlugin.domain.recipes.impl.CraftingRecipe;
import org.zuma.examplePlugin.domain.recipes.impl.SmeltingRecipe;
import org.zuma.examplePlugin.domain.recipes.impl.SmithingRecipe;
import org.zuma.examplePlugin.domain.recipes.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.recipes.meta.ConcreteRecipeMeta;

public class RecipeFactory {

    public AbstractRecipe createRecipe(ItemStack displayItem, AbstractRecipeMeta meta) {
        final ConcreteRecipeMeta recipeMeta = new ConcreteRecipeMeta(meta.id(), meta.craftTimeMs(), meta.requirements(), meta.ingredients(), meta.successResult(), meta.failResult(), meta.successXp());

        return switch (meta.type()) {
            case SMELTING_RECIPE -> new SmeltingRecipe(displayItem, recipeMeta);
            case SMITHING_RECIPE -> new SmithingRecipe(displayItem, recipeMeta);
            case COOKING_RECIPE -> new CookingRecipe(displayItem, recipeMeta);
            default -> new CraftingRecipe(displayItem, recipeMeta);

        };
    }
}
