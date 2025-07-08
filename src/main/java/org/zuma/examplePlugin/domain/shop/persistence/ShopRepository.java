package org.zuma.examplePlugin.domain.shop.persistence;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.shop.constants.ShopId;
import org.zuma.examplePlugin.domain.shop.meta.v1.ShopMeta;

import java.util.List;

public class ShopRepository {
    private final Gson gson = new Gson();

    private static final List<ShopItemConfig> GENERAL_STORE_ITEMS = List.of(
            new ShopItemConfig(ItemId.SMITHING_HAMMER.asInt(), 8, 30, 100L, 20L),
            new ShopItemConfig(ItemId.SHEARS.asInt(), 8, 30, 35L, 10L),
            new ShopItemConfig(ItemId.CHISEL.asInt(), 8, 30, 35L, 10L),
            new ShopItemConfig(ItemId.FLINT_AND_STEEL.asInt(), 8, 30, 40L, 12L),
            new ShopItemConfig(ItemId.EMPTY_POT.asInt(), 8, 30, 10L, 3L),
            new ShopItemConfig(ItemId.EMPTY_BUCKET.asInt(), 8, 30, 10L, 3L),
            new ShopItemConfig(ItemId.POT_OF_FLOUR.asInt(), 0, 10, 25L, 8L)
    );


    public List<ShopConfig> getAll() {
        return List.of(

                new ShopConfig(ShopId.GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1)));


    }
}

