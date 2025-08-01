package org.zuma.examplePlugin.domain.droptable.persistence;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.droptable.DropItemEntry;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.droptable.DropTableRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;

import java.util.List;

public class DropTableRepository {
    private static final Gson gson = new Gson();

    public List<DropTableDBEntry> getAll() {
        return List.of(

                // Nodes
                new DropTableDBEntry(DropTableId.FLAX_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.FLAX.asInt(), 1, 1, 1)
                ))), 1)),


                // Woodcutting
                new DropTableDBEntry(DropTableId.WOODCUTTING_RARE_DROPTABLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.COINS.asInt(), 1, 10, 10),
                        new DropItemEntry(ItemId.COINS.asInt(), 1, 50, 50)
                ))), 1)),

                new DropTableDBEntry(DropTableId.TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.LOG.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.BIRCH_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.BIRCH_LOG.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.MAPLE_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.MAPLE_LOG.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.MAHOGANY_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.MAHOGANY_LOG.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.YEW_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.YEW_LOG.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.MAGIC_TREE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.MAGIC_LOG.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.TIN_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.TIN_ORE.asInt(), 1, 1, 1)
                ))), 1)),

                // Mining
                new DropTableDBEntry(DropTableId.MINING_RARE_DROPTABLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.COINS.asInt(), 1, 10, 10),
                        new DropItemEntry(ItemId.COINS.asInt(), 1, 50, 50)
                ))), 1)),
                new DropTableDBEntry(DropTableId.COPPER_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.COPPER_ORE.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.IRON_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.IRON_ORE.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.COAL_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.COAL_ORE.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.MITHRIL_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.MITHRIL_ORE.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.ADAMANTITE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.ADAMANTITE_ORE.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.RUNITE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RUNITE_ORE.asInt(), 1, 1, 1)
                ))), 1)),


                // Fishing
                new DropTableDBEntry(DropTableId.FISHING_RARE_DROPTABLE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.COINS.asInt(), 1, 10, 10),
                        new DropItemEntry(ItemId.COINS.asInt(), 1, 50, 50)
                ))), 1)),
                new DropTableDBEntry(DropTableId.HERRING_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RAW_HERRING.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.MINNOW_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RAW_MINNOW.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.TROUT_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RAW_TROUT.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.SALMON_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RAW_SALMON.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.TUNA_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RAW_TUNA.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.CRAWLER_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RAW_CRAWLER.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.PIKE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RAW_PIKE.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.EEL_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RAW_EEL.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.SHARK_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RAW_SHARK.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.TURTLE_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RAW_TURTLE.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.RAY_NODE.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RAW_RAY.asInt(), 1, 1, 1)
                ))), 1)),


                // Mobs

                new DropTableDBEntry(DropTableId.BONES.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.BONES.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.COINS_SMALL.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.COINS.asInt(), 1, 8, 3)
                ))), 1)),

                new DropTableDBEntry(DropTableId.WOOL.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.WOOL.asInt(), 1, 1, 4)
                ))), 1)),

                new DropTableDBEntry(DropTableId.CHICKEN_MEAT.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.RAW_CHICKEN.asInt(), 1, 1, 1)
                ))), 1)),

                new DropTableDBEntry(DropTableId.FEATHERS.asInt(), new VersionedJson(gson.toJson(new DropTableRecord(List.of(
                        new DropItemEntry(ItemId.FEATHER.asInt(), 1, 15, 2)
                ))), 1))

        );
    }

    ;
}
