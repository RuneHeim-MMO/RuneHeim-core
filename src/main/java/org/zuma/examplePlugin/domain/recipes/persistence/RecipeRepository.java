package org.zuma.examplePlugin.domain.recipes.persistence;

import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeId;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.domain.recipes.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class RecipeRepository {

    public List<AbstractRecipeMeta> getAll() {
        return List.of(
                new AbstractRecipeMeta(RecipeId.TEST_RECIPE.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.COOKING, 1)),
                        List.of(new ItemRecord(ItemId.IRON_ORE, 1)),
                        new ItemRecord(ItemId.IRON_BAR, 1),
                        null, List.of(new SkillXpRecord(SkillType.SMITHING, 5000)))

        );
    }
}
