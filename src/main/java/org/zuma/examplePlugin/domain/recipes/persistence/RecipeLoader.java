package org.zuma.examplePlugin.domain.recipes.persistence;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.recipes.RecipeFactory;
import org.zuma.examplePlugin.domain.recipes.RecipeRegistry;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.meta.AbstractRecipeMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class RecipeLoader {
    private final ItemRegistry itemRegistry;
    private final RecipeRegistry recipeRegistry;
    private final RecipeRepository repository;
    private final RecipeFactory factory;

    public RecipeLoader(ItemRegistry itemRegistry, RecipeRegistry recipeRegistry, RecipeRepository repository, RecipeFactory factory) {
        this.itemRegistry = itemRegistry;
        this.recipeRegistry = recipeRegistry;
        this.repository = repository;
        this.factory = factory;
    }

    public void loadAllRecipes() {
        this.repository.getAll().forEach(entity -> registerRecipe(entity.meta(), entity.json()));
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + this.recipeRegistry.getAllRecipes().size() + " recipes.");
    }

    private void registerRecipe(AbstractRecipeMeta meta, @Nullable VersionedJson json) {
        ItemStack itemStack = new ItemStack(Material.BARRIER);

        if (meta.successResult() != null) {
            AbstractItem item = this.itemRegistry.getItem(meta.successResult().getItemId());

            if (item == null) {
                RuneQuestPlugin.getRuneLogger().logWithStack(LogLevel.WARN, "Cannot register " + meta.type().name() + " recipe. Recipe result ItemRecord id is invalid.");
                return;
            }

            itemStack = item.createItemStackOfType();
        }

        ItemMeta displayItemMeta = itemStack.getItemMeta();

        displayItemMeta.lore(new ArrayList<>());
        itemStack.setItemMeta(displayItemMeta);

        AbstractRecipe newRecipe = factory.createRecipe(itemStack, meta, json);
        recipeRegistry.registerRecipe(newRecipe.getId(), newRecipe);
    }
}
