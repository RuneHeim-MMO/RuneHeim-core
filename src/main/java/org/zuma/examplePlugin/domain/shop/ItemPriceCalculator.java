package org.zuma.examplePlugin.domain.shop;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.impl.Material;
import org.zuma.examplePlugin.domain.recipe.IngredientRecord;
import org.zuma.examplePlugin.domain.recipe.RecipeRegistry;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;

import javax.annotation.Nullable;
import java.util.*;

public class ItemPriceCalculator {
    private static final double SELL_PRICE_MULTIPLIER = 0.25; // NPCs buy items at 80% of the calculated price
    private static final HashMap<Integer, Integer> priceCache = new HashMap<>(); // id : buy price

    public static void buildCache() {
        priceCache.clear();

        List<AbstractItemType> items = new ArrayList<>(RuneQuestPlugin.getItemRegistry().getAllItems());
        items.sort(Comparator.comparing(item -> item.displayName));

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Algorithmic item price calculation started for " + items.size() + " items...");
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "----------------------------------------------------------------------");

        for (AbstractItemType item : items) {
            Map.Entry<Integer, Integer> buySellPriceKV = getPrice(item);

            priceCache.put(item.id, buySellPriceKV != null ? buySellPriceKV.getKey() : null);

            if (buySellPriceKV != null)
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "1x " + item.displayName + ": \u001B[93mB " + buySellPriceKV.getKey() + "gp | S \u001B[93m" + buySellPriceKV.getValue() + "gp\u001B[0m\u001B[96m (mult: x" + item.getCraftPriceMultiplier() + ").\u001B[0m");
        }
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "----------------------------------------------------------------------");


    }

    @Nullable // Buy price, sell price
    public static Map.Entry<Integer, Integer> getPrice(ItemId id) {
        AbstractItemType item = RuneQuestPlugin.getItemRegistry().getType(id);

        if (item == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "ItemPriceCalculator: Item with id " + id + " not found in registry.");
            return null;
        }

        return getPrice(item);
    }

    @Nullable // Buy price, sell price
    public static Map.Entry<Integer, Integer> getPrice(AbstractItemType item) {
        if (priceCache.containsKey(item.id)) {
            int price = priceCache.get(item.id);

            return Map.entry(priceCache.get(item.id), (int) (price * SELL_PRICE_MULTIPLIER));
        }

        Integer price = calculateFinalPrice(item);
        Integer finalPrice = (price != null && price == 0) ? null : price; // Treat 0 price as undetermined

        priceCache.put(item.id, finalPrice);
        if (finalPrice == null) {
            return null;
        }
        return Map.entry(finalPrice, (int) (finalPrice * SELL_PRICE_MULTIPLIER));
    }

    @Nullable
    // Returns null if price cannot be determined
    private static Integer calculateFinalPrice(AbstractItemType item) {
        if (item instanceof Material material && material.getBasePrice() == null) {
            return null; // Cannot determine price if base price is not set for materials
        }

        Integer basePrice = item instanceof Material material ? material.getBasePrice() : 0;

        RecipeRegistry recipeRegistry = RuneQuestPlugin.getRecipeRegistry();
        AbstractRecipe matchingRecipe = null;
        int craftAmount = 0;

        for (AbstractRecipe recipe : recipeRegistry.getAllRecipes()) {
            ItemRecord successResult = recipe.getSuccessResult();

            if (successResult != null && successResult.getItemId() == item.id) {
                matchingRecipe = recipe;
                craftAmount = successResult.getAmount();
                break;
            }
        }

        // Item cannot be crafted
        if (matchingRecipe == null) {
            return basePrice;
        }

        if (matchingRecipe.getRecipeCost() > 0)
            basePrice += Math.toIntExact(matchingRecipe.getRecipeCost() / craftAmount);

        for (IngredientRecord ingredient : matchingRecipe.getIngredients()) {
            AbstractItemType ingredientItem = RuneQuestPlugin.getItemRegistry().getType(ingredient.itemId());

            if (!(ingredientItem instanceof Material material)) continue;
            if ((!ingredient.isConsumedOnSuccess() || !ingredientItem.isTradeable)) continue;

            Map.Entry<Integer, Integer> buySellKV = getPrice(material);

            if (buySellKV == null) {
                basePrice = null;
                break;
            }

            basePrice += buySellKV.getKey() * ingredient.amount();

        }

        if (basePrice != null) {
            basePrice = (int) (basePrice * item.getCraftPriceMultiplier());
            return basePrice / craftAmount;
        }

        return null;

    }
}
