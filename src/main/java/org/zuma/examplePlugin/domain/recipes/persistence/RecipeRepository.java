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
                new AbstractRecipeMeta(RecipeId.BRONZE_BAR.getId(), RecipeType.SMELTING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 1)),
                        List.of(
                                new ItemRecord(ItemId.BRONZE_ORE, 1)
                        ),
                        new ItemRecord(ItemId.BRONZE_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 6.2))
                ),

                new AbstractRecipeMeta(RecipeId.IRON_BAR.getId(), RecipeType.SMELTING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 15)),
                        List.of(new ItemRecord(ItemId.IRON_ORE, 1)),
                        new ItemRecord(ItemId.IRON_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 12.5))
                ),

                new AbstractRecipeMeta(RecipeId.STEEL_BAR.getId(), RecipeType.SMELTING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 30)),
                        List.of(
                                new ItemRecord(ItemId.IRON_ORE, 1)
                                // new ItemRecord(ItemId.COAL, 2)
                        ),
                        new ItemRecord(ItemId.STEEL_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 17.5))
                ),

                new AbstractRecipeMeta(RecipeId.MITHRIL_BAR.getId(), RecipeType.SMELTING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 50)),
                        List.of(
                                new ItemRecord(ItemId.MITHRIL_ORE, 1)
                                //new ItemRecord(ItemId.COAL, 4)
                        ),
                        new ItemRecord(ItemId.MITHRIL_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 30.0))
                ),

                new AbstractRecipeMeta(RecipeId.ADAMANTITE_BAR.getId(), RecipeType.SMELTING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 70)),
                        List.of(
                                new ItemRecord(ItemId.ADAMANTITE_ORE, 1)
                                // new ItemRecord(ItemId.COAL, 6)
                        ),
                        new ItemRecord(ItemId.ADAMANTITE_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5))
                ),

                new AbstractRecipeMeta(RecipeId.RUNITE_BAR.getId(), RecipeType.SMELTING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 85)),
                        List.of(
                                new ItemRecord(ItemId.RUNITE_ORE, 1)
                                // new ItemRecord(ItemId.COAL, 8)
                        ),
                        new ItemRecord(ItemId.RUNITE_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0))
                )
        );
    }
}
