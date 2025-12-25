package org.zuma.examplePlugin.domain.gui.impl.individual.recipe;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.gui.base.RecipeInventory;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipe.constants.RecipeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class AnvilInventory extends RecipeInventory {

    public AnvilInventory(String title, int id) {
        super(title, id, 6, "Smith", 1, 5, new HashSet<>(Arrays.asList(0, 1, 9, 10)));
    }

    @Override
    public List<AbstractRecipe> getRecipes() {
        return RuneQuestPlugin.getRecipeRegistry().getRecipesByType(RecipeType.SMITHING_RECIPE, true);
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        boolean hasHammer = PlayerUtils.hasItems(player, List.of(new ItemRecord(ItemId.SMITHING_HAMMER, 1)));

        if (!hasHammer) {
            HudService.showFailTitle(player, "You need a hammer to operate this!");
            return false;
        }

        return super.attemptOpenInventory(player, onFail);
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }

}
