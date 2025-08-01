package org.zuma.examplePlugin.domain.droptable.persistence;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.droptable.DropTableEntry;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.droptable.DropTableRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;

import java.util.List;

public class DropTableRepository {
    private static final Gson gson = new Gson();

    public List<DropTableDBEntry> getAll() {
        return List.of(

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
                ))), 1)),

                // General
                new DropTableDBEntry(DropTableId.GEM_DROP_TABLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.UNCUT_SAPPHIRE.asInt(), 1, 1, 4, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.UNCUT_EMERALD.asInt(), 1, 1, 8, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.UNCUT_RUBY.asInt(), 1, 1, 16, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.UNCUT_DIAMOND.asInt(), 1, 1, 64, DropTableEntry.Type.ITEM)
                ))), 1)),

                // Nodes
                new DropTableDBEntry(DropTableId.FLAX_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.FLAX.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),


                // Woodcutting
                new DropTableDBEntry(DropTableId.WOODCUTTING_RARE_DROPTABLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 10, 10, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 50, 50, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.LOG.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.BIRCH_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.BIRCH_LOG.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.MAPLE_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MAPLE_LOG.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.MAHOGANY_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MAHOGANY_LOG.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.YEW_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.YEW_LOG.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.MAGIC_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MAGIC_LOG.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.TIN_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.TIN_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                // Mining
                new DropTableDBEntry(DropTableId.MINING_RARE_DROPTABLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 10, 10, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 50, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(DropTableId.GEM_DROP_TABLE.asInt(), 1, 1, 50, DropTableEntry.Type.NESTED_DROP_TABLE)
                ))), 1)),
                new DropTableDBEntry(DropTableId.COPPER_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COPPER_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.IRON_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.IRON_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.COAL_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COAL_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.MITHRIL_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.MITHRIL_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.ADAMANTITE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.ADAMANTITE_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.RUNITE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RUNITE_ORE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),


                // Fishing
                new DropTableDBEntry(DropTableId.FISHING_RARE_DROPTABLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 10, 10, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 50, 50, DropTableEntry.Type.ITEM),
                        new DropTableEntry(ItemId.SCRATCH_CARD.asInt(), 1, 1, 150, DropTableEntry.Type.ITEM)
                        ))), 1)),
                new DropTableDBEntry(DropTableId.HERRING_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_HERRING.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.MINNOW_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_MINNOW.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.TROUT_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_TROUT.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.SALMON_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_SALMON.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.TUNA_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_TUNA.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.CRAWLER_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_CRAWLER.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.PIKE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_PIKE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.EEL_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_EEL.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.SHARK_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_SHARK.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.TURTLE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_TURTLE.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.RAY_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_RAY.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),


                // Mobs

                new DropTableDBEntry(DropTableId.BONES.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.BONES.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.COINS_SMALL.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.COINS.asInt(), 1, 8, 3, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.WOOL.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.WOOL.asInt(), 1, 1, 4, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.CHICKEN_MEAT.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.RAW_CHICKEN.asInt(), 1, 1, 1, DropTableEntry.Type.ITEM)
                ))), 1)),

                new DropTableDBEntry(DropTableId.FEATHERS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropTableEntry(ItemId.FEATHER.asInt(), 1, 15, 2, DropTableEntry.Type.ITEM)
                ))), 1))

        );
    }

    ;
}
