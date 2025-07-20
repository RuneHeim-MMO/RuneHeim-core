package org.zuma.examplePlugin.domain.recipes.meta;

import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.recipes.IngredientRecord;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;

import java.util.List;

public record ConcreteRecipeMeta(
        int id,
        int craftTimeMs, List<SkillRequirement> requirements,
        List<IngredientRecord> ingredients, @Nullable ItemRecord successResult,
        @Nullable ItemRecord failResult, List<SkillXpRecord> successXp, @Nullable Long cost) {
}
