package org.zuma.examplePlugin.domain.recipes.meta;

import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;

import java.util.List;

public record AbstractRecipeMeta(
        int id,
        RecipeType type, int craftTimeMs, List<SkillRequirement> requirements,
        List<ItemRecord> ingredients, ItemRecord successResult,
        @Nullable ItemRecord failResult, List<SkillXpRecord> successXp) {
}
