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
