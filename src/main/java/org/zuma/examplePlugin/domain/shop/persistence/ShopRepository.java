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
            new ShopItemConfig(ItemId.SMITHING_HAMMER.asInt(), 8, 30, 80L, 10L),
            new ShopItemConfig(ItemId.FLINT_AND_STEEL.asInt(), 8, 30, 20L, 5L)
    );


    public List<ShopConfig> getAll() {
        return List.of(

                new ShopConfig(ShopId.GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1)));


    }
}

