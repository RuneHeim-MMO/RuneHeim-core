package org.zuma.examplePlugin.domain.shop;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.impl.fairy.Fairy;
import org.zuma.examplePlugin.domain.item.meta.v1.FairyMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.FairyStateMeta;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;
import org.zuma.examplePlugin.domain.shop.meta.v1.ShopMeta;
import org.zuma.examplePlugin.domain.shop.persistence.ShopConfig;

import java.util.ArrayList;

public class ShopFactory {
    private final Gson gson = new Gson();

    public Shop createShop(ShopConfig config) {
        if (config.json() == null) {
            throw new UnsupportedOperationException("Cannot create shop with id " + config.id() + " required json is null!");
        }

        switch (config.json().version()) {
            case 1 -> {
                ShopMeta deserialized = gson.fromJson(config.json().meta(), ShopMeta.class);
                return new Shop(config.id(), deserialized.stockConfiguration());
            }
            default -> throw new UnsupportedJsonVersionException("Shop", config.json().version());
        }

    }

}
