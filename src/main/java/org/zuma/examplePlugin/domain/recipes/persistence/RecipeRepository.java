package org.zuma.examplePlugin.domain.recipes.persistence;

import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.recipes.IngredientRecord;
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
                // Bars
                new AbstractRecipeMeta(RecipeId.BRONZE_BAR.getId(), RecipeType.SMELTING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.COPPER_ORE, 1, true),
                                new IngredientRecord(ItemId.TIN_ORE, 1, true)
                        ),
                        new ItemRecord(ItemId.BRONZE_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 6.2))
                ),

                new AbstractRecipeMeta(RecipeId.IRON_BAR.getId(), RecipeType.SMELTING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 15)),
                        List.of(new IngredientRecord(ItemId.IRON_ORE, 1, true)),
                        new ItemRecord(ItemId.IRON_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 12.5))
                ),

                new AbstractRecipeMeta(RecipeId.STEEL_BAR.getId(), RecipeType.SMELTING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 30)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_ORE, 1, true),
                                new IngredientRecord(ItemId.COAL_ORE, 2, true)
                        ),
                        new ItemRecord(ItemId.STEEL_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 17.5))
                ),

                new AbstractRecipeMeta(RecipeId.MITHRIL_BAR.getId(), RecipeType.SMELTING_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 50)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_ORE, 1, true),
                                new IngredientRecord(ItemId.COAL_ORE, 4, true)
                        ),
                        new ItemRecord(ItemId.MITHRIL_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 30.0))
                ),

                new AbstractRecipeMeta(RecipeId.ADAMANTITE_BAR.getId(), RecipeType.SMELTING_RECIPE, 10000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 70)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANTITE_ORE, 1, true),
                                new IngredientRecord(ItemId.COAL_ORE, 6, true)
                        ),
                        new ItemRecord(ItemId.ADAMANTITE_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5))
                ),

                new AbstractRecipeMeta(RecipeId.RUNITE_BAR.getId(), RecipeType.SMELTING_RECIPE, 15000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 85)),
                        List.of(
                                new IngredientRecord(ItemId.RUNITE_ORE, 1, true),
                                new IngredientRecord(ItemId.COAL_ORE, 8, true)
                        ),
                        new ItemRecord(ItemId.RUNITE_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0))
                ),

                // Food

                new AbstractRecipeMeta(RecipeId.COOKED_HERRING.getId(), RecipeType.COOKING_RECIPE, 2200,
                        List.of(new SkillRequirement(SkillType.COOKING, 5)),
                        List.of(new IngredientRecord(ItemId.RAW_HERRING, 1, true)),
                        new ItemRecord(ItemId.COOKED_HERRING, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 50))
                ),

                new AbstractRecipeMeta(RecipeId.COOKED_MINNOW.getId(), RecipeType.COOKING_RECIPE, 2000,
                        List.of(new SkillRequirement(SkillType.COOKING, 1)),
                        List.of(new IngredientRecord(ItemId.RAW_MINNOW, 1, true)),
                        new ItemRecord(ItemId.COOKED_MINNOW, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 10))
                ),

                new AbstractRecipeMeta(RecipeId.COOKED_TROUT.getId(), RecipeType.COOKING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.COOKING, 15)),
                        List.of(new IngredientRecord(ItemId.RAW_TROUT, 1, true)),
                        new ItemRecord(ItemId.COOKED_TROUT, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 70))
                ),

                new AbstractRecipeMeta(RecipeId.COOKED_SALMON.getId(), RecipeType.COOKING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.COOKING, 25)),
                        List.of(new IngredientRecord(ItemId.RAW_SALMON, 1, true)),
                        new ItemRecord(ItemId.COOKED_SALMON, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 90))
                ),

                new AbstractRecipeMeta(RecipeId.COOKED_TUNA.getId(), RecipeType.COOKING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.COOKING, 30)),
                        List.of(new IngredientRecord(ItemId.RAW_TUNA, 1, true)),
                        new ItemRecord(ItemId.COOKED_TUNA, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 100))
                ),

                new AbstractRecipeMeta(RecipeId.COOKED_CRAWLER.getId(), RecipeType.COOKING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.COOKING, 20)),
                        List.of(new IngredientRecord(ItemId.RAW_CRAWLER, 1, true)),
                        new ItemRecord(ItemId.COOKED_CRAWLER, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 85))
                ),

                new AbstractRecipeMeta(RecipeId.COOKED_PIKE.getId(), RecipeType.COOKING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.COOKING, 20)),
                        List.of(new IngredientRecord(ItemId.RAW_PIKE, 1, true)),
                        new ItemRecord(ItemId.COOKED_PIKE, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 80))
                ),

                new AbstractRecipeMeta(RecipeId.COOKED_EEL.getId(), RecipeType.COOKING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.COOKING, 28)),
                        List.of(new IngredientRecord(ItemId.RAW_EEL, 1, true)),
                        new ItemRecord(ItemId.COOKED_EEL, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 95))
                ),

                new AbstractRecipeMeta(RecipeId.COOKED_SHARK.getId(), RecipeType.COOKING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.COOKING, 80)),
                        List.of(new IngredientRecord(ItemId.RAW_SHARK, 1, true)),
                        new ItemRecord(ItemId.COOKED_SHARK, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 210))
                ),

                new AbstractRecipeMeta(RecipeId.COOKED_TURTLE.getId(), RecipeType.COOKING_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.COOKING, 90)),
                        List.of(new IngredientRecord(ItemId.RAW_TURTLE, 1, true)),
                        new ItemRecord(ItemId.COOKED_TURTLE, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 250))
                ),

                new AbstractRecipeMeta(RecipeId.COOKED_RAY.getId(), RecipeType.COOKING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.COOKING, 95)),
                        List.of(new IngredientRecord(ItemId.RAW_RAY, 1, true)),
                        new ItemRecord(ItemId.COOKED_RAY, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 300))
                ),


                // Helmets
                new AbstractRecipeMeta(RecipeId.BRONZE_HELMET.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_HELMET, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 12.5))
                ),

                new AbstractRecipeMeta(RecipeId.IRON_HELMET.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 15)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.IRON_HELMET, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0))
                ),

                new AbstractRecipeMeta(RecipeId.STEEL_HELMET.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 30)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.STEEL_HELMET, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5))
                ),

                new AbstractRecipeMeta(RecipeId.MITHRIL_HELMET.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 50)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_HELMET, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0))
                ),

                new AbstractRecipeMeta(RecipeId.ADAMANTITE_HELMET.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 70)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANTITE_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.ADAMANTITE_HELMET, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62.5))
                ),

                new AbstractRecipeMeta(RecipeId.RUNITE_HELMET.getId(), RecipeType.SMITHING_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 85)),
                        List.of(
                                new IngredientRecord(ItemId.RUNITE_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.RUNITE_HELMET, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0))
                ),

                // Platebody
                new AbstractRecipeMeta(RecipeId.BRONZE_BODY.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 4)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_BODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 18.8))
                ),

                new AbstractRecipeMeta(RecipeId.IRON_BODY.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 20)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.IRON_BODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5))
                ),

                new AbstractRecipeMeta(RecipeId.STEEL_BODY.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 52)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 5, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.STEEL_BODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0))
                ),

                new AbstractRecipeMeta(RecipeId.MITHRIL_BODY.getId(), RecipeType.SMITHING_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 70)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 5, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_BODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 112.5))
                ),

                new AbstractRecipeMeta(RecipeId.ADAMANTITE_BODY.getId(), RecipeType.SMITHING_RECIPE, 9000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 80)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANTITE_BAR, 6, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.ADAMANTITE_BODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 150.0))
                ),

                new AbstractRecipeMeta(RecipeId.RUNITE_BODY.getId(), RecipeType.SMITHING_RECIPE, 12000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 99)),
                        List.of(
                                new IngredientRecord(ItemId.RUNITE_BAR, 8, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.RUNITE_BODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 187.5))
                ),


                // Platelegs

                new AbstractRecipeMeta(RecipeId.BRONZE_PLATELEGS.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 7)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_PLATELEGS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 18.8))
                ),

                new AbstractRecipeMeta(RecipeId.IRON_PLATELEGS.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 25)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.IRON_PLATELEGS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5))
                ),

                new AbstractRecipeMeta(RecipeId.STEEL_PLATELEGS.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 55)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 4, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.STEEL_PLATELEGS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0))
                ),

                new AbstractRecipeMeta(RecipeId.MITHRIL_PLATELEGS.getId(), RecipeType.SMITHING_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 72)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 5, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_PLATELEGS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 112.5))
                ),

                new AbstractRecipeMeta(RecipeId.ADAMANTITE_PLATELEGS.getId(), RecipeType.SMITHING_RECIPE, 9000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 85)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANTITE_BAR, 7, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.ADAMANTITE_PLATELEGS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 150.0))
                ),

                new AbstractRecipeMeta(RecipeId.RUNITE_PLATELEGS.getId(), RecipeType.SMITHING_RECIPE, 12000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 99)),
                        List.of(
                                new IngredientRecord(ItemId.RUNITE_BAR, 8, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.RUNITE_PLATELEGS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 187.5))
                ),

                // Boots

                new AbstractRecipeMeta(RecipeId.BRONZE_BOOTS.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 7)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_BOOTS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 13.8))
                ),

                new AbstractRecipeMeta(RecipeId.IRON_BOOTS.getId(), RecipeType.SMITHING_RECIPE, 3500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 21)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.IRON_BOOTS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0))
                ),

                new AbstractRecipeMeta(RecipeId.STEEL_BOOTS.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 55)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.STEEL_BOOTS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0))
                ),

                new AbstractRecipeMeta(RecipeId.MITHRIL_BOOTS.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 73)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_BOOTS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0))
                ),

                new AbstractRecipeMeta(RecipeId.ADAMANTITE_BOOTS.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 85)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANTITE_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.ADAMANTITE_BOOTS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 100.0))
                ),

                new AbstractRecipeMeta(RecipeId.RUNITE_BOOTS.getId(), RecipeType.SMITHING_RECIPE, 9000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 99)),
                        List.of(
                                new IngredientRecord(ItemId.RUNITE_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.RUNITE_BOOTS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 125.0))
                ),

                // Swords
                new AbstractRecipeMeta(RecipeId.BRONZE_SWORD.getId(), RecipeType.SMITHING_RECIPE, 2500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_SWORD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 12.5))
                ),

                new AbstractRecipeMeta(RecipeId.IRON_SWORD.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.IRON_SWORD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0))
                ),

                new AbstractRecipeMeta(RecipeId.STEEL_SWORD.getId(), RecipeType.SMITHING_RECIPE, 3500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 22)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.STEEL_SWORD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5))
                ),

                new AbstractRecipeMeta(RecipeId.MITHRIL_SWORD.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 30)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_SWORD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0))
                ),

                new AbstractRecipeMeta(RecipeId.ADAMANTITE_SWORD.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 50)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANTITE_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.ADAMANTITE_SWORD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62.5))
                ),

                new AbstractRecipeMeta(RecipeId.RUNITE_SWORD.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 70)),
                        List.of(
                                new IngredientRecord(ItemId.RUNITE_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.RUNITE_SWORD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0))
                ),

// Daggers
                new AbstractRecipeMeta(RecipeId.BRONZE_DAGGER.getId(), RecipeType.SMITHING_RECIPE, 2500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_DAGGER, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 12.5))
                ),

                new AbstractRecipeMeta(RecipeId.IRON_DAGGER.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.IRON_DAGGER, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0))
                ),

                new AbstractRecipeMeta(RecipeId.STEEL_DAGGER.getId(), RecipeType.SMITHING_RECIPE, 3500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 22)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.STEEL_DAGGER, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5))
                ),

                new AbstractRecipeMeta(RecipeId.MITHRIL_DAGGER.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 30)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_DAGGER, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0))
                ),

                new AbstractRecipeMeta(RecipeId.ADAMANTITE_DAGGER.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 50)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANTITE_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.ADAMANTITE_DAGGER, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62.5))
                ),

                new AbstractRecipeMeta(RecipeId.RUNITE_DAGGER.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 70)),
                        List.of(
                                new IngredientRecord(ItemId.RUNITE_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.RUNITE_DAGGER, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0))
                ),

                // Battleaxes
                new AbstractRecipeMeta(RecipeId.BRONZE_BATTLEAXE.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 6)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_BATTLEAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 13.8))
                ),

                new AbstractRecipeMeta(RecipeId.IRON_BATTLEAXE.getId(), RecipeType.SMITHING_RECIPE, 3500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 22)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.IRON_BATTLEAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0))
                ),

                new AbstractRecipeMeta(RecipeId.STEEL_BATTLEAXE.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 33)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.STEEL_BATTLEAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5))
                ),

                new AbstractRecipeMeta(RecipeId.MITHRIL_BATTLEAXE.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 46)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_BATTLEAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0))
                ),

                new AbstractRecipeMeta(RecipeId.ADAMANTITE_BATTLEAXE.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 61)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANTITE_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.ADAMANTITE_BATTLEAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62.5))
                ),

                new AbstractRecipeMeta(RecipeId.RUNITE_BATTLEAXE.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 75)),
                        List.of(
                                new IngredientRecord(ItemId.RUNITE_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.RUNITE_BATTLEAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0))
                ),

                // Axes
                new AbstractRecipeMeta(RecipeId.BRONZE_AXE.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_AXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 13.8))
                ),

                new AbstractRecipeMeta(RecipeId.IRON_AXE.getId(), RecipeType.SMITHING_RECIPE, 3500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.IRON_AXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0))
                ),

                new AbstractRecipeMeta(RecipeId.STEEL_AXE.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 22)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.STEEL_AXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5))
                ),

                new AbstractRecipeMeta(RecipeId.MITHRIL_AXE.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 33)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_AXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0))
                ),

                new AbstractRecipeMeta(RecipeId.ADAMANTITE_AXE.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 46)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANTITE_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.ADAMANTITE_AXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62.5))
                ),

                new AbstractRecipeMeta(RecipeId.RUNITE_AXE.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 61)),
                        List.of(
                                new IngredientRecord(ItemId.RUNITE_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.RUNITE_AXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0))
                ),

                new AbstractRecipeMeta(RecipeId.BRONZE_PICKAXE.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_PICKAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 13.8))
                ),

                new AbstractRecipeMeta(RecipeId.IRON_PICKAXE.getId(), RecipeType.SMITHING_RECIPE, 3500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 1, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.IRON_PICKAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0))
                ),

                new AbstractRecipeMeta(RecipeId.STEEL_PICKAXE.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 22)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.STEEL_PICKAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5))
                ),

                new AbstractRecipeMeta(RecipeId.MITHRIL_PICKAXE.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 33)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 2, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_PICKAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0))
                ),

                new AbstractRecipeMeta(RecipeId.ADAMANTITE_PICKAXE.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 46)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANTITE_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.ADAMANTITE_PICKAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62.5))
                ),

                new AbstractRecipeMeta(RecipeId.RUNITE_PICKAXE.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 61)),
                        List.of(
                                new IngredientRecord(ItemId.RUNITE_BAR, 3, true),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false)
                        ),
                        new ItemRecord(ItemId.RUNITE_PICKAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0))
                )


        );
    }
}
