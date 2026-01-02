package org.zuma.examplePlugin.domain.droptable.persistence;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.droptable.DropTableEntry;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.droptable.DropTableRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.quest.constants.QuestId;

import java.util.List;

public class DropTableRepository {
    private static final Gson gson = new Gson();

    public List<DropTableDBEntry> getAll() {
        return List.of(

                // Daily Rewards
                new DropTableDBEntry(DropTableId.DAILY_REWARD_FIRST_DAY.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 500, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.DAILY_REWARD_DAY_FILLER.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.IRON_BAR.asInt(), 4, 8, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_BAR.asInt(), 4, 8, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_BAR.asInt(), 3, 8, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RUNE_BAR.asInt(), 2, 8, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COOKED_TUNA.asInt(), 1, 32, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ATTACK_POTION_I.asInt(), 1, 3, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STRENGTH_POTION_I.asInt(), 1, 3, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.DEFENCE_POTION_I.asInt(), 1, 3, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RANGED_POTION_I.asInt(), 1, 3, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.YEW_LOG.asInt(), 1, 32, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MAPLE_LOG.asInt(), 1, 32, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MAHOGANY_LOG.asInt(), 1, 32, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MAGIC_LOG.asInt(), 1, 16, 30, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.REGULAR_FAIRY_RATION.asInt(), 1, 16, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MAPLE_SHORT_BOW.asInt(), 1, 1, 10, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_CROSSBOW.asInt(), 1, 1, 10, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_CROSSBOW.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.BIG_BONES.asInt(), 1, 32, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.LARGE_BONES.asInt(), 1, 32, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.GENIE_XP_LAMP.asInt(), 1, 4, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.SAPPHIRE.asInt(), 1, 4, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RUBY.asInt(), 1, 4, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.EMERALD.asInt(), 1, 4, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.DIAMOND.asInt(), 1, 4, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.SCRATCH_CARD.asInt(), 1, 3, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_RING.asInt(), 1, 2, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.GRAY_MULE_MOUNT.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.AMULET_OF_THE_PROTECTOR.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.AMULET_OF_MIGHT.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.AMULET_OF_PRECISION.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FUSION_SCROLL.asInt(), 1, 1, 80, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.STEEL_MEDHELM.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_FULLHELM.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_CHAINBODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_BODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_BOOTS.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_DAGGER.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_BATTLEAXE.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_SWORD.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.COWL.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STUDDED_LEATHER_BODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STUDDED_LEATHER_LEGGINGS.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.HARD_LEATHER_LEGGINGS.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.HARD_LEATHER_BODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.COINS.asInt(), 1, 500, 5, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.DAILY_REWARD_GRAND_PRIZE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.STEEL_BAR.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_BAR.asInt(), 16, 32, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RUNE_BAR.asInt(), 8, 16, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COOKED_SHARK.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COOKED_EEL.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ATTACK_POTION_II.asInt(), 1, 5, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STRENGTH_POTION_II.asInt(), 1, 5, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.DEFENCE_POTION_II.asInt(), 1, 5, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RANGED_POTION_II.asInt(), 1, 5, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.YEW_LOG.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MAPLE_LOG.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MAHOGANY_LOG.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MAGIC_LOG.asInt(), 16, 32, 30, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.REGULAR_FAIRY_RATION.asInt(), 16, 32, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FINE_FAIRY_RATION.asInt(), 8, 16, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MAGIC_SHORT_BOW.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_CROSSBOW.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_CROSSBOW.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.BIG_BONES.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.LARGE_BONES.asInt(), 16, 64, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.GENIE_XP_LAMP.asInt(), 3, 6, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.SCRATCH_CARD.asInt(), 2, 6, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_RING.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RUNE_RING.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.GRAY_MULE_MOUNT.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.MITHRIL_MEDHELM.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_FULLHELM.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_CHAINBODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_BODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_BOOTS.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_DAGGER.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_BATTLEAXE.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_SWORD.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.ADAMANT_MEDHELM.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_FULLHELM.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_CHAINBODY.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_BODY.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_BOOTS.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_DAGGER.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_BATTLEAXE.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_SWORD.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.RUNE_MEDHELM.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RUNE_FULLHELM.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RUNE_CHAINBODY.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RUNE_BODY.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RUNE_BOOTS.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RUNE_DAGGER.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RUNE_BATTLEAXE.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RUNE_SWORD.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.AMULET_OF_FORTITUDE.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.AMULET_OF_THE_DUELIST.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.AMULET_OF_THE_HUNTER.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.CREAMY_STEED_MOUNT.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.SNOW_WHITE_HORSE_MOUNT.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.BLACK_STALLION_MOUNT.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.DARK_BROWN_WARHORSE_MOUNT.asInt(), 1, 1, 250, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.FAIRY_OF_HOPE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FAIRY_OF_LIGHT.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FAIRY_OF_SILENCE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FAIRY_OF_DARKNESS.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FAIRY_OF_FROST.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.FAIRY_ATTACK_UPGRADE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FAIRY_DEFENCE_UPGRADE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FAIRY_STRENGTH_UPGRADE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FAIRY_HITPOINTS_UPGRADE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FAIRY_RANGE_UPGRADE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.BRUTALITE_CORE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.OBSIDIAN_BULWARK.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.BLADESTONE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ARCHERS_EMBER.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.GREEN_DHIDE_BODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.GREEN_DHIDE_LEGGINGS.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.COINS.asInt(), 1, 5000, 5, DropTableEntry.Type.ITEM)
                ), null)), 1)),


                // Quest Drops
                new DropTableDBEntry(DropTableId.TUNDRA_WOLF_THE_SICK_CAMELS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.WOLF_FANG_THE_SICK_CAMELS_QUEST.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), QuestId.THE_SICK_CAMELS)), 1)),

                new DropTableDBEntry(DropTableId.SWAMP_FROG_THE_SICK_CAMELS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.FROG_EYE_THE_SICK_CAMELS_QUEST.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), QuestId.THE_SICK_CAMELS)), 1)),


                // Chance items
                new DropTableDBEntry(DropTableId.SCRATCH_CARD_COINS_UNDER_2K_COINS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 2000, 2000, 4, DropTableEntry.Type.ITEM),  // 1/4 = 25%
                        new DropTableEntry(ItemId.COINS.asInt(), 1500, 1500, 5, DropTableEntry.Type.ITEM),  // (3/4)*(1/5) = 15%

                        new DropTableEntry(ItemId.COINS.asInt(), 5, 5, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 50, 50, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 100, 100, 10, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 300, 300, 7, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 600, 600, 7, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 800, 800, 10, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 1000, 1000, 7, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                // General
                new DropTableDBEntry(DropTableId.GEM_DROP_TABLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.UNCUT_SAPPHIRE.asInt(), 1, 1, 4, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.UNCUT_EMERALD.asInt(), 1, 1, 8, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.UNCUT_RUBY.asInt(), 1, 1, 16, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.UNCUT_DIAMOND.asInt(), 1, 1, 64, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                // Nodes
                new DropTableDBEntry(DropTableId.FLAX_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.FLAX.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.CARROT_CROP_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.CARROT.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.POTATO_CROP_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.POTATO.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),


                // Woodcutting
                new DropTableDBEntry(DropTableId.WOODCUTTING_RARE_DROPTABLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 10, 10, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 50, 50, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.LOG.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.BIRCH_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.BIRCH_LOG.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.MAPLE_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MAPLE_LOG.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.MAHOGANY_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MAHOGANY_LOG.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.YEW_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.YEW_LOG.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.MAGIC_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MAGIC_LOG.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.TIN_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.TIN_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                // Mining
                new DropTableDBEntry(DropTableId.MINING_RARE_DROPTABLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 10, 10, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 50, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(DropTableId.GEM_DROP_TABLE.asInt(), 1, 1, 50, DropTableEntry.Type.NESTED_DROP_TABLE)
                ), null)), 1)),
                new DropTableDBEntry(DropTableId.COPPER_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COPPER_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.IRON_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.IRON_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.COAL_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COAL_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.MITHRIL_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MITHRIL_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.ADAMANT_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.ADAMANT_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.RUNE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RUNE_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),


                // Fishing
                new DropTableDBEntry(DropTableId.FISHING_RARE_DROPTABLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 10, 10, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 50, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.SCRATCH_CARD.asInt(), 1, 1, 150, DropTableEntry.Type.ITEM)
                ), null)), 1)),
                new DropTableDBEntry(DropTableId.HERRING_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_HERRING.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.MINNOW_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_MINNOW.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.TROUT_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_TROUT.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.SALMON_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_SALMON.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.TUNA_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_TUNA.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.CRAWLER_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_CRAWLER.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.PIKE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_PIKE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.EEL_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_EEL.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.SHARK_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_SHARK.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.TURTLE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_TURTLE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.RAY_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_RAY.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),


                // Mobs

                new DropTableDBEntry(DropTableId.BONES.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.BONES.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.BIG_BONES.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.BIG_BONES.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.COINS_SMALL.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 8, 3, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.COINS_MEDIUM.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 10, 150, 3, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.COINS_MEDIUM_GUARANTEED.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 10, 150, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.WOOL.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.WOOL.asInt(), 1, 1, 4, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.CHICKEN_MEAT.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_CHICKEN.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.RAW_ANIMAL_MEAT.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_MEAT.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.FEATHERS_1_TO_15.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.FEATHER.asInt(), 1, 15, 2, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.FEATHERS_3_TO_8.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.FEATHER.asInt(), 3, 8, 2, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.COWHIDE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COWHIDE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),


                new DropTableDBEntry(DropTableId.WOLF_LOW_LVL_ARMOR.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.IRON_MEDHELM.asInt(), 1, 1, 30, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.IRON_BOOTS.asInt(), 1, 1, 30, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_MEDHELM.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.HARD_LEATHER_LEGGINGS.asInt(), 1, 1, 70, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.LEATHER_LEGGINGS.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.WOLF_COINS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 5, 5, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 10, 50, 10, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 10, 100, 20, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.GOBLIN_LOW_LVL_ARMOR.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.IRON_BODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.BRONZE_BOOTS.asInt(), 1, 1, 10, DropTableEntry.Type.ITEM)
                ), null)), 1)),


                new DropTableDBEntry(DropTableId.DUNGEON_TURRET.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.IRON_BAR.asInt(), 1, 2, 10, DropTableEntry.Type.ITEM),
                        new DropTableEntry(DropTableId.COINS_SMALL.asInt(), 1, 1, 3, DropTableEntry.Type.NESTED_DROP_TABLE),
                        new DropTableEntry(DropTableId.GEM_DROP_TABLE.asInt(), 1, 1, 50, DropTableEntry.Type.NESTED_DROP_TABLE)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.LAVA_DUNGEON_KEY_A.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.LAVA_DUNGEON_KEY_A.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),
                new DropTableDBEntry(DropTableId.LAVA_DUNGEON_KEY_B.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.LAVA_DUNGEON_KEY_B.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),
                new DropTableDBEntry(DropTableId.LAVA_DUNGEON_KEY_C.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.LAVA_DUNGEON_KEY_C.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),
                new DropTableDBEntry(DropTableId.LAVA_DUNGEON_KEY_D.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.LAVA_DUNGEON_KEY_D.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),


                new DropTableDBEntry(DropTableId.LAVA_DUNGEON_BOSS_BONES.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.BIG_BONES.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.LAVA_DUNGEON_BOSS_COINS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 100, 250, 1, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 250, 1000, 30, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.LAVA_DUNGEON_RINGS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MITHRIL_RING.asInt(), 1, 1, 70, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_RING.asInt(), 1, 1, 150, DropTableEntry.Type.ITEM)
                ), null)), 1)),


                new DropTableDBEntry(DropTableId.LAVA_DUNGEON_BOSS_COMMON.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.REGULAR_FAIRY_RATION.asInt(), 1, 16, 32, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COAL_ORE.asInt(), 1, 12, 4, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_ORE.asInt(), 1, 4, 6, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_ORE.asInt(), 1, 4, 6, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ATTACK_POTION_I.asInt(), 1, 3, 8, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.DEFENCE_POTION_I.asInt(), 1, 3, 8, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.HEALTH_POTION_I.asInt(), 1, 3, 8, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RANGED_POTION_I.asInt(), 1, 3, 8, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.LAVA_DUNGEON_BOSS_RARE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MITHRIL_CROSSBOW.asInt(), 1, 1, 28, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_CROSSBOW.asInt(), 1, 1, 32, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FUSION_SCROLL.asInt(), 1, 1, 64, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.PLATINUM_TOKEN.asInt(), 1, 5, 64, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.DRAGON_LEATHER.asInt(), 4, 8, 40, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.GREEN_DHIDE_BODY.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.GREEN_DHIDE_LEGGINGS.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.AMULET_OF_THE_WARD.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.AMULET_OF_THE_SHARPSHOOTER.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.AMULET_OF_THE_GLADIATOR.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MEDIUM_ITEM_BAG.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FAIRY_OF_DARKNESS.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.BLADESTONE.asInt(), 1, 1, 120, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.SPIDER_DUNGEON_BOSS_COINS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 100, 250, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.SPIDER_DUNGEON_BOSS_COMMON.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.REGULAR_FAIRY_RATION.asInt(), 1, 16, 32, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.SPIDER_SILK.asInt(), 1, 12, 1, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.SPIDER_DUNGEON_BOSS_RARE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MITHRIL_CROSSBOW.asInt(), 1, 1, 16, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_CROSSBOW.asInt(), 1, 1, 32, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FUSION_SCROLL.asInt(), 1, 1, 64, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.PLATINUM_TOKEN.asInt(), 1, 2, 64, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.AMULET_OF_THE_HUNTER.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.AMULET_OF_FORTITUDE.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.AMULET_OF_THE_DUELIST.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.SMALL_ITEM_BAG.asInt(), 1, 1, 70, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.FAIRY_OF_SILENCE.asInt(), 1, 1, 80, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.OBSIDIAN_BULWARK.asInt(), 1, 1, 120, DropTableEntry.Type.ITEM)
                ), null)), 1)),


                new DropTableDBEntry(DropTableId.BANDIT_LOW_LEVEL_ARMOR.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.BRONZE_MEDHELM.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.IRON_MEDHELM.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_MEDHELM.asInt(), 1, 1, 70, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.BRONZE_CHAINBODY.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.IRON_CHAINBODY.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_CHAINBODY.asInt(), 1, 1, 70, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.BANDIT_LOW_LEVEL_WEAPONS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.BRONZE_SWORD.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.IRON_SWORD.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_SWORD.asInt(), 1, 1, 70, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.BRONZE_DAGGER.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.IRON_DAGGER.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.BANDIT_MEDIUM_LEVEL_ARMOR.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.STEEL_MEDHELM.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_MEDHELM.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_MEDHELM.asInt(), 1, 1, 150, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.STEEL_CHAINBODY.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_CHAINBODY.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_CHAINBODY.asInt(), 1, 1, 150, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.STEEL_BODY.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_BODY.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_BODY.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.BANDIT_MEDIUM_LEVEL_WEAPONS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.STEEL_SWORD.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_SWORD.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.STEEL_DAGGER.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_DAGGER.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.BANDIT_HIGH_LEVEL_ARMOR.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.ADAMANT_MEDHELM.asInt(), 1, 1, 90, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_MEDHELM.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.ADAMANT_CHAINBODY.asInt(), 1, 1, 90, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_CHAINBODY.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.ADAMANT_BODY.asInt(), 1, 1, 90, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_BODY.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.BANDIT_HIGH_LEVEL_WEAPONS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MITHRIL_SWORD.asInt(), 1, 1, 70, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ADAMANT_SWORD.asInt(), 1, 1, 150, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.RUNE_SWORD.asInt(), 1, 1, 500, DropTableEntry.Type.ITEM),

                        new DropTableEntry(ItemId.ADAMANT_DAGGER.asInt(), 1, 1, 70, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.MITHRIL_DAGGER.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                // New
                new DropTableDBEntry(DropTableId.ARMADILLO_LOW_LEVEL.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.ARMADILLO_SCALE.asInt(), 1, 1, 8, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ARMADILLO_CLAW.asInt(), 1, 1, 15, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.ARMADILLO_HIGH_LEVEL.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.ARMADILLO_SCALE.asInt(), 1, 1, 6, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ARMADILLO_CLAW.asInt(), 1, 1, 15, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.SHARP_ARMADILLO_CLAW.asInt(), 1, 1, 70, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.BOAR.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.BOAR_TUSK.asInt(), 1, 1, 8, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.THICK_HIDE.asInt(), 1, 1, 2, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.HOGLIN.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.THICK_HIDE.asInt(), 1, 1, 3, DropTableEntry.Type.ITEM)
                ), null)), 1)),


                // Shared bug materials
                new DropTableDBEntry(DropTableId.INSECT_WINGS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.INSECT_WINGS.asInt(), 1, 2, 4, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.COCKROACH.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COCKROACH_LEG.asInt(), 1, 1, 4, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.SURVIVAL_GLAND.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.ANT.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.ANT_EGGS.asInt(), 1, 3, 6, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.ANT_MANDIBLES.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.CHITIN_PLATES.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.CHITIN_PLATES.asInt(), 1, 2, 5, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.SPIDER_SILK.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.SPIDER_SILK.asInt(), 1, 2, 5, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.SCORPION.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.SCORPION_STINGER.asInt(), 1, 1, 8, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.CHITIN_PLATES.asInt(), 1, 2, 5, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.VENOM_SAC.asInt(), 1, 1, 80, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.BUNNY.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.SOFT_FUR.asInt(), 1, 1, 4, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.WARHOUND.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.STEEL_BAR.asInt(), 1, 2, 4, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COAL_ORE.asInt(), 1, 2, 4, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.DEER_ANTLER.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.DEER_ANTLER.asInt(), 1, 1, 6, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.EAGLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.EAGLE_CLAW.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.LEOPARD.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.PREDATOR_CLAW.asInt(), 1, 1, 12, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.LEOPARD_BABY.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.SOFT_CUB_FUR.asInt(), 1, 1, 3, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.MOLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.UNDERGROUND_FUNGUS.asInt(), 1, 1, 16, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.OCELOT.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.PREDATOR_CLAW.asInt(), 1, 1, 8, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.RAT.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MOLDY_CHEESE.asInt(), 1, 1, 16, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.FROG.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.SLIMY_SAC.asInt(), 1, 1, 8, DropTableEntry.Type.ITEM)
                ), null)), 1)),

                new DropTableDBEntry(DropTableId.COAL_PURIFICATION_INPUT_4.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.PURE_COAL_ORE.asInt(), 1, 2, 2, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COAL_ORE.asInt(), 1, 2, 2, DropTableEntry.Type.ITEM)
                ), null)), 1))
        );
    }

}
