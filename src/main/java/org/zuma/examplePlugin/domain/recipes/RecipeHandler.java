package org.zuma.examplePlugin.domain.recipes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.cooldown.CooldownHandler;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.events.recipe.PlayerCraftingFinishEvent;
import org.zuma.examplePlugin.events.recipe.PlayerCraftingStartEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.zuma.examplePlugin.core.utils.ItemUtils.multiplyItemRecords;

public class RecipeHandler {
    private final RecipeRegistry registry;
    private final ArrayList<AbstractRecipe> validInventoryRecipes = new ArrayList<>();
    private final CooldownHandler cooldownHandler = new CooldownHandler();


    public RecipeHandler(RecipeRegistry registry) {
        this.registry = registry;

        validInventoryRecipes.addAll(registry.getRecipesByType(RecipeType.INVENTORY_RECIPE, false));
        validInventoryRecipes.addAll(registry.getRecipesByType(RecipeType.CAMPFIRE_RECIPE, false));
    }

    public boolean craftRecipe(RunePlayer player, AbstractRecipe recipe, int times) {
        return recipe.craftRecipe(player, times);
    }


    public boolean handleInventoryRecipeClick(RunePlayer player, Inventory inventory, AbstractItemType firstItem, AbstractItemType secondItem) {
        List<AbstractRecipe> matchingRecipes = validInventoryRecipes.stream()
                .filter(recipe -> recipe.getType() == RecipeType.INVENTORY_RECIPE || recipe.getType() == RecipeType.CAMPFIRE_RECIPE)
                .filter(recipe -> recipe.matchesItemPair(firstItem, secondItem))
                .toList();

        if (matchingRecipes.isEmpty()) {
            return false;
        }

        AbstractRecipe matchingRecipe = matchingRecipes.getFirst();
        return craftRecipe(player, matchingRecipe, 1);
    }


    public RecipeRegistry getRegistry() {
        return registry;
    }

    public int getAvailableRecipesNumber(RunePlayer player) {
        Collection<AbstractRecipe> recipes = registry.getAllRecipes();
        int availableRecipes = 0;

        for (AbstractRecipe recipe : recipes) {
            if (player.meetsRequirements(recipe)) {
                availableRecipes++;
            }
        }

        return availableRecipes;

    }
}
