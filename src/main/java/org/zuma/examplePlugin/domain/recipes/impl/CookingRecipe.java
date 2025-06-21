package org.zuma.examplePlugin.domain.recipes.impl;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.domain.recipes.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.recipes.meta.ConcreteRecipeMeta;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.concurrent.ThreadLocalRandom;

public class CookingRecipe extends AbstractRecipe {
    private final SkillRequirement mainRequirement;
    private final int MINIMUM_SUCCESS_CHANCE = 30;

    public CookingRecipe(ItemStack displayItemstack, ConcreteRecipeMeta meta) {
        super(displayItemstack, new AbstractRecipeMeta(meta.id(), RecipeType.COOKING_RECIPE, meta.craftTimeMs(), meta.requirements(), meta.ingredients(), meta.successResult(), meta.failResult(), meta.successXp()));

        mainRequirement = super.getRequirements().stream()
                .filter(req -> req.type() == SkillType.COOKING)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isCraftSuccessful(RunePlayer player) {
        if (mainRequirement == null) {
            return true;
        }

        int successChance = PlayerUtils.getCraftSuccessChance(player, mainRequirement, MINIMUM_SUCCESS_CHANCE);
        return ThreadLocalRandom.current().nextInt(0, 101) < successChance;

    }

    @Override
    public int getChanceOfSuccess(RunePlayer player) {
        if (mainRequirement == null) {
            return 100;
        }

        return PlayerUtils.getCraftSuccessChance(player, mainRequirement, MINIMUM_SUCCESS_CHANCE);
    }

}
