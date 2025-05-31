package org.zuma.examplePlugin.domain.recipes;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.base.Recipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.events.recipe.PlayerRecipeCraftingStart;
import org.zuma.examplePlugin.events.recipe.PlayerRecipeCraftingSuccess;

import static org.zuma.examplePlugin.core.utils.ItemUtils.multiplyItemRecords;

public class RecipeHandler {
    private final RecipeRegistry registry;

    public RecipeHandler(RecipeRegistry registry) {
        this.registry = registry;
    }

    public void craftRecipe(RunePlayer player, Recipe recipe, int times) {
        if (times <= 0) {
            return;
        }

        RecipeType type = recipe.getType();
        Component resultComponent = recipe.getDisplayItemstack().displayName();

        if (player.isBusy()) {
            player.showFailedMessage(Component.text("You are already busy with something else!"));
            return;
        }

        if (!player.meetsRequirements(recipe)) {
            player.showFailedMessage(Component.text("You do not meet requirements to " + type.getVerb() + " ").append(resultComponent));
            return;
        }

        if (!PlayerUtils.hasItems(player, multiplyItemRecords(recipe.getIngredients(), times))) {
            player.showFailedMessage(Component.text("You do not have required ingredients to " + type.getVerb() + " " + times + "x ").append(resultComponent));
            return;
        }

        PlayerRecipeCraftingStart event = new PlayerRecipeCraftingStart(player, recipe, times);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        player.useBusyState(recipe.getCraftTimeMs() * times, type.getContinuousVerb(), type.getPastTenseVerb(), type.getStepSound(), type.getFinishSound(), (consumer) -> {
            if (!PlayerUtils.hasItems(player, multiplyItemRecords(recipe.getIngredients(), times))) {
                player.showFailedMessage(Component.text("You do not have required ingredients to " + type.getVerb() + " " + times + "x ").append(resultComponent));
                return;
            }

            boolean itemsRemoveSuccess = PlayerUtils.removeItems(player, multiplyItemRecords(recipe.getIngredients(), times));

            if (!itemsRemoveSuccess) {
                player.showFailedMessage(Component.text("Failed removing items from player inventory."));
                return;
            }

            // TODO: success/fail logic
            PlayerRecipeCraftingSuccess successEvent = new PlayerRecipeCraftingSuccess(player, recipe, times);
            Bukkit.getServer().getPluginManager().callEvent(successEvent);

            if (successEvent.isCancelled()) {
                return;
            }

            player.addItemOrDrop(new ItemRecord(recipe.getSuccessResult().getItemId(), recipe.getSuccessResult().getAmount() * times));
            player.getPlayer().sendMessage(Component.text("Successfully " + type.getPastTenseVerb() + " ").append(resultComponent).color(Colors.SUCCESS.asColor()));
            type.getFinishSound().play(player);
        });
    }


    public RecipeRegistry getRegistry() {
        return registry;
    }
}
