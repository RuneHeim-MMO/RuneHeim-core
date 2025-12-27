package org.zuma.examplePlugin.domain.recipe.persistence;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.recipe.IngredientRecord;
import org.zuma.examplePlugin.domain.recipe.constants.RecipeId;
import org.zuma.examplePlugin.domain.recipe.constants.RecipeType;
import org.zuma.examplePlugin.domain.recipe.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.recipe.meta.v1.CampfireRecipeMeta;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class RecipeRepository {
    private final Gson gson = new Gson();

    public List<RecipeEntity> getAll() {
        return List.of(
                // Furniture

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COMBAT_DUMMY_FURNITURE.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 44)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 4, true, false),
                                new IngredientRecord(ItemId.MAPLE_LOG, 8, true, false),
                                new IngredientRecord(ItemId.HARD_LEATHER, 8, true, false),
                                new IngredientRecord(ItemId.THREAD, 8, true, false),

                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false),
                                new IngredientRecord(ItemId.CHISEL, 1, false, false),
                                new IngredientRecord(ItemId.NEEDLE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.COMBAT_DUMMY_FURNITURE, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 130)), null), null),


                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ANVIL_FURNITURE.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 10000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 50)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 64, true, false),

                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ANVIL_FURNITURE, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 150)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STOVE_FURNITURE.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 10000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 53)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 48, true, false),
                                new IngredientRecord(ItemId.PURE_COAL_ORE, 32, true, false),

                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false),
                                new IngredientRecord(ItemId.FLINT_AND_STEEL, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STOVE_FURNITURE, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 167)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.CRAFTING_TABLE_FURNITURE.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 45)),
                        List.of(
                                new IngredientRecord(ItemId.YEW_LOG, 8, true, false),
                                new IngredientRecord(ItemId.THICK_HIDE, 4, true, false),
                                new IngredientRecord(ItemId.LINEN_FABRIC, 4, true, false),

                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false),
                                new IngredientRecord(ItemId.CHISEL, 1, false, false)
                        ),
                        new ItemRecord(ItemId.CRAFTING_TABLE_FURNITURE, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 140)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.FLETCHING_TABLE_FURNITURE.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 48)),
                        List.of(
                                new IngredientRecord(ItemId.YEW_LOG, 8, true, false),
                                new IngredientRecord(ItemId.HARD_LEATHER, 4, true, false),
                                new IngredientRecord(ItemId.LINEN_FABRIC, 4, true, false),

                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false),
                                new IngredientRecord(ItemId.CHISEL, 1, false, false)
                        ),
                        new ItemRecord(ItemId.FLETCHING_TABLE_FURNITURE, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 168)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BLAST_FURNACE_FURNITURE.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 15000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 65)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 64, true, false),
                                new IngredientRecord(ItemId.PURE_COAL_ORE, 64, true, false),

                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false),
                                new IngredientRecord(ItemId.FLINT_AND_STEEL, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BLAST_FURNACE_FURNITURE, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 227)), null), null),


                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ALTAR_FURNITURE.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 10000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 55)),
                        List.of(
                                new IngredientRecord(ItemId.MAGIC_LOG, 16, true, false),
                                new IngredientRecord(ItemId.MAGIC_ASHES, 2, true, false),
                                new IngredientRecord(ItemId.SOFT_CUB_FUR, 2, true, false),

                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false),
                                new IngredientRecord(ItemId.CHISEL, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ALTAR_FURNITURE, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 200)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.LANTERN_FURNITURE.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 40)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 8, true, false),
                                new IngredientRecord(ItemId.PURE_COAL_ORE, 2, true, false),

                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false),
                                new IngredientRecord(ItemId.FLINT_AND_STEEL, 1, false, false)
                        ),
                        new ItemRecord(ItemId.LANTERN_FURNITURE, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 140)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.SOUL_LANTERN_FURNITURE.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 43)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 8, true, false),
                                new IngredientRecord(ItemId.PURE_COAL_ORE, 2, true, false),
                                new IngredientRecord(ItemId.MAGIC_ASHES, 2, true, false),

                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false),
                                new IngredientRecord(ItemId.FLINT_AND_STEEL, 1, false, false)
                        ),
                        new ItemRecord(ItemId.SOUL_LANTERN_FURNITURE, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 150)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.LAMP_FURNITURE.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 42)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 8, true, false),
                                new IngredientRecord(ItemId.PURE_COAL_ORE, 2, true, false),

                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.REDSTONE_LAMP_FURNITURE, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 147)), null), null),


                new RecipeEntity(new AbstractRecipeMeta(RecipeId.TOOL_STORAGE_BAG.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 10000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 45)),
                        List.of(
                                new IngredientRecord(ItemId.NEEDLE, 1, false, false),
                                new IngredientRecord(ItemId.THREAD, 6, true, false),
                                new IngredientRecord(ItemId.LINEN_FABRIC, 18, true, false),
                                new IngredientRecord(ItemId.STEEL_BUCKLE, 2, true, false),
                                new IngredientRecord(ItemId.HARD_LEATHER, 4, true, false)
                        ),
                        new ItemRecord(ItemId.TOOL_BAG, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 120)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.REGULAR_FAIRY_RATION.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 50)),
                        List.of(
                                new IngredientRecord(ItemId.THREAD, 4, true, false),
                                new IngredientRecord(ItemId.LINEN_FABRIC, 3, true, false),
                                new IngredientRecord(ItemId.COOKED_PIKE, 3, true, false),
                                new IngredientRecord(ItemId.INSECT_WINGS, 4, true, false),
                                new IngredientRecord(ItemId.COCKROACH_LEG, 4, true, false)
                        ),
                        new ItemRecord(ItemId.REGULAR_FAIRY_RATION, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 175)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.SMALL_STORAGE_BAG.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 10000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 55)),
                        List.of(
                                new IngredientRecord(ItemId.NEEDLE, 1, false, false),
                                new IngredientRecord(ItemId.THREAD, 12, true, false),
                                new IngredientRecord(ItemId.LINEN_FABRIC, 20, true, false),
                                new IngredientRecord(ItemId.STEEL_BUCKLE, 2, true, false),
                                new IngredientRecord(ItemId.BALL_OF_WOOL, 4, true, false),
                                new IngredientRecord(ItemId.HARD_LEATHER, 4, true, false)
                        ),
                        new ItemRecord(ItemId.SMALL_ITEM_BAG, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 175)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ARMADILLO_HOOKBLADE.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 10000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 50)),
                        List.of(
                                new IngredientRecord(ItemId.ARMADILLO_SCALE, 10, true, false),
                                new IngredientRecord(ItemId.ARMADILLO_CLAW, 4, true, false),
                                new IngredientRecord(ItemId.SHARP_ARMADILLO_CLAW, 2, true, false)
                        ),
                        new ItemRecord(ItemId.ARMADILLO_HOOKBLADE, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 175)), null), null),


                // Tanner
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.LEATHER.getId(), RecipeType.TANNER_RECIPE, 2000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 1)),
                        List.of(new IngredientRecord(ItemId.COWHIDE, 1, true, false)),
                        new ItemRecord(ItemId.LEATHER, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 15)), 20L), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.HARD_LEATHER.getId(), RecipeType.TANNER_RECIPE, 2000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 20)),
                        List.of(new IngredientRecord(ItemId.THICK_HIDE, 1, true, false)),
                        new ItemRecord(ItemId.HARD_LEATHER, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 25)), 35L), null),

                // Crafting armor from leather
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COIF.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.NEEDLE, 1, false, false),
                                new IngredientRecord(ItemId.THREAD, 1, true, false),
                                new IngredientRecord(ItemId.LEATHER, 1, true, false)
                        ),
                        new ItemRecord(ItemId.COIF, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 15)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COWL.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 9)),
                        List.of(
                                new IngredientRecord(ItemId.NEEDLE, 1, false, false),
                                new IngredientRecord(ItemId.THREAD, 1, true, false),
                                new IngredientRecord(ItemId.LEATHER, 1, true, false)
                        ),
                        new ItemRecord(ItemId.COWL, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 20)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.LEATHER_BODY.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 14)),
                        List.of(
                                new IngredientRecord(ItemId.NEEDLE, 1, false, false),
                                new IngredientRecord(ItemId.THREAD, 3, true, false),
                                new IngredientRecord(ItemId.LEATHER, 3, true, false)
                        ),
                        new ItemRecord(ItemId.LEATHER_BODY, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 38.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.LEATHER_CHAPS.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 18)),
                        List.of(
                                new IngredientRecord(ItemId.NEEDLE, 1, false, false),
                                new IngredientRecord(ItemId.THREAD, 2, true, false),
                                new IngredientRecord(ItemId.LEATHER, 2, true, false)
                        ),
                        new ItemRecord(ItemId.LEATHER_LEGGINGS, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 45.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.LEATHER_BOOTS.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 7)),
                        List.of(
                                new IngredientRecord(ItemId.NEEDLE, 1, false, false),
                                new IngredientRecord(ItemId.THREAD, 1, true, false),
                                new IngredientRecord(ItemId.LEATHER, 1, true, false)
                        ),
                        new ItemRecord(ItemId.LEATHER_BOOTS, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 16.25)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.HARD_LEATHER_BODY.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 28)),
                        List.of(
                                new IngredientRecord(ItemId.NEEDLE, 1, false, false),
                                new IngredientRecord(ItemId.THREAD, 3, true, false),
                                new IngredientRecord(ItemId.HARD_LEATHER, 3, true, false)
                        ),
                        new ItemRecord(ItemId.HARD_LEATHER_BODY, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 70.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.HARD_LEATHER_CHAPS.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 28)),
                        List.of(
                                new IngredientRecord(ItemId.NEEDLE, 1, false, false),
                                new IngredientRecord(ItemId.THREAD, 2, true, false),
                                new IngredientRecord(ItemId.HARD_LEATHER, 2, true, false)
                        ),
                        new ItemRecord(ItemId.HARD_LEATHER_LEGGINGS, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 70.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STUDDED_LEATHER_BODY.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 44)),
                        List.of(
                                new IngredientRecord(ItemId.LEATHER_BODY, 1, true, false),
                                new IngredientRecord(ItemId.IRON_ARROW_TIPS, 10, false, false)
                        ),
                        new ItemRecord(ItemId.STUDDED_LEATHER_BODY, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 110.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STUDDED_LEATHER_CHAPS.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 41)),
                        List.of(
                                new IngredientRecord(ItemId.LEATHER_LEGGINGS, 1, true, false),
                                new IngredientRecord(ItemId.IRON_ARROW_TIPS, 8, false, false)
                        ),
                        new ItemRecord(ItemId.STUDDED_LEATHER_LEGGINGS, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 115.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.GREEN_DHIDE_BODY.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 63)),
                        List.of(
                                new IngredientRecord(ItemId.NEEDLE, 1, false, false),
                                new IngredientRecord(ItemId.THREAD, 3, true, false),
                                new IngredientRecord(ItemId.DRAGON_LEATHER, 3, true, false)
                        ),
                        new ItemRecord(ItemId.GREEN_DHIDE_BODY, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 190.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.GREEN_DHIDE_CHAPS.getId(), RecipeType.CRAFTING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 60)),
                        List.of(
                                new IngredientRecord(ItemId.NEEDLE, 1, false, false),
                                new IngredientRecord(ItemId.THREAD, 2, true, false),
                                new IngredientRecord(ItemId.DRAGON_LEATHER, 2, true, false)
                        ),
                        new ItemRecord(ItemId.GREEN_DHIDE_LEGGINGS, 1),
                        null, List.of(new SkillXpRecord(SkillType.CRAFTING, 180.0)), null), null),


                // Gems cutting (crafting)
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.SAPPHIRE.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 20)),
                        List.of(
                                new IngredientRecord(ItemId.UNCUT_SAPPHIRE, 1, true, false),
                                new IngredientRecord(ItemId.CHISEL, 1, false, false)
                        ),
                        new ItemRecord(ItemId.SAPPHIRE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.CRAFTING, 50.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.EMERALD.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 27)),
                        List.of(
                                new IngredientRecord(ItemId.UNCUT_EMERALD, 1, true, false),
                                new IngredientRecord(ItemId.CHISEL, 1, false, false)
                        ),
                        new ItemRecord(ItemId.EMERALD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.CRAFTING, 67.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUBY.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 34)),
                        List.of(
                                new IngredientRecord(ItemId.UNCUT_RUBY, 1, true, false),
                                new IngredientRecord(ItemId.CHISEL, 1, false, false)
                        ),
                        new ItemRecord(ItemId.RUBY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.CRAFTING, 85.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.DIAMOND.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 43)),
                        List.of(
                                new IngredientRecord(ItemId.UNCUT_DIAMOND, 1, true, false),
                                new IngredientRecord(ItemId.CHISEL, 1, false, false)
                        ),
                        new ItemRecord(ItemId.DIAMOND, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.CRAFTING, 107.5)), null), null),

                // Fletching
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.SHORTBOW.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 5)),
                        List.of(
                                new IngredientRecord(ItemId.SHORTBOW_UNFINISHED, 1, true, false),
                                new IngredientRecord(ItemId.BOW_STRING, 1, true, false)
                        ),
                        new ItemRecord(ItemId.SHORT_BOW, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 5.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.LONGBOW.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.LONGBOW_UNFINISHED, 1, true, false),
                                new IngredientRecord(ItemId.BOW_STRING, 1, true, false)
                        ),
                        new ItemRecord(ItemId.LONG_BOW, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 10.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BIRCH_SHORTBOW.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 20)),
                        List.of(
                                new IngredientRecord(ItemId.BIRCH_SHORTBOW_UNFINISHED, 1, true, false),
                                new IngredientRecord(ItemId.BOW_STRING, 1, true, false)
                        ),
                        new ItemRecord(ItemId.BIRCH_SHORT_BOW, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 16.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BIRCH_LONGBOW.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 25)),
                        List.of(
                                new IngredientRecord(ItemId.BIRCH_LONGBOW_UNFINISHED, 1, true, false),
                                new IngredientRecord(ItemId.BOW_STRING, 1, true, false)
                        ),
                        new ItemRecord(ItemId.BIRCH_LONG_BOW, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 25.0)), null), null),


                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAPLE_SHORTBOW.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 35)),
                        List.of(
                                new IngredientRecord(ItemId.MAPLE_SHORTBOW_UNFINISHED, 1, true, false),
                                new IngredientRecord(ItemId.BOW_STRING, 1, true, false)
                        ),
                        new ItemRecord(ItemId.MAPLE_SHORT_BOW, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 58.3)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAPLE_LONGBOW.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 40)),
                        List.of(
                                new IngredientRecord(ItemId.MAPLE_LONGBOW_UNFINISHED, 1, true, false),
                                new IngredientRecord(ItemId.BOW_STRING, 1, true, false)
                        ),
                        new ItemRecord(ItemId.MAPLE_LONG_BOW, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 58.3)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.YEW_SHORTBOW.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 55)),
                        List.of(
                                new IngredientRecord(ItemId.YEW_SHORTBOW_UNFINISHED, 1, true, false),
                                new IngredientRecord(ItemId.BOW_STRING, 1, true, false)
                        ),
                        new ItemRecord(ItemId.YEW_SHORT_BOW, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 67.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.YEW_LONGBOW.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 60)),
                        List.of(
                                new IngredientRecord(ItemId.YEW_LONGBOW_UNFINISHED, 1, true, false),
                                new IngredientRecord(ItemId.BOW_STRING, 1, true, false)
                        ),
                        new ItemRecord(ItemId.YEW_LONG_BOW, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 75.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAGIC_SHORTBOW.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 70)),
                        List.of(
                                new IngredientRecord(ItemId.MAGIC_SHORTBOW_UNFINISHED, 1, true, false),
                                new IngredientRecord(ItemId.MAGIC_BOW_STRING, 1, true, false)
                        ),
                        new ItemRecord(ItemId.MAGIC_SHORT_BOW, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 83.3)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAGIC_LONGBOW.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 75)),
                        List.of(
                                new IngredientRecord(ItemId.MAGIC_LONGBOW_UNFINISHED, 1, true, false),
                                new IngredientRecord(ItemId.MAGIC_BOW_STRING, 1, true, false)
                        ),
                        new ItemRecord(ItemId.MAGIC_LONG_BOW, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 91.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.SHORTBOW_UNFINISHED.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 5)),
                        List.of(
                                new IngredientRecord(ItemId.LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.SHORTBOW_UNFINISHED, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 5.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.LONGBOW_UNFINISHED.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.LONGBOW_UNFINISHED, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 10.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BIRCH_SHORTBOW_UNFINISHED.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 20)),
                        List.of(
                                new IngredientRecord(ItemId.BIRCH_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BIRCH_SHORTBOW_UNFINISHED, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 16.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BIRCH_LONGBOW_UNFINISHED.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 25)),
                        List.of(
                                new IngredientRecord(ItemId.BIRCH_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BIRCH_LONGBOW_UNFINISHED, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 25.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAPLE_SHORTBOW_UNFINISHED.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 35)),
                        List.of(
                                new IngredientRecord(ItemId.MAPLE_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MAPLE_SHORTBOW_UNFINISHED, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 41.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAPLE_LONGBOW_UNFINISHED.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 40)),
                        List.of(
                                new IngredientRecord(ItemId.MAPLE_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MAPLE_LONGBOW_UNFINISHED, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 58.3)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.YEW_SHORTBOW_UNFINISHED.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 55)),
                        List.of(
                                new IngredientRecord(ItemId.YEW_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.YEW_SHORTBOW_UNFINISHED, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 67.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.YEW_LONGBOW_UNFINISHED.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 60)),
                        List.of(
                                new IngredientRecord(ItemId.YEW_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.YEW_LONGBOW_UNFINISHED, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 75.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAGIC_SHORTBOW_UNFINISHED.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 70)),
                        List.of(
                                new IngredientRecord(ItemId.MAGIC_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MAGIC_SHORTBOW_UNFINISHED, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 83.3)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAGIC_LONGBOW_UNFINISHED.getId(), RecipeType.FLETCHING_TABLE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 75)),
                        List.of(
                                new IngredientRecord(ItemId.MAGIC_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MAGIC_LONGBOW_UNFINISHED, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 91.5)), null), null),


                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_ARROW.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_ARROW_TIPS, 15, true, false),
                                new IngredientRecord(ItemId.HEADLESS_ARROW, 15, true, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_ARROW, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 15.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_ARROW.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 15)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_ARROW_TIPS, 15, true, false),
                                new IngredientRecord(ItemId.HEADLESS_ARROW, 15, true, false)
                        ),
                        new ItemRecord(ItemId.IRON_ARROW, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 37.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_ARROW.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 30)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_ARROW_TIPS, 15, true, false),
                                new IngredientRecord(ItemId.HEADLESS_ARROW, 15, true, false)
                        ),
                        new ItemRecord(ItemId.STEEL_ARROW, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 75.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_ARROW.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 45)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_ARROW_TIPS, 15, true, false),
                                new IngredientRecord(ItemId.HEADLESS_ARROW, 15, true, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_ARROW, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 112.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_ARROW.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 60)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_ARROW_TIPS, 15, true, false),
                                new IngredientRecord(ItemId.HEADLESS_ARROW, 15, true, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_ARROW, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 150.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_ARROW.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 75)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_ARROW_TIPS, 15, true, false),
                                new IngredientRecord(ItemId.HEADLESS_ARROW, 15, true, false)
                        ),
                        new ItemRecord(ItemId.RUNE_ARROW, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 187.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.HEADLESS_ARROW.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.ARROW_SHAFT, 15, true, false),
                                new IngredientRecord(ItemId.FEATHER, 15, true, false)
                        ),
                        new ItemRecord(ItemId.HEADLESS_ARROW, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 15.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.LOG_ARROW_SHAFT.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ARROW_SHAFT, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 15.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BIRCH_ARROW_SHAFT.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 20)),
                        List.of(
                                new IngredientRecord(ItemId.BIRCH_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ARROW_SHAFT, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 25.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAPLE_ARROW_SHAFT.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 35)),
                        List.of(
                                new IngredientRecord(ItemId.MAPLE_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ARROW_SHAFT, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 35.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.YEW_ARROW_SHAFT.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 50)),
                        List.of(
                                new IngredientRecord(ItemId.YEW_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ARROW_SHAFT, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 45.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAHOGANY_ARROW_SHAFT.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 55)),
                        List.of(
                                new IngredientRecord(ItemId.MAHOGANY_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ARROW_SHAFT, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 50.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAGIC_ARROW_SHAFT.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FLETCHING, 75)),
                        List.of(
                                new IngredientRecord(ItemId.MAGIC_LOG, 1, true, false),
                                new IngredientRecord(ItemId.KNIFE, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ARROW_SHAFT, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.FLETCHING, 60.0)), null), null),


                // Spinning wheel
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BALL_OF_WOOL_RECIPE.getId(), RecipeType.SPINNING_WHEEL_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 1)),
                        List.of(new IngredientRecord(ItemId.WOOL, 1, true, true)),
                        new ItemRecord(ItemId.BALL_OF_WOOL, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.CRAFTING, 5)), null
                ), null),
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.THREAD.getId(), RecipeType.SPINNING_WHEEL_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 4)),
                        List.of(new IngredientRecord(ItemId.BALL_OF_WOOL, 1, true, true)),
                        new ItemRecord(ItemId.THREAD, 4),
                        null,
                        List.of(new SkillXpRecord(SkillType.CRAFTING, 8)), null
                ), null),
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BOW_STRING_RECIPE.getId(), RecipeType.SPINNING_WHEEL_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 8)),
                        List.of(new IngredientRecord(ItemId.FLAX, 1, true, true)),
                        new ItemRecord(ItemId.BOW_STRING, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.CRAFTING, 15)), null
                ), null),
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.FISHING_NET_RECIPE.getId(), RecipeType.SPINNING_WHEEL_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 15)),
                        List.of(new IngredientRecord(ItemId.FLAX, 3, true, true)),
                        new ItemRecord(ItemId.FISHING_NET, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.CRAFTING, 20)), null
                ), null),
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.LINEN_FABRIC.getId(), RecipeType.SPINNING_WHEEL_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 30)),
                        List.of(new IngredientRecord(ItemId.FLAX, 10, true, true)),
                        new ItemRecord(ItemId.LINEN_FABRIC, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.CRAFTING, 50)), null
                ), null),
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAGIC_BOW_STRING_RECIPE.getId(), RecipeType.SPINNING_WHEEL_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.CRAFTING, 40)),
                        List.of(new IngredientRecord(ItemId.BOW_STRING, 1, true, true),
                                new IngredientRecord(ItemId.MAGIC_ASHES, 3, true, true)),
                        new ItemRecord(ItemId.MAGIC_BOW_STRING, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.CRAFTING, 80)), null
                ), null),

                // Tools
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COMPASS.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 7)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 1, true, false),
                                new IngredientRecord(ItemId.LOG, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.COMPASS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 20)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.KNIFE.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 8)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.KNIFE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 20)), null
                ), null),

                // Campfires
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.LOG_CAMPFIRE.getId(), RecipeType.CAMPFIRE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FIREMAKING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.LOG, 1, true, false),
                                new IngredientRecord(ItemId.FLINT_AND_STEEL, 1, false, false)
                        ),
                        null,
                        null,
                        List.of(new SkillXpRecord(SkillType.FIREMAKING, 40)), null
                ), new VersionedJson(gson.toJson(
                        new CampfireRecipeMeta("Log", "Campfire", 180, false, new ItemRecord(ItemId.ASHES, 1))), 1)),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BIRCH_CAMPFIRE.getId(), RecipeType.CAMPFIRE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FIREMAKING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.BIRCH_LOG, 1, true, false),
                                new IngredientRecord(ItemId.FLINT_AND_STEEL, 1, false, false)
                        ),
                        null,
                        null,
                        List.of(new SkillXpRecord(SkillType.FIREMAKING, 50)), null
                ), new VersionedJson(gson.toJson(
                        new CampfireRecipeMeta("Birch Log", "Campfire", 120, false, new ItemRecord(ItemId.ASHES, 1))), 1)),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAPLE_CAMPFIRE.getId(), RecipeType.CAMPFIRE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FIREMAKING, 15)),
                        List.of(
                                new IngredientRecord(ItemId.MAPLE_LOG, 1, true, false),
                                new IngredientRecord(ItemId.FLINT_AND_STEEL, 1, false, false)
                        ),
                        null,
                        null,
                        List.of(new SkillXpRecord(SkillType.FIREMAKING, 60)), null
                ), new VersionedJson(gson.toJson(
                        new CampfireRecipeMeta("Maple Log", "Campfire", 240, false, new ItemRecord(ItemId.ASHES, 1))), 1)),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.YEW_CAMPFIRE.getId(), RecipeType.CAMPFIRE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FIREMAKING, 20)),
                        List.of(
                                new IngredientRecord(ItemId.YEW_LOG, 1, true, false),
                                new IngredientRecord(ItemId.FLINT_AND_STEEL, 1, false, false)
                        ),
                        null,
                        null,
                        List.of(new SkillXpRecord(SkillType.FIREMAKING, 90)), null
                ), new VersionedJson(gson.toJson(
                        new CampfireRecipeMeta("Yew Log", "Campfire", 300, false, new ItemRecord(ItemId.ASHES, 1))), 1)),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAHOGANY_CAMPFIRE.getId(), RecipeType.CAMPFIRE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FIREMAKING, 30)),
                        List.of(
                                new IngredientRecord(ItemId.MAHOGANY_LOG, 1, true, false),
                                new IngredientRecord(ItemId.FLINT_AND_STEEL, 1, false, false)
                        ),
                        null,
                        null,
                        List.of(new SkillXpRecord(SkillType.FIREMAKING, 180)), null
                ), new VersionedJson(gson.toJson(
                        new CampfireRecipeMeta("Mahogany Log", "Campfire", 300, false, new ItemRecord(ItemId.ASHES, 1))), 1)),


                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MAGIC_LOG_CAMPFIRE.getId(), RecipeType.CAMPFIRE_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.FIREMAKING, 50)),
                        List.of(
                                new IngredientRecord(ItemId.MAGIC_LOG, 1, true, false),
                                new IngredientRecord(ItemId.FLINT_AND_STEEL, 1, false, false)
                        ),
                        null,
                        null,
                        List.of(new SkillXpRecord(SkillType.FIREMAKING, 250)), null
                ), new VersionedJson(gson.toJson(
                        new CampfireRecipeMeta("Magic Logs", "Magic Campfire", 600, true, new ItemRecord(ItemId.MAGIC_ASHES, 1))), 1)),

                // Arrow Tips
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_ARROW_TIPS.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 5)),
                        List.of(new IngredientRecord(ItemId.BRONZE_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)),
                        new ItemRecord(ItemId.BRONZE_ARROW_TIPS, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 12.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_ARROW_TIPS.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 20)),
                        List.of(new IngredientRecord(ItemId.IRON_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)),
                        new ItemRecord(ItemId.IRON_ARROW_TIPS, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_ARROW_TIPS.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 35)),
                        List.of(new IngredientRecord(ItemId.STEEL_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)),
                        new ItemRecord(ItemId.STEEL_ARROW_TIPS, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_ARROW_TIPS.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 50)),
                        List.of(new IngredientRecord(ItemId.MITHRIL_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)),
                        new ItemRecord(ItemId.MITHRIL_ARROW_TIPS, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_ARROW_TIPS.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 70)),
                        List.of(new IngredientRecord(ItemId.ADAMANT_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)),
                        new ItemRecord(ItemId.ADAMANT_ARROW_TIPS, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62.5)), null), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_ARROW_TIPS.getId(), RecipeType.SMITHING_RECIPE, 500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 90)),
                        List.of(new IngredientRecord(ItemId.RUNE_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)),
                        new ItemRecord(ItemId.RUNE_ARROW_TIPS, 15),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0)), null), null),

                // Bars
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_BAR.getId(), RecipeType.SMELTING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.COPPER_ORE, 1, true, false),
                                new IngredientRecord(ItemId.TIN_ORE, 1, true, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 6.2)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_BAR.getId(), RecipeType.SMELTING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 15)),
                        List.of(new IngredientRecord(ItemId.IRON_ORE, 1, true, false)),
                        new ItemRecord(ItemId.IRON_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 12.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_BAR.getId(), RecipeType.SMELTING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 30)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_ORE, 1, true, false),
                                new IngredientRecord(ItemId.COAL_ORE, 2, true, false)
                        ),
                        new ItemRecord(ItemId.STEEL_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 17.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_BAR.getId(), RecipeType.SMELTING_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 45)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_ORE, 1, true, false),
                                new IngredientRecord(ItemId.PURE_COAL_ORE, 1, true, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 30.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_BAR.getId(), RecipeType.SMELTING_RECIPE, 10000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 65)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_ORE, 1, true, false),
                                new IngredientRecord(ItemId.PURE_COAL_ORE, 2, true, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_BAR.getId(), RecipeType.SMELTING_RECIPE, 15000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 85)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_ORE, 1, true, false),
                                new IngredientRecord(ItemId.PURE_COAL_ORE, 4, true, false)
                        ),
                        new ItemRecord(ItemId.RUNE_BAR, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0)), null
                ), null),

                // Food
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_CHICKEN.getId(), RecipeType.COOKING_RECIPE, 2200,
                        List.of(new SkillRequirement(SkillType.COOKING, 1)),
                        List.of(new IngredientRecord(ItemId.RAW_CHICKEN, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_CHICKEN, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 30)), null
                ), null),
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_MEAT.getId(), RecipeType.COOKING_RECIPE, 2200,
                        List.of(new SkillRequirement(SkillType.COOKING, 1)),
                        List.of(new IngredientRecord(ItemId.RAW_MEAT, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_MEAT, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 30)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_HERRING.getId(), RecipeType.COOKING_RECIPE, 2200,
                        List.of(new SkillRequirement(SkillType.COOKING, 1)),
                        List.of(new IngredientRecord(ItemId.RAW_HERRING, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_HERRING, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 30)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_MINNOW.getId(), RecipeType.COOKING_RECIPE, 2000,
                        List.of(new SkillRequirement(SkillType.COOKING, 1)),
                        List.of(new IngredientRecord(ItemId.RAW_MINNOW, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_MINNOW, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 30)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_TROUT.getId(), RecipeType.COOKING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.COOKING, 15)),
                        List.of(new IngredientRecord(ItemId.RAW_TROUT, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_TROUT, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 45)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_SALMON.getId(), RecipeType.COOKING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.COOKING, 25)),
                        List.of(new IngredientRecord(ItemId.RAW_SALMON, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_SALMON, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 50)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_TUNA.getId(), RecipeType.COOKING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.COOKING, 35)),
                        List.of(new IngredientRecord(ItemId.RAW_TUNA, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_TUNA, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 60)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_CRAWLER.getId(), RecipeType.COOKING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.COOKING, 45)),
                        List.of(new IngredientRecord(ItemId.RAW_CRAWLER, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_CRAWLER, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 70)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_PIKE.getId(), RecipeType.COOKING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.COOKING, 55)),
                        List.of(new IngredientRecord(ItemId.RAW_PIKE, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_PIKE, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 85)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_EEL.getId(), RecipeType.COOKING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.COOKING, 65)),
                        List.of(new IngredientRecord(ItemId.RAW_EEL, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_EEL, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 110)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_SHARK.getId(), RecipeType.COOKING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.COOKING, 75)),
                        List.of(new IngredientRecord(ItemId.RAW_SHARK, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_SHARK, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 130)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_TURTLE.getId(), RecipeType.COOKING_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.COOKING, 85)),
                        List.of(new IngredientRecord(ItemId.RAW_TURTLE, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_TURTLE, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 150)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.COOKED_RAY.getId(), RecipeType.COOKING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.COOKING, 95)),
                        List.of(new IngredientRecord(ItemId.RAW_RAY, 1, true, true)),
                        new ItemRecord(ItemId.COOKED_RAY, 1),
                        new ItemRecord(ItemId.BURNT_FOOD, 1),
                        List.of(new SkillXpRecord(SkillType.COOKING, 200)), null
                ), null),


                // Smithing materials
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_BUCKLE.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 30)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STEEL_BUCKLE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 18.5)), null
                ), null),

                // Helmets
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_MEDHELM.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 3)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_MEDHELM, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 12.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_MEDHELM.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 18)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.IRON_MEDHELM, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_MEDHELM.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 33)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STEEL_MEDHELM, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_MEDHELM.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 53)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_MEDHELM, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_MEDHELM.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 73)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_MEDHELM, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_MEDHELM.getId(), RecipeType.SMITHING_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 88)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.RUNE_MEDHELM, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_FULLHELM.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 7)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_FULLHELM, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_FULLHELM.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 22)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.IRON_FULLHELM, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_FULLHELM.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 37)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STEEL_FULLHELM, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_FULLHELM.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 57)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_FULLHELM, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 100.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_FULLHELM.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 77)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_FULLHELM, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 125)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_FULLHELM.getId(), RecipeType.SMITHING_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 92)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.RUNE_FULLHELM, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 150)), null
                ), null),


                // Platebody
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_BODY.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 15)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 5, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_BODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_BODY.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 23)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 5, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.IRON_BODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 100)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_BODY.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 35)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 5, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STEEL_BODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 180)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_BODY.getId(), RecipeType.SMITHING_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 48)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 5, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_BODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 200)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_BODY.getId(), RecipeType.SMITHING_RECIPE, 9000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 65)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_BAR, 5, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_BODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 260.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_BODY.getId(), RecipeType.SMITHING_RECIPE, 12000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 99)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_BAR, 5, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.RUNE_BODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 300)), null
                ), null),

                // Chainbody
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_CHAINBODY.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_CHAINBODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_CHAINBODY.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 22)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.IRON_CHAINBODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 100)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_CHAINBODY.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 33)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STEEL_CHAINBODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 180)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_CHAINBODY.getId(), RecipeType.SMITHING_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 46)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_CHAINBODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 200)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_CHAINBODY.getId(), RecipeType.SMITHING_RECIPE, 9000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 60)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_CHAINBODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 260.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_CHAINBODY.getId(), RecipeType.SMITHING_RECIPE, 12000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 90)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.RUNE_CHAINBODY, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 300)), null
                ), null),


                // Platelegs

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_PLATELEGS.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 10)),


                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_PLATELEGS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 35)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_PLATELEGS.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 25)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.IRON_PLATELEGS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_PLATELEGS.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 34)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STEEL_PLATELEGS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 112.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_PLATELEGS.getId(), RecipeType.SMITHING_RECIPE, 8000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 47)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_PLATELEGS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 150)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_PLATELEGS.getId(), RecipeType.SMITHING_RECIPE, 9000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 64)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_PLATELEGS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 170.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_PLATELEGS.getId(), RecipeType.SMITHING_RECIPE, 12000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 99)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.RUNE_PLATELEGS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 225)), null
                ), null),

                // Boots

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_BOOTS.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 7)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_BOOTS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 13.8)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_BOOTS.getId(), RecipeType.SMITHING_RECIPE, 3500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 21)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.IRON_BOOTS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_BOOTS.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 32)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STEEL_BOOTS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_BOOTS.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 44)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_BOOTS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_BOOTS.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 62)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_BOOTS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 100.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_BOOTS.getId(), RecipeType.SMITHING_RECIPE, 9000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 78)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.RUNE_BOOTS, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 125.0)), null
                ), null),

                // Swords
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_SWORD.getId(), RecipeType.SMITHING_RECIPE, 2500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 4)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_SWORD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 12.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_SWORD.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.IRON_SWORD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_SWORD.getId(), RecipeType.SMITHING_RECIPE, 3500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 22)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STEEL_SWORD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_SWORD.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 30)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_SWORD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_SWORD.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 50)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_SWORD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_SWORD.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 70)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.RUNE_SWORD, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0)), null
                ), null),

// Daggers
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_DAGGER.getId(), RecipeType.SMITHING_RECIPE, 2500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_DAGGER, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 12.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_DAGGER.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.IRON_DAGGER, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_DAGGER.getId(), RecipeType.SMITHING_RECIPE, 3500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 22)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STEEL_DAGGER, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_DAGGER.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 30)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_DAGGER, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_DAGGER.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 50)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_DAGGER, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_DAGGER.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 70)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.RUNE_DAGGER, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0)), null
                ), null),

                // Battleaxes
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_BATTLEAXE.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_BATTLEAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_BATTLEAXE.getId(), RecipeType.SMITHING_RECIPE, 3500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 22)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.IRON_BATTLEAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_BATTLEAXE.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 33)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STEEL_BATTLEAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 112.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_BATTLEAXE.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 46)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_BATTLEAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 150.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_BATTLEAXE.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 61)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_BATTLEAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 185.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_BATTLEAXE.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 75)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.RUNE_BATTLEAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 225.0)), null
                ), null),

                // Axes
                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_AXE.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_AXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 13.8)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_AXE.getId(), RecipeType.SMITHING_RECIPE, 3500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.IRON_AXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_AXE.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 22)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STEEL_AXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_AXE.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 33)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_AXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_AXE.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 46)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_AXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_AXE.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 61)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.RUNE_AXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0)), null
                ), null),


                new RecipeEntity(new AbstractRecipeMeta(RecipeId.BRONZE_PICKAXE.getId(), RecipeType.SMITHING_RECIPE, 3000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 1)),
                        List.of(
                                new IngredientRecord(ItemId.BRONZE_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.BRONZE_PICKAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 13.8)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.IRON_PICKAXE.getId(), RecipeType.SMITHING_RECIPE, 3500,
                        List.of(new SkillRequirement(SkillType.SMITHING, 10)),
                        List.of(
                                new IngredientRecord(ItemId.IRON_BAR, 1, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.IRON_PICKAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 25.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.STEEL_PICKAXE.getId(), RecipeType.SMITHING_RECIPE, 4000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 22)),
                        List.of(
                                new IngredientRecord(ItemId.STEEL_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.STEEL_PICKAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 37.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.MITHRIL_PICKAXE.getId(), RecipeType.SMITHING_RECIPE, 5000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 33)),
                        List.of(
                                new IngredientRecord(ItemId.MITHRIL_BAR, 2, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.MITHRIL_PICKAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 50.0)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.ADAMANT_PICKAXE.getId(), RecipeType.SMITHING_RECIPE, 6000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 46)),
                        List.of(
                                new IngredientRecord(ItemId.ADAMANT_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.ADAMANT_PICKAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 62.5)), null
                ), null),

                new RecipeEntity(new AbstractRecipeMeta(RecipeId.RUNE_PICKAXE.getId(), RecipeType.SMITHING_RECIPE, 7000,
                        List.of(new SkillRequirement(SkillType.SMITHING, 61)),
                        List.of(
                                new IngredientRecord(ItemId.RUNE_BAR, 3, true, false),
                                new IngredientRecord(ItemId.SMITHING_HAMMER, 1, false, false)
                        ),
                        new ItemRecord(ItemId.RUNE_PICKAXE, 1),
                        null,
                        List.of(new SkillXpRecord(SkillType.SMITHING, 75.0)), null
                ), null)
        );
    }
}
