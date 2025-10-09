package org.zuma.examplePlugin.domain.shop.persistence;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.shop.constants.ShopId;
import org.zuma.examplePlugin.domain.shop.meta.v1.ShopMeta;

import java.util.List;

import static org.zuma.examplePlugin.domain.shop.ItemPriceCalculator.getPrice;

public class ShopRepository {
    private final Gson gson = new Gson();

    private static final List<ShopItemConfig> GENERAL_STORE_ITEMS = List.of(
            new ShopItemConfig(ItemId.SMITHING_HAMMER.asInt(), 8, 30, 100L, 20L, null, null),
            new ShopItemConfig(ItemId.SHEARS.asInt(), 8, 30, 35L, 10L, null, null),
            new ShopItemConfig(ItemId.CHISEL.asInt(), 8, 30, 35L, 10L, null, null),
            new ShopItemConfig(ItemId.FLINT_AND_STEEL.asInt(), 8, 30, 40L, 12L, null, null),
            new ShopItemConfig(ItemId.COMPASS.asInt(), 8, 30, ItemId.COMPASS.getBuyPrice(), ItemId.COMPASS.getSellPrice(), null, null)
            // new ShopItemConfig(ItemId.EMPTY_POT.asInt(), 8, 30, 10L, 3L, null, null),
            // new ShopItemConfig(ItemId.EMPTY_BUCKET.asInt(), 8, 30, 10L, 3L, null, null),
            // new ShopItemConfig(ItemId.POT_OF_FLOUR.asInt(), 0, 10, 25L, 8L, null, null)
    );


    public List<ShopConfig> getAll() {
        return List.of(

                new ShopConfig(ShopId.JEWLER.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.UNCUT_SAPPHIRE.asInt(), 4, 30, ItemId.UNCUT_SAPPHIRE.getBuyPrice(), ItemId.UNCUT_SAPPHIRE.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.UNCUT_EMERALD.asInt(), 3, 30, ItemId.UNCUT_EMERALD.getBuyPrice(), ItemId.UNCUT_EMERALD.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.UNCUT_RUBY.asInt(), 2, 30, ItemId.UNCUT_RUBY.getBuyPrice(), ItemId.UNCUT_RUBY.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.UNCUT_DIAMOND.asInt(), 1, 30, ItemId.UNCUT_DIAMOND.getBuyPrice(), ItemId.UNCUT_DIAMOND.getSellPrice(), null, null),

                        new ShopItemConfig(ItemId.SAPPHIRE.asInt(), 0, 300, ItemId.SAPPHIRE.getBuyPrice(), ItemId.SAPPHIRE.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.EMERALD.asInt(), 0, 300, ItemId.EMERALD.getBuyPrice(), ItemId.EMERALD.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.RUBY.asInt(), 0, 300, ItemId.RUBY.getBuyPrice(), ItemId.RUBY.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.DIAMOND.asInt(), 0, 300, ItemId.DIAMOND.getBuyPrice(), ItemId.DIAMOND.getSellPrice(), null, null),

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
                        new ShopItemConfig(ItemId.BRONZE_BAR.asInt(), 8, 30, ItemId.BRONZE_BAR.getBuyPrice(), ItemId.BRONZE_BAR.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.IRON_BAR.asInt(), 8, 30, ItemId.IRON_BAR.getBuyPrice(), ItemId.IRON_BAR.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_BAR.asInt(), 8, 30, ItemId.STEEL_BAR.getBuyPrice(), ItemId.STEEL_BAR.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.COAL_ORE.asInt(), 4, 30, ItemId.COAL_ORE.getBuyPrice(), ItemId.COAL_ORE.getSellPrice(), null, null)
                ))), 1)),

                new ShopConfig(ShopId.DRAKKAR_ARMOR_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.BRONZE_DAGGER.asInt(), 8, 30, ItemId.BRONZE_DAGGER.getBuyPrice(), ItemId.BRONZE_DAGGER.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.BRONZE_SWORD.asInt(), 8, 30, ItemId.BRONZE_SWORD.getBuyPrice(), ItemId.BRONZE_SWORD.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.BRONZE_BATTLEAXE.asInt(), 8, 30, ItemId.BRONZE_BATTLEAXE.getBuyPrice(), ItemId.BRONZE_BATTLEAXE.getSellPrice(), null, null),

                        new ShopItemConfig(ItemId.BRONZE_MEDHELM.asInt(), 4, 30, ItemId.BRONZE_MEDHELM.getBuyPrice(), ItemId.BRONZE_MEDHELM.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.BRONZE_BODY.asInt(), 4, 30, ItemId.BRONZE_BODY.getBuyPrice(), ItemId.BRONZE_BODY.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.BRONZE_PLATELEGS.asInt(), 4, 30, ItemId.BRONZE_PLATELEGS.getBuyPrice(), ItemId.BRONZE_PLATELEGS.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.BRONZE_BOOTS.asInt(), 4, 30, ItemId.BRONZE_BOOTS.getBuyPrice(), ItemId.BRONZE_BOOTS.getSellPrice(), null, null),


                        new ShopItemConfig(ItemId.IRON_DAGGER.asInt(), 6, 60, ItemId.IRON_DAGGER.getBuyPrice(), ItemId.IRON_DAGGER.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.IRON_SWORD.asInt(), 6, 60, ItemId.IRON_SWORD.getBuyPrice(), ItemId.IRON_SWORD.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.IRON_BATTLEAXE.asInt(), 6, 60, ItemId.IRON_BATTLEAXE.getBuyPrice(), ItemId.IRON_BATTLEAXE.getSellPrice(), null, null),

                        new ShopItemConfig(ItemId.IRON_MEDHELM.asInt(), 3, 30, ItemId.IRON_MEDHELM.getBuyPrice(), ItemId.IRON_MEDHELM.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.IRON_BODY.asInt(), 3, 30, ItemId.IRON_BODY.getBuyPrice(), ItemId.IRON_BODY.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.IRON_PLATELEGS.asInt(), 3, 30, ItemId.IRON_PLATELEGS.getBuyPrice(), ItemId.IRON_PLATELEGS.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.IRON_BOOTS.asInt(), 3, 30, ItemId.IRON_BOOTS.getBuyPrice(), ItemId.IRON_BOOTS.getSellPrice(), null, null),


                        new ShopItemConfig(ItemId.STEEL_DAGGER.asInt(), 4, 120, ItemId.STEEL_DAGGER.getBuyPrice(), ItemId.STEEL_DAGGER.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_SWORD.asInt(), 4, 120, ItemId.STEEL_SWORD.getBuyPrice(), ItemId.STEEL_SWORD.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_BATTLEAXE.asInt(), 4, 120, ItemId.STEEL_BATTLEAXE.getBuyPrice(), ItemId.STEEL_BATTLEAXE.getSellPrice(), null, null),

                        new ShopItemConfig(ItemId.STEEL_MEDHELM.asInt(), 2, 30, ItemId.STEEL_MEDHELM.getBuyPrice(), ItemId.STEEL_MEDHELM.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_CHAINBODY.asInt(), 2, 30, ItemId.STEEL_CHAINBODY.getBuyPrice(), ItemId.STEEL_CHAINBODY.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_PLATELEGS.asInt(), 2, 30, ItemId.STEEL_PLATELEGS.getBuyPrice(), ItemId.STEEL_PLATELEGS.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_BOOTS.asInt(), 2, 30, ItemId.STEEL_BOOTS.getBuyPrice(), ItemId.STEEL_BOOTS.getSellPrice(), null, null)


                ))), 1)),

                new ShopConfig(ShopId.BASIC_FOOD_STALL.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.COOKED_MEAT.asInt(), 32, 30, ItemId.COOKED_MEAT.getBuyPrice(), null, null, null),
                        new ShopItemConfig(ItemId.COOKED_CHICKEN.asInt(), 32, 30, ItemId.COOKED_CHICKEN.getBuyPrice(), null, null, null)
                ))), 1)),

                new ShopConfig(ShopId.DRAKKAR_GUARD_ARCHER_MERCHANT.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.SHORT_BOW.asInt(), 4, 30, ItemId.SHORT_BOW.getBuyPrice(), null, null, null),
                        new ShopItemConfig(ItemId.LONG_BOW.asInt(), 2, 30, ItemId.LONG_BOW.getBuyPrice(), null, null, null),

                        new ShopItemConfig(ItemId.BIRCH_SHORT_BOW.asInt(), 3, 30, ItemId.BIRCH_SHORT_BOW.getBuyPrice(), null, null, null),
                        new ShopItemConfig(ItemId.BIRCH_LONG_BOW.asInt(), 2, 30, ItemId.BIRCH_LONG_BOW.getBuyPrice(), null, null, null),

                        new ShopItemConfig(ItemId.MAPLE_SHORT_BOW.asInt(), 1, 600, ItemId.MAPLE_SHORT_BOW.getBuyPrice(), null, null, null),
                        new ShopItemConfig(ItemId.MAPLE_LONG_BOW.asInt(), 1, 600, ItemId.MAPLE_LONG_BOW.getBuyPrice(), null, null, null),

                        new ShopItemConfig(ItemId.IRON_CROSSBOW.asInt(), 3, 3600, 1200L, null, null, null),

                        new ShopItemConfig(ItemId.BRONZE_ARROW.asInt(), 512, 10, ItemId.BRONZE_ARROW.getBuyPrice(), ItemId.BRONZE_ARROW.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.IRON_ARROW.asInt(), 512, 10, ItemId.IRON_ARROW.getBuyPrice(), ItemId.IRON_ARROW.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_ARROW.asInt(), 512, 10, ItemId.STEEL_ARROW.getBuyPrice(), ItemId.STEEL_ARROW.getSellPrice(), null, null)

                ))), 1)),

                new ShopConfig(ShopId.INFERNAK_BLACKSMITH.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.SMITHING_HAMMER.asInt(), 8, 30, 90L, 20L, null, null),
                        new ShopItemConfig(ItemId.STEEL_BAR.asInt(), 16, 30, ItemId.STEEL_BAR.getBuyPrice(), ItemId.STEEL_BAR.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.MITHRIL_BAR.asInt(), 4, 30, ItemId.MITHRIL_BAR.getBuyPrice(), ItemId.MITHRIL_BAR.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.ADAMANT_BAR.asInt(), 4, 30, ItemId.ADAMANT_BAR.getBuyPrice(), ItemId.ADAMANT_BAR.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.COAL_ORE.asInt(), 64, 30, ItemId.COAL_ORE.getBuyPrice(), ItemId.COAL_ORE.getSellPrice(), null, null)
                ))), 1)),


                new ShopConfig(ShopId.VALDRIN_WEAPON_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.MITHRIL_SWORD.asInt(), 2, 30, ItemId.MITHRIL_SWORD.getBuyPrice(), ItemId.MITHRIL_SWORD.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_BATTLEAXE.asInt(), 4, 30, ItemId.STEEL_BATTLEAXE.getBuyPrice(), ItemId.STEEL_BATTLEAXE.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_DAGGER.asInt(), 4, 30, ItemId.STEEL_DAGGER.getBuyPrice(), ItemId.STEEL_DAGGER.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_SWORD.asInt(), 4, 30, ItemId.STEEL_SWORD.getBuyPrice(), ItemId.STEEL_SWORD.getSellPrice(), null, null)
                ))), 1)),

                new ShopConfig(ShopId.ELDARWYN_ARMOR_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.MITHRIL_BOOTS.asInt(), 2, 30, ItemId.MITHRIL_BOOTS.getBuyPrice(), ItemId.MITHRIL_BOOTS.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.MITHRIL_PLATELEGS.asInt(), 2, 30, ItemId.MITHRIL_PLATELEGS.getBuyPrice(), ItemId.MITHRIL_PLATELEGS.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.MITHRIL_CHAINBODY.asInt(), 2, 30, ItemId.MITHRIL_CHAINBODY.getBuyPrice(), ItemId.MITHRIL_CHAINBODY.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.MITHRIL_MEDHELM.asInt(), 2, 30, ItemId.MITHRIL_MEDHELM.getBuyPrice(), ItemId.MITHRIL_MEDHELM.getSellPrice(), null, null),

                        new ShopItemConfig(ItemId.ADAMANT_PLATELENG.asInt(), 1, 3600 * 2, ItemId.ADAMANT_PLATELENG.getBuyPrice(), ItemId.ADAMANT_PLATELENG.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.ADAMANT_CHAINBODY.asInt(), 1, 3600 * 2, ItemId.ADAMANT_CHAINBODY.getBuyPrice(), ItemId.ADAMANT_CHAINBODY.getSellPrice(), null, null)
                ))), 1)),

                new ShopConfig(ShopId.SAHARIJIN_FISHING_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.REINFORCED_FISHING_ROD.asInt(), 2, 600, 1800L, null, null, null),
                        new ShopItemConfig(ItemId.FISHING_TRAP.asInt(), 2, 600, 2800L, null, null, null)
                ))), 1)),

                new ShopConfig(ShopId.SPOREFELL_FISHING_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.FISHING_NET.asInt(), 8, 600, 180L, null, null, null),
                        new ShopItemConfig(ItemId.FISHING_ROD.asInt(), 4, 600, 360L, null, null, null),
                        new ShopItemConfig(ItemId.REINFORCED_FISHING_ROD.asInt(), 2, 600, 1800L, null, null, null)
                ))), 1)),

                new ShopConfig(ShopId.ASHWALE_MINING_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.BRONZE_PICKAXE.asInt(), 8, 300, ItemId.BRONZE_PICKAXE.getBuyPrice(), ItemId.BRONZE_PICKAXE.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.IRON_PICKAXE.asInt(), 6, 300, ItemId.IRON_PICKAXE.getBuyPrice(), ItemId.IRON_PICKAXE.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_PICKAXE.asInt(), 4, 300, ItemId.STEEL_PICKAXE.getBuyPrice(), ItemId.STEEL_PICKAXE.getSellPrice(), null, null)
                ))), 1)),

                new ShopConfig(ShopId.ASHWALE_AXE_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.BRONZE_AXE.asInt(), 8, 300, ItemId.BRONZE_AXE.getBuyPrice(), ItemId.BRONZE_AXE.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.IRON_AXE.asInt(), 6, 300, ItemId.IRON_AXE.getBuyPrice(), ItemId.IRON_AXE.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_AXE.asInt(), 4, 300, ItemId.STEEL_AXE.getBuyPrice(), ItemId.STEEL_AXE.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.MITHRIL_AXE.asInt(), 2, 300, ItemId.MITHRIL_AXE.getBuyPrice(), ItemId.MITHRIL_AXE.getSellPrice(), null, null)
                ))), 1)),

                new ShopConfig(ShopId.MURKWATCH_ARCHERY_MERCHANT.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.SHORT_BOW.asInt(), 4, 30, ItemId.SHORT_BOW.getBuyPrice(), null, null, null),
                        new ShopItemConfig(ItemId.LONG_BOW.asInt(), 2, 30, ItemId.LONG_BOW.getBuyPrice(), null, null, null),

                        new ShopItemConfig(ItemId.BIRCH_SHORT_BOW.asInt(), 3, 30, ItemId.BIRCH_SHORT_BOW.getBuyPrice(), null, null, null),
                        new ShopItemConfig(ItemId.BIRCH_LONG_BOW.asInt(), 2, 30, ItemId.BIRCH_LONG_BOW.getBuyPrice(), null, null, null),

                        new ShopItemConfig(ItemId.MAPLE_SHORT_BOW.asInt(), 1, 600, ItemId.MAPLE_SHORT_BOW.getBuyPrice(), null, null, null),
                        new ShopItemConfig(ItemId.MAPLE_LONG_BOW.asInt(), 1, 600, ItemId.MAPLE_LONG_BOW.getBuyPrice(), null, null, null),

                        new ShopItemConfig(ItemId.YEW_SHORT_BOW.asInt(), 1, 600, ItemId.YEW_SHORT_BOW.getBuyPrice(), null, null, null),
                        new ShopItemConfig(ItemId.YEW_LONG_BOW.asInt(), 1, 600, ItemId.YEW_LONG_BOW.getBuyPrice(), null, null, null),

                        new ShopItemConfig(ItemId.IRON_CROSSBOW.asInt(), 3, 3600, 1200L, null, null, null),
                        new ShopItemConfig(ItemId.STEEL_CROSSBOW.asInt(), 3, 3600, 1800L, null, null, null),

                        new ShopItemConfig(ItemId.BRONZE_ARROW.asInt(), 512, 10, ItemId.BRONZE_ARROW.getBuyPrice(), ItemId.BRONZE_ARROW.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.IRON_ARROW.asInt(), 512, 10, ItemId.IRON_ARROW.getBuyPrice(), ItemId.IRON_ARROW.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_ARROW.asInt(), 512, 10, ItemId.STEEL_ARROW.getBuyPrice(), ItemId.STEEL_ARROW.getSellPrice(), null, null)

                ))), 1)),

                new ShopConfig(ShopId.MURKWATCH_BLACKSMITH.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.SMITHING_HAMMER.asInt(), 8, 30, 90L, 20L, null, null),
                        new ShopItemConfig(ItemId.STEEL_BAR.asInt(), 8, 30, ItemId.STEEL_BAR.getBuyPrice(), ItemId.STEEL_BAR.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.MITHRIL_BAR.asInt(), 4, 30, ItemId.MITHRIL_BAR.getBuyPrice(), ItemId.MITHRIL_BAR.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.COPPER_ORE.asInt(), 32, 30, ItemId.COPPER_ORE.getBuyPrice(), ItemId.COPPER_ORE.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.COAL_ORE.asInt(), 32, 30, ItemId.COAL_ORE.getBuyPrice(), ItemId.COAL_ORE.getSellPrice(), null, null)
                ))), 1)),

                new ShopConfig(ShopId.BRINEPORT_FISHING_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.FISHING_NET.asInt(), 8, 600, 180L, null, null, null),
                        new ShopItemConfig(ItemId.FISHING_ROD.asInt(), 4, 600, 360L, null, null, null)
                ))), 1)),


                new ShopConfig(ShopId.FENSHADE_MINING_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.BRONZE_PICKAXE.asInt(), 8, 300, ItemId.BRONZE_PICKAXE.getBuyPrice(), ItemId.BRONZE_PICKAXE.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.IRON_PICKAXE.asInt(), 6, 300, ItemId.IRON_PICKAXE.getBuyPrice(), ItemId.IRON_PICKAXE.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.STEEL_PICKAXE.asInt(), 4, 300, ItemId.STEEL_PICKAXE.getBuyPrice(), ItemId.STEEL_PICKAXE.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.MITHRIL_PICKAXE.asInt(), 4, 300, ItemId.MITHRIL_PICKAXE.getBuyPrice(), ItemId.MITHRIL_PICKAXE.getSellPrice(), null, null)
                ))), 1)),

                new ShopConfig(ShopId.SAKURMEER_FOOD_SHOP.asInt(), new VersionedJson(gson.toJson(new ShopMeta(List.of(
                        new ShopItemConfig(ItemId.COOKED_SALMON.asInt(), 32, 30, ItemId.COOKED_SALMON.getBuyPrice(), ItemId.COOKED_SALMON.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.COOKED_TROUT.asInt(), 20, 30, ItemId.COOKED_TROUT.getBuyPrice(), ItemId.COOKED_TROUT.getSellPrice(), null, null),
                        new ShopItemConfig(ItemId.COOKED_TUNA.asInt(), 14, 30, ItemId.COOKED_TUNA.getBuyPrice(), ItemId.COOKED_TUNA.getSellPrice(), null, null)

                ))), 1)),

                new ShopConfig(ShopId.BRINEPORT_GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1)),
                new ShopConfig(ShopId.MURKWATCH_GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1)),
                new ShopConfig(ShopId.ELDARWYN_GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1)),
                new ShopConfig(ShopId.SAKURMEER_GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1)),
                new ShopConfig(ShopId.AETHERIS_GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1)),
                new ShopConfig(ShopId.ISLENOR_GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1)),
                new ShopConfig(ShopId.THORNBARK_GENERAL_STORE.asInt(), new VersionedJson(gson.toJson(new ShopMeta(GENERAL_STORE_ITEMS)), 1))


        );


    }
}
