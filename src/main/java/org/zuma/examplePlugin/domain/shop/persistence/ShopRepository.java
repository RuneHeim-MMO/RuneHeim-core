package org.zuma.examplePlugin.domain.shop.persistence;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.shop.constants.ShopId;
import org.zuma.examplePlugin.domain.shop.meta.v1.ShopMeta;

import java.util.List;

public class ShopRepository {
    private final Gson gson = new Gson();

    public List<ShopConfig> getAll() {
        return List.of(

                new ShopConfig(ShopId.GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.YEW_LOG.asInt(), 64, null, 80L, 20L)


                ))), 1)));


    }
}

