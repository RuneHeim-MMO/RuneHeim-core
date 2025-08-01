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
            new ShopItemConfig(ItemId.SMITHING_HAMMER.asInt(), 8, 30, 100L, 20L, null, null),
            new ShopItemConfig(ItemId.SHEARS.asInt(), 8, 30, 35L, 10L, null, null),
            new ShopItemConfig(ItemId.CHISEL.asInt(), 8, 30, 35L, 10L, null, null),
            new ShopItemConfig(ItemId.FLINT_AND_STEEL.asInt(), 8, 30, 40L, 12L, null, null),
            new ShopItemConfig(ItemId.EMPTY_POT.asInt(), 8, 30, 10L, 3L, null, null),
            new ShopItemConfig(ItemId.EMPTY_BUCKET.asInt(), 8, 30, 10L, 3L, null, null),
            new ShopItemConfig(ItemId.POT_OF_FLOUR.asInt(), 0, 10, 25L, 8L, null, null)
    );


    public List<ShopConfig> getAll() {
        return List.of(

                new ShopConfig(ShopId.JEWLER.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.UNCUT_SAPPHIRE.asInt(), 4, 30, 300L, 60L, null, null),
                        new ShopItemConfig(ItemId.UNCUT_EMERALD.asInt(), 3, 30, 400L, 80L, null, null),
                        new ShopItemConfig(ItemId.UNCUT_RUBY.asInt(), 2, 30, 600L, 120L, null, null),
                        new ShopItemConfig(ItemId.UNCUT_DIAMOND.asInt(), 1, 30, 900L, 180L, null, null),

                        new ShopItemConfig(ItemId.SAPPHIRE.asInt(), 0, 300, 600L, 120L, null, null),
                        new ShopItemConfig(ItemId.EMERALD.asInt(), 0, 300, 800L, 160L, null, null),
                        new ShopItemConfig(ItemId.RUBY.asInt(), 0, 300, 1200L, 240L, null, null),
                        new ShopItemConfig(ItemId.DIAMOND.asInt(), 0, 300, 1800L, 360L, null, null)
                ))), 1)),

                new ShopConfig(ShopId.INVESTMENT_BANKER.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.PLATINUM_TOKEN.asInt(), 1000, 30, 1000L, 1000L, null, null),
                        new ShopItemConfig(ItemId.SCRATCH_CARD.asInt(), 10, 30, 1000L, 500L, null, null)
                ))), 1)),

                new ShopConfig(ShopId.GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1)),

                new ShopConfig(ShopId.DRAKKAR_BLACKSMITH.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.SMITHING_HAMMER.asInt(), 8, 30, 90L, 20L, null, null),
                        new ShopItemConfig(ItemId.BRONZE_BAR.asInt(), 8, 30, 250L, 30L, null, null),
                        new ShopItemConfig(ItemId.IRON_BAR.asInt(), 8, 30, 350L, 50L, null, null),
                        new ShopItemConfig(ItemId.STEEL_BAR.asInt(), 8, 30, 450L, 70L, null, null),
                        new ShopItemConfig(ItemId.COAL_ORE.asInt(), 4, 30, 80L, 20L, null, null)
                ))), 1))


        );


    }
}

