package org.zuma.examplePlugin.domain.recipe.impl;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipe.constants.RecipeType;
import org.zuma.examplePlugin.domain.recipe.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.concurrent.ThreadLocalRandom;

public class CookingRecipe extends AbstractRecipe {
    private final static int MINIMUM_SUCCESS_CHANCE = 50;
    private final SkillRequirement mainRequirement;

    public CookingRecipe(ItemStack displayItemstack, AbstractRecipeMeta meta) {
        super(displayItemstack, new AbstractRecipeMeta(meta.id(), RecipeType.COOKING_RECIPE, meta.craftTimeMs(), meta.requirements(), meta.ingredients(), meta.successResult(), meta.failResult(), meta.successXp(), meta.cost()));

        mainRequirement = super.getSkillRequirements().stream()
                .filter(req -> req.type() == SkillType.COOKING)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isCraftSuccessful(RunePlayer player) {
        return ThreadLocalRandom.current().nextInt(0, 101) < getChanceOfSuccess(player);

    }

    @Override
    public int getChanceOfSuccess(RunePlayer player) {
        if (mainRequirement == null) {
            return 100;
        }

        return PlayerUtils.getCraftSuccessChance(player, mainRequirement, MINIMUM_SUCCESS_CHANCE);
    }

}
