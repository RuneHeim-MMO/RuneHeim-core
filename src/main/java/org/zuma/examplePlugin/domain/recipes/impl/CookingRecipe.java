package org.zuma.examplePlugin.domain.recipes.impl;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.domain.recipes.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.recipes.meta.ConcreteRecipeMeta;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

public class CookingRecipe extends AbstractRecipe {
    private static final int MAX_LEVEL = 99;
    private static final int SAFE_OFFSET = 10;

    public CookingRecipe(ItemStack displayItemstack, ConcreteRecipeMeta meta) {
        super(displayItemstack, new AbstractRecipeMeta(meta.id(), RecipeType.COOKING_RECIPE, meta.craftTimeMs(), meta.requirements(), meta.ingredients(), meta.successResult(), meta.failResult(), meta.successXp()));
    }

    @Override
    public boolean isCraftSuccessful(RunePlayer player) {
        return true;
    }

    @Override
    public int getChanceOfSuccess(RunePlayer player) {
        SkillRequirement cookingReq = super.getRequirements().stream()
                .filter(req -> req.type() == SkillType.COOKING)
                .findFirst()
                .orElse(null);

        if (cookingReq == null) return 100; // No cooking requirement? Always succeed

        int requiredLevel = cookingReq.minimumLevel();
        int playerLevel = player.getSkill(SkillType.COOKING).getLevel();

        if (playerLevel >= MAX_LEVEL || playerLevel >= requiredLevel + SAFE_OFFSET) {
            return 100;
        }

        if (playerLevel < requiredLevel) {
            // Below requirement: minimum success chance (e.g. 30%)
            return 30;
        }

        // Between [requiredLevel] and [requiredLevel + SAFE_OFFSET]
        int offset = playerLevel - requiredLevel;
        float progress = offset / (float) SAFE_OFFSET;
        return Math.round(30 + (70 * progress)); // from 30% to 100%
    }

}
