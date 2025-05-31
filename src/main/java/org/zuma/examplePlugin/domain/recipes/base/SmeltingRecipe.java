package org.zuma.examplePlugin.domain.recipes.base;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;

import java.util.List;

public class SmeltingRecipe extends AbstractRecipe {
    public SmeltingRecipe(int id, ItemStack displayItemstack, int craftTimeMs, List<SkillRequirement> requirements, List<ItemRecord> ingredients, ItemRecord successResult, @Nullable ItemRecord failResult, List<SkillXpRecord> successXp) {
        super(id, displayItemstack, RecipeType.SMELTING_RECIPE, craftTimeMs, requirements, ingredients, successResult, failResult, successXp);
    }

    @Override
    public boolean isCraftSuccessful(RunePlayer player) {
        return true;
    }

    @Override
    public int getChanceOfSuccess(RunePlayer player) {
        return 80;
    }

    @Override
    public void onCraft(RunePlayer player, int craftAmount) {

    }
}
