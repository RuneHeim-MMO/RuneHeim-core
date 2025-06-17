package org.zuma.examplePlugin.domain.recipes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.events.recipe.PlayerCraftingFinishEvent;
import org.zuma.examplePlugin.events.recipe.PlayerCraftingStartEvent;

import java.util.List;

import static org.zuma.examplePlugin.core.utils.ItemUtils.multiplyItemRecords;

public class RecipeHandler {
    private final RecipeRegistry registry;
    private final ItemRegistry itemRegistry;

    public RecipeHandler(RecipeRegistry registry, ItemRegistry itemRegistry) {
        this.registry = registry;
        this.itemRegistry = itemRegistry;
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

        player.useBusyState(recipe.getCraftTimeMs() * times,
                type.getContinuousVerb() + " " + recipeResultNameString,
                type.getPastTenseVerb() + "  " + recipeResultNameString, type.getStepSound(), type.getFinishSound(), (consumer) -> {

                    // If players inventory gets modified during waiting for the craft (e.g. player drops item)
                    if (!PlayerUtils.hasItems(player, totalIngredients)) {
                        HudService.sendFailMessage(player, Component.text("You do not have required ingredients to " + type.getVerb().toLowerCase() + " " + times + "x ").append(resultComponent));
                        return;
                    }

                    boolean itemsRemoveSuccess = PlayerUtils.removeItems(player, totalIngredients);

                    if (!itemsRemoveSuccess) {
                        HudService.sendFailMessage(player, Component.text("Failed removing items from player inventory."));
                        return;
                    }


                    int successCrafts = getSuccessCraftsAmount(player, recipe, times);
                    int failedCrafts = times - successCrafts;

                    PlayerCraftingFinishEvent successEvent = new PlayerCraftingFinishEvent(player, recipe, successCrafts, failedCrafts);
                    Bukkit.getServer().getPluginManager().callEvent(successEvent);

                    if (successEvent.isCancelled()) {
                        return;
                    }

                    if (successCrafts > 0) {
                        player.addItemOrDrop(new ItemRecord(recipe.getSuccessResult().getItemId(), recipe.getSuccessResult().getAmount() * successCrafts));
                        recipe.onCraftFinish(player, successCrafts);
                        player.getPlayer().sendMessage(Component.text("Successfully " + type.getPastTenseVerb().toLowerCase() + " " + successCrafts + "x ").append(resultComponent).color(Color.SUCCESS.asColor()));
                        type.getFinishSound().play(player);
                    }

                    if (failedCrafts > 0 && recipe.getFailResult() != null) {
                        player.addItemOrDrop(new ItemRecord(recipe.getFailResult().getItemId(), recipe.getSuccessResult().getAmount() * failedCrafts));
                    }


                });

        return true;
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
