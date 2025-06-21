package org.zuma.examplePlugin.domain.recipes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.events.recipe.PlayerCraftingFinishEvent;
import org.zuma.examplePlugin.events.recipe.PlayerCraftingStartEvent;

import java.util.ArrayList;
import java.util.List;

import static org.zuma.examplePlugin.core.utils.ItemUtils.multiplyItemRecords;

public class RecipeHandler {
    private final RecipeRegistry registry;
    private final ItemRegistry itemRegistry;
    private final ArrayList<AbstractRecipe> validInventoryRecipes = new ArrayList<>();


    public RecipeHandler(RecipeRegistry registry, ItemRegistry itemRegistry) {
        this.registry = registry;
        this.itemRegistry = itemRegistry;

        validInventoryRecipes.addAll(registry.getRecipesByType(RecipeType.INVENTORY_RECIPE, false));
        validInventoryRecipes.addAll(registry.getRecipesByType(RecipeType.CAMPFIRE_RECIPE, false));
    }

    public boolean craftRecipe(RunePlayer player, AbstractRecipe recipe, int times) {
        if (times <= 0) {
            return false;
        }

        RecipeType type = recipe.getType();
        Component resultComponent = recipe.getDisplayItemstack(player, itemRegistry).displayName().color(NamedTextColor.WHITE);
        String recipeResultNameString = times + "x " + TextUtils.extractPlainText(resultComponent);

        List<ItemRecord> requiredTools = recipe.getIngredients().stream()
                .filter(record -> !record.isConsumedOnCraft())
                .map(record -> new ItemRecord(record.itemId(), record.amount())).toList();

        List<ItemRecord> totalIngredients = multiplyItemRecords(recipe.getIngredients().stream()
                .filter(IngredientRecord::isConsumedOnCraft)
                .map(record -> new ItemRecord(record.itemId(), record.amount())).toList(), times);


        if (player.isBusy()) {
            HudService.sendFailMessage(player, Component.text("You are already busy with something else!"));
            return false;
        }

        if (!player.meetsRequirements(recipe)) {
            HudService.sendFailMessage(player, Component.text("You do not meet requirements to " + type.getVerb().toLowerCase() + " ").append(resultComponent));
            return false;
        }

        if (!PlayerUtils.hasItems(player, totalIngredients)) {
            HudService.sendFailMessage(player, Component.text("You do not have required ingredients to " + type.getVerb().toLowerCase() + " " + times + "x ").append(resultComponent));
            return false;
        }

        if (!PlayerUtils.hasItems(player, requiredTools)) {
            HudService.sendFailMessage(player, Component.text("You do not have required tools to " + type.getVerb().toLowerCase() + " " + times + "x ").append(resultComponent));
            return false;
        }


        PlayerCraftingStartEvent event = new PlayerCraftingStartEvent(player, recipe, times);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return false;
        }

        type.getStartSound().play(player);
        recipe.onCraftingStart(player);

        player.useBusyState(recipe.getCraftTimeMs() * times,
                type.getContinuousVerb() + " " + recipeResultNameString, "", type.getStepSound(), type.getFinishSound(), () -> {

                    // If players inventory gets modified during waiting for the craft (e.g. player drops item)
                    if (!PlayerUtils.hasItems(player, totalIngredients)) {
                        HudService.sendFailMessage(player, Component.text("You do not have required ingredients to " + type.getVerb().toLowerCase() + " " + times + "x ").append(resultComponent));
                        recipe.onCraftingCancelled(player);
                        return;
                    }

                    if (!PlayerUtils.hasItems(player, requiredTools)) {
                        HudService.sendFailMessage(player, Component.text("You do not have required tools to " + type.getVerb().toLowerCase() + " " + times + "x ").append(resultComponent));
                        recipe.onCraftingCancelled(player);
                        return;
                    }

                    boolean itemsRemoveSuccess = PlayerUtils.removeItems(player, totalIngredients);

                    if (!itemsRemoveSuccess) {
                        HudService.sendFailMessage(player, Component.text("Failed removing items from player inventory."));
                        recipe.onCraftingCancelled(player);
                        return;
                    }


                    int successCrafts = getSuccessCraftsAmount(player, recipe, times);
                    int failedCrafts = times - successCrafts;

                    PlayerCraftingFinishEvent successEvent = new PlayerCraftingFinishEvent(player, recipe, successCrafts, failedCrafts);
                    Bukkit.getServer().getPluginManager().callEvent(successEvent);

                    if (successEvent.isCancelled()) {
                        recipe.onCraftingCancelled(player);
                        return;
                    }

                    if (successCrafts > 0) {
                        // Can be null for recipes that dont produce items (e.g. campfires)
                        if (recipe.getSuccessResult() != null) {
                            player.addItemOrDrop(new ItemRecord(recipe.getSuccessResult().getItemId(), recipe.getSuccessResult().getAmount() * successCrafts));
                        }

                        recipe.onCraftFinish(player, successCrafts);
                        recipe.onCraftingSuccess(player);
                        player.getPlayer().sendMessage(Component.text("Successfully " + type.getPastTenseVerb().toLowerCase() + " " + successCrafts + "x ").append(resultComponent).color(Color.SUCCESS.asColor()));
                        type.getFinishSound().play(player);
                    } else {

                        if (times == 1) {
                            recipe.onCraftingCancelled(player);
                        }
                    }

                    if (failedCrafts > 0 && recipe.getFailResult() != null) {
                        int itemsYieldAmntOnSuccess = recipe.getSuccessResult() != null ? recipe.getSuccessResult().getAmount() : 1;
                        player.addItemOrDrop(new ItemRecord(recipe.getFailResult().getItemId(), itemsYieldAmntOnSuccess * failedCrafts));
                    }

                    if (times == 1 && failedCrafts == 1) {
                        player.getPlayer().sendMessage(Component.text("Failed to " + type.getVerb().toLowerCase() + " a ").append(resultComponent).color(Color.ERROR.asColor()));
                    }


                }, () -> recipe.onCraftingCancelled(player));

        return true;
    }

    public boolean handleInventoryRecipeClick(RunePlayer player, Inventory inventory, AbstractItem firstItem, AbstractItem secondItem) {

        List<AbstractRecipe> matchingRecipes = validInventoryRecipes.stream()
                .filter(recipe -> {
                    boolean hasFirstItem = false;
                    boolean hasSecondItem = false;

                    for (IngredientRecord record : recipe.getIngredients()) {
                        int id = record.itemId().asInt();
                        if (firstItem.id == id) hasFirstItem = true;
                        if (secondItem.id == id) hasSecondItem = true;
                        if (hasFirstItem && hasSecondItem) return true;
                    }
                    return false;
                }).toList();


        if (matchingRecipes.isEmpty()) {
            return false;
        }

        AbstractRecipe matchingRecipe = matchingRecipes.getFirst();
        return craftRecipe(player, matchingRecipe, 1);
    }


    private int getSuccessCraftsAmount(RunePlayer player, AbstractRecipe recipe, int craftAmount) {
        int successCrafts = 0;

        for (int i = 0; i < craftAmount; i++) {
            if (recipe.isCraftSuccessful(player)) {
                successCrafts++;
            }
        }

        return successCrafts;
    }


    public RecipeRegistry getRegistry() {
        return registry;
    }
}
