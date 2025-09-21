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
            new ShopItemConfig(ItemId.COMPASS.asInt(), 8, 30, 80L, 15L, null, null)
            // new ShopItemConfig(ItemId.EMPTY_POT.asInt(), 8, 30, 10L, 3L, null, null),
            // new ShopItemConfig(ItemId.EMPTY_BUCKET.asInt(), 8, 30, 10L, 3L, null, null),
            // new ShopItemConfig(ItemId.POT_OF_FLOUR.asInt(), 0, 10, 25L, 8L, null, null)
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
                        new ShopItemConfig(ItemId.DIAMOND.asInt(), 0, 300, 1800L, 360L, null, null),

                        new ShopItemConfig(ItemId.BRONZE_RING.asInt(), 2, 300, 400L, 360L, null, null),
                        new ShopItemConfig(ItemId.AMULET_OF_GUARDING.asInt(), 1, 300, 1000L, 360L, null, null)
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
                ))), 1)),

                new ShopConfig(ShopId.DRAKKAR_ARMOR_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.BRONZE_DAGGER.asInt(), 8, 30, 200L, 20L, null, null),
                        new ShopItemConfig(ItemId.BRONZE_SWORD.asInt(), 8, 30, 250L, 25L, null, null),
                        new ShopItemConfig(ItemId.BRONZE_BATTLEAXE.asInt(), 8, 30, 600L, 70L, null, null),

                        new ShopItemConfig(ItemId.BRONZE_MEDHELM.asInt(), 4, 30, 500L, 50L, null, null),
                        new ShopItemConfig(ItemId.BRONZE_BODY.asInt(), 4, 30, 1000L, 100L, null, null),
                        new ShopItemConfig(ItemId.BRONZE_PLATELEGS.asInt(), 4, 30, 750L, 75L, null, null),
                        new ShopItemConfig(ItemId.BRONZE_BOOTS.asInt(), 4, 30, 500L, 50L, null, null),


                        new ShopItemConfig(ItemId.IRON_DAGGER.asInt(), 6, 60, 300L, 35L, null, null),
                        new ShopItemConfig(ItemId.IRON_SWORD.asInt(), 6, 60, 350L, 40L, null, null),
                        new ShopItemConfig(ItemId.IRON_BATTLEAXE.asInt(), 6, 60, 700L, 120L, null, null),

                        new ShopItemConfig(ItemId.IRON_MEDHELM.asInt(), 3, 30, 700L, 70L, null, null),
                        new ShopItemConfig(ItemId.IRON_BODY.asInt(), 3, 30, 1400L, 140L, null, null),
                        new ShopItemConfig(ItemId.IRON_PLATELEGS.asInt(), 3, 30, 1050L, 100L, null, null),
                        new ShopItemConfig(ItemId.IRON_BOOTS.asInt(), 3, 30, 700L, 70L, null, null),


                        new ShopItemConfig(ItemId.STEEL_DAGGER.asInt(), 4, 120, 450L, 45L, null, null),
                        new ShopItemConfig(ItemId.STEEL_SWORD.asInt(), 4, 120, 500L, 50L, null, null),
                        new ShopItemConfig(ItemId.STEEL_BATTLEAXE.asInt(), 4, 120, 1250L, 125L, null, null),

                        new ShopItemConfig(ItemId.STEEL_MEDHELM.asInt(), 2, 30, 900L, 90L, null, null),
                        new ShopItemConfig(ItemId.STEEL_BODY.asInt(), 2, 30, 1800L, 180L, null, null),
                        new ShopItemConfig(ItemId.STEEL_PLATELEGS.asInt(), 2, 30, 1350L, 135L, null, null),
                        new ShopItemConfig(ItemId.STEEL_BOOTS.asInt(), 2, 30, 900L, 90L, null, null)


                ))), 1)),

                new ShopConfig(ShopId.BASIC_FOOD_STALL.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.COOKED_MEAT.asInt(), 32, 30, 40L, null, null, null),
                        new ShopItemConfig(ItemId.COOKED_CHICKEN.asInt(), 32, 30, 40L, null, null, null)
                ))), 1)),

                new ShopConfig(ShopId.DRAKKAR_GUARD_ARCHER_MERCHANT.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.SHORT_BOW.asInt(), 4, 30, 200L, null, null, null),
                        new ShopItemConfig(ItemId.LONG_BOW.asInt(), 2, 30, 250L, null, null, null),

                        new ShopItemConfig(ItemId.BIRCH_SHORT_BOW.asInt(), 3, 30, 300L, null, null, null),
                        new ShopItemConfig(ItemId.BIRCH_LONG_BOW.asInt(), 2, 30, 350L, null, null, null),

                        new ShopItemConfig(ItemId.MAPLE_SHORT_BOW.asInt(), 1, 7200, 2500L, null, null, null),
                        new ShopItemConfig(ItemId.MAPLE_LONG_BOW.asInt(), 1, 7200, 2850L, null, null, null),

                        new ShopItemConfig(ItemId.IRON_CROSSBOW.asInt(), 3, 3600, 3500L, null, null, null),

                        new ShopItemConfig(ItemId.BRONZE_ARROW.asInt(), 512, 10, 10L, 1L, null, null),
                        new ShopItemConfig(ItemId.IRON_ARROW.asInt(), 512, 10, 15L, 2L, null, null),
                        new ShopItemConfig(ItemId.STEEL_ARROW.asInt(), 512, 10, 20L, 3L, null, null)

                ))), 1)),

                new ShopConfig(ShopId.INFERNAK_BLACKSMITH.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        // todo
                ))), 1)),


                new ShopConfig(ShopId.VALDRIN_WEAPON_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        // todo
                ))), 1)),

                new ShopConfig(ShopId.ELDARWYN_ARMOR_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        // todo
                ))), 1)),

                new ShopConfig(ShopId.SAHARIJIN_FISHING_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        // todo
                ))), 1)),

                new ShopConfig(ShopId.SPOREFELL_FISHING_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        // todo
                ))), 1)),

                new ShopConfig(ShopId.ASHWALE_MINING_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        // todo
                ))), 1)),

                new ShopConfig(ShopId.ASHWALE_AXE_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        // todo
                ))), 1)),

                new ShopConfig(ShopId.MURKWATCH_ARCHERY_MERCHANT.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        // todo
                ))), 1)),

                new ShopConfig(ShopId.MURKWATCH_BLACKSMITH.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        // todo
                ))), 1)),

                new ShopConfig(ShopId.BRINEPORT_FISHING_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        // todo
                ))), 1)),


                new ShopConfig(ShopId.FENSHADE_MINING_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        // todo
                ))), 1)),

                new ShopConfig(ShopId.BRINEPORT_GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1)),
                new ShopConfig(ShopId.MURKWATCH_GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1)),
                new ShopConfig(ShopId.ELDARWYN_GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1))


        );


    }
}

