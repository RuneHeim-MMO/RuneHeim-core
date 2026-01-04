package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.dialog.Dialogs;
import org.zuma.examplePlugin.domain.dialog.constants.DialogPreset;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.InventoryOpenerMob;

import java.util.List;
import java.util.function.Supplier;

public class Shopkeepers {

    private static final Supplier<DialogBuilder> glaciemWeaponShopDialog = () -> DialogBuilder.create("glaciem-weapon-shop-dialog")
            .passiveStep(c -> c.randomText(List.of("Looking for some fine weapons?", "This land is rich of Mithril!")))
            .interactiveStep(c -> c.text("Open shop?")
                    .option("§aYes" + " §f" + Icon.MONEY_BAG.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNo", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));


    private static final Supplier<DialogBuilder> genericShopDialog = () -> DialogBuilder.create("generic-shop-dialog")
            .interactiveStep(c -> c.randomText(DialogPreset.WANT_TO_SEE_STOCK.getList())
                    .option("§aYes" + " §f" + Icon.MONEY_BAG.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNo", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));

    private static final Supplier<DialogBuilder> foodShopDialog = () -> DialogBuilder.create("food-shop-dialog")
            .interactiveStep(c -> c.randomText(DialogPreset.WANT_TO_SEE_FOOD_STOCK.getList())
                    .option("§aYes" + " §f" + Icon.MONEY_BAG.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNo", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));

    private static final Supplier<DialogBuilder> tannerDialog = () -> DialogBuilder.create("tanner-dialog")
            .interactiveStep(c -> c.randomText(List.of("Want leather? I’ll tan your hides."))
                    .option("§aYes" + " §f" + Icon.LEATHER.get(), b -> b.action("open_inventory_{id_38}"))
                    .option("§aSupplies" + " §f" + Icon.MONEY_BAG.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNo", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));


    private static final Supplier<DialogBuilder> guardArcherDialog = () -> DialogBuilder.deserialize("{\"id\":\"guard-archer-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"cda76b8a-06cf-4344-abc9-dbb70291126d\",\"content\":[{\"line\":\"Psst, hey!\",\"typewriter\":true}],\"uuid\":\"c21378fb-2e9b-42d5-9db5-ed2e7fb423f6\",\"actions\":[]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"1dba6230-3701-48f8-a333-9727af1b45ea\",\"content\":[{\"line\":\"I can't really do this.\",\"typewriter\":true},{\"line\":\"But a man's gotta feed his family.\",\"typewriter\":true}],\"uuid\":\"c7d4f9af-7235-463d-97a0-a2fce726ec59\",\"actions\":[]},{\"type\":\"InteractiveDialogStep\",\"uniqueIdentifier\":\"cd3407fe-ee7a-48bc-90de-944ced8125fc\",\"content\":[{\"line\":\"Don’t even think about snitching.\",\"typewriter\":true}],\"options\":[{\"label\":\"§aTrade §fⓩ\",\"actions\":[\"dialog_holder_forward_interaction\"],\"followup\":{\"id\":\"followup-empty\",\"steps\":[],\"uuid\":\"cbada3a7-c433-482e-b07b-0bf3f571dee3\"}},{\"label\":\"Snitching about what?\",\"actions\":[],\"followup\":{\"id\":\"guard-archer-dialog\",\"steps\":[{\"uuid\":\"b747b606-1b85-4a62-ab5f-6f5399a78e18\",\"uniqueIdentifier\":\"c0cadf45-986f-4252-b3f5-b05628af6023\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Nothing, you saw nothing.\",\"typewriter\":true}],\"id\":\"guard-archer-dialog\"}],\"uuid\":\"859c3795-a1fe-46b9-9964-4f7245721d39\"}},{\"label\":\"Or else?\",\"actions\":[],\"followup\":{\"id\":\"guard-archer-dialog\",\"steps\":[{\"uuid\":\"3829c560-6151-4ad8-91d2-877d7bcd5487\",\"uniqueIdentifier\":\"ba18785e-49f2-4707-a067-db5e83cbead1\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Don't play with me!\",\"typewriter\":true},{\"line\":\"You don’t want to see the inside of a cell, do you?\",\"typewriter\":true}],\"id\":\"guard-archer-dialog\"}],\"uuid\":\"29a609d6-49a2-4bae-9cdc-079e2422df3c\"}}],\"uuid\":\"ad671081-d76d-4104-8dbd-5053830a2a5f\"}],\"uuid\":\"4e0a81ea-7bc1-4c16-b325-8328b88165e5\"}");

    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.DRAKKAR_SMELTERY_WORKER.getId(), () -> new InventoryOpenerMob(Icon.PICKAXE.get() + " Smeltery Worker", EquipmentSet.COAL,
                MobBehaviors.VILLAGER_MAN_1.getBehavior(-2), "drakkar_smeltery_worker", null, InventoryId.DRAKKAR_SMELTERY_SHOP.asInt(), 100)
                .attachDialog(Dialogs.drakkarSmelteryWorkerDialog));

        registry.register(MobType.DRAKKAR_GENERAL_STORE.getId(), () -> new InventoryOpenerMob(Icon.CHEST.get() + " General Store", EquipmentSet.PAPER_IN_HAND,
                MobBehaviors.VILLAGER_MAN_1.getBehavior(), "shopkeeper_1", null, InventoryId.DRAKKAR_GENERAL_STORE.asInt(), 40)
                .attachDialog(Dialogs.drakkarGeneralStore));

        registry.register(MobType.DRAKKAR_BLACKSMITH.getId(), () -> new InventoryOpenerMob(Icon.HAMMER.get() + " Blacksmith", EquipmentSet.BLACKSMITH,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "blacksmith", null, InventoryId.DRAKKAR_BLACKSMITH.asInt(), 40)
                .attachDialog(Dialogs.drakkarBlacksmithDialog));

        registry.register(MobType.MURKWATCH_BLACKSMITH.getId(), () -> new InventoryOpenerMob(Icon.HAMMER.get() + " Blacksmith", EquipmentSet.BLACKSMITH,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "murkwatch_blacksmith", null, InventoryId.MURKWATCH_BLACKSMITH.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.DRAKKAR_JEWELER.getId(), () -> new InventoryOpenerMob(Icon.BLUE_GEM.get() + " Jeweler", EquipmentSet.JEWELER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "jeweler", null, InventoryId.DRAKKAR_JEWELER.asInt(), 40)
                .attachDialog(Dialogs.drakkarJewelerDialog));

        registry.register(MobType.SAHARIJIN_JEWELER.getId(), () -> new InventoryOpenerMob(Icon.BLUE_GEM.get() + " Jeweler", EquipmentSet.JEWELER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "saharijin_jeweler", null, InventoryId.SAHARIJIN_JEWELER.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.DRAKKAR_ARMOR_SHOP.getId(), () -> new InventoryOpenerMob(Icon.ARMOR.get() + " Armor Merchant", EquipmentSet.RUNE_CHAIN_AND_SWORD,
                MobBehaviors.VILLAGER_MAN_1.getBehavior(), "armor_merchant", null, InventoryId.DRAKKAR_ARMOR_SHOP.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.BASIC_FOOD_STALL_SHOP.getId(), () -> new InventoryOpenerMob(Icon.FRUIT.get() + " Food Stall", null,
                MobBehaviors.VILLAGER_WOMAN_3.getBehavior(), "food_stall_merchant", null, InventoryId.DRAKKAR_FOOD_STALL.asInt(), 0)
                .attachDialog(foodShopDialog));

        registry.register(MobType.DRAKKAR_GUARD_ARCHER_SHOP.getId(), () -> new InventoryOpenerMob(Icon.BOW.get() + " Town Archer", EquipmentSet.RUNE_FULL_IRON_CROSSBOW,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "guard_archer_merchant", null, InventoryId.DRAKKAR_GUARD_ARCHERY_SHOP.asInt(), 60)
                .attachDialog(guardArcherDialog));

        registry.register(MobType.MURKWATCH_ARCHER_SHOP.getId(), () -> new InventoryOpenerMob(Icon.BOW.get() + " Archery Merchant", EquipmentSet.RUNE_FULL_CROSSBOW,
                MobBehaviors.VILLAGER_WOMAN_2.getBehavior(), "murkwatch_archery_merchant", null, InventoryId.MURKWATCH_ARCHERY_SHOP.asInt(), 30)
                .attachDialog(genericShopDialog));

        registry.register(MobType.BRINEPORT_FISHING_SHOP.getId(), () -> new InventoryOpenerMob(Icon.FISHING_HOOK.get() + " Fishing Merchant", EquipmentSet.FISHERMAN,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "brineport_fisherman", null, InventoryId.BRINEPORT_FISHING_SHOP.asInt(), 40)
                .attachDialog(Dialogs.brineportFishermanDialog));


        registry.register(MobType.FENSHADE_MINING_SHOP.getId(), () -> new InventoryOpenerMob(Icon.PICKAXE.get() + " Mining Merchant", EquipmentSet.PICKAXES,
                MobBehaviors.VILLAGER_WOMAN_5.getBehavior(), "fenshade_miner", null, InventoryId.FENSHADE_MINING_SHOP.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.ASHWALE_MINING_SHOP.getId(), () -> new InventoryOpenerMob(Icon.PICKAXE.get() + " Mining Merchant", EquipmentSet.PICKAXES,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "ashwale_miner", null, InventoryId.ASHWALE_MINING_SHOP.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.ASHWALE_AXE_SHOP.getId(), () -> new InventoryOpenerMob(Icon.AXE.get() + " Axe Merchant", EquipmentSet.AXES,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "ashwale_lumberjack", null, InventoryId.ASHWALE_AXE_SHOP.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.GLACIEM_AXE_SHOP.getId(), () -> new InventoryOpenerMob(Icon.AXE.get() + " Axe Merchant", EquipmentSet.AXES,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "glaciem_lumberjack", null, InventoryId.GLACIEM_AXE_SHOP.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.GLACIEM_WEAPON_SHOP.getId(), () -> new InventoryOpenerMob(Icon.SWORD.get() + " Weapon Merchant", EquipmentSet.MITHRIL_CHAIN_HALF_SET_HAMMER,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "glaciem_weapon_merchant", null, InventoryId.GLACIEM_WEAPON_SHOP.asInt(), 30)
                .attachDialog(glaciemWeaponShopDialog));

        registry.register(MobType.SPOREFELL_FISHING_SHOP.getId(), () -> new InventoryOpenerMob(Icon.FISHING_HOOK.get() + " Fishing Merchant", EquipmentSet.FISHERMAN,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "sporefell_fisherman", null, InventoryId.SPOREFELL_FISHING_SHOP.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.SAHARIJIN_FISHING_SHOP.getId(), () -> new InventoryOpenerMob(Icon.FISHING_HOOK.get() + " Fishing Merchant", EquipmentSet.FISHERMAN,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "saharijin_fisherman", null, InventoryId.SAHARIJIN_FISHING_SHOP.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.ELDARWYN_ARMOR_SHOP.getId(), () -> new InventoryOpenerMob(Icon.ARMOR.get() + " Armor Merchant", EquipmentSet.MITHRIL_CHAIN_HALF_SET_SWORD,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "eldarwyn_armor_merchant", null, InventoryId.ELDARWYN_ARMOR_SHOP.asInt(), 30)
                .attachDialog(genericShopDialog));

        registry.register(MobType.VALDRIN_WEAPON_SHOP.getId(), () -> new InventoryOpenerMob(Icon.SWORD.get() + " Weapon Merchant", EquipmentSet.ADAMANT_CHAIN_HALF_SET_SWORD,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "valdrin_weapon_merchant", null, InventoryId.VALDRIN_WEAPON_SHOP.asInt(), 30)
                .attachDialog(genericShopDialog));

        registry.register(MobType.INFERNAK_BLACKSMITH.getId(), () -> new InventoryOpenerMob(Icon.HAMMER.get() + " Blacksmith", EquipmentSet.BLACKSMITH,
                MobBehaviors.HELLFLESH_1.getBehavior(), "infernak_blacksmith", null, InventoryId.INFERNAK_BLACKSMITH.asInt(), 30)
                .attachDialog(genericShopDialog)); //  shop

        registry.register(MobType.MURKWATCH_GENERAL_STORE.getId(), () -> new InventoryOpenerMob(Icon.CHEST.get() + " General Store", EquipmentSet.PAPER_IN_HAND,
                MobBehaviors.VILLAGER_MAN_1.getBehavior(), "shopkeeper_2", null, InventoryId.MURKWATCH_GENERAL_STORE.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.ELDARWYN_GENERAL_STORE.getId(), () -> new InventoryOpenerMob(Icon.CHEST.get() + " General Store", EquipmentSet.PAPER_IN_HAND,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "shopkeeper_3", null, InventoryId.ELDARWYN_GENERAL_STORE.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.BRINEPORT_GENERAL_STORE.getId(), () -> new InventoryOpenerMob(Icon.CHEST.get() + " General Store", EquipmentSet.PAPER_IN_HAND,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "shopkeeper_4", null, InventoryId.BRINEPORT_GENERAL_STORE.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.SAKURMEER_GENERAL_STORE.getId(), () -> new InventoryOpenerMob(Icon.CHEST.get() + " General Store", EquipmentSet.PAPER_IN_HAND,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "shopkeeper_5", null, InventoryId.SAKURMEER_GENERAL_STORE.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.AETHERIS_GENERAL_STORE.getId(), () -> new InventoryOpenerMob(Icon.CHEST.get() + " General Store", EquipmentSet.PAPER_IN_HAND,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "shopkeeper_6", null, InventoryId.AETHERIS_GENERAL_STORE.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.THORNBARK_GENERAL_STORE.getId(), () -> new InventoryOpenerMob(Icon.CHEST.get() + " General Store", EquipmentSet.PAPER_IN_HAND,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "shopkeeper_2", null, InventoryId.THORNBARK_GENERAL_STORE.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.ISLENOR_GENERAL_STORE.getId(), () -> new InventoryOpenerMob(Icon.CHEST.get() + " General Store", EquipmentSet.PAPER_IN_HAND,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "shopkeeper_7", null, InventoryId.ISLENOR_GENERAL_STORE.asInt(), 40)
                .attachDialog(genericShopDialog));

        registry.register(MobType.SAKURMEER_FOOD_SHOP.getId(), () -> new InventoryOpenerMob(Icon.FRUIT.get() + " Caterer", EquipmentSet.MUSHROOM_STEW,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "waiter", null, InventoryId.SAKURMEER_FOOD_SHOP.asInt(), 40)
                .attachDialog(foodShopDialog));

        registry.register(MobType.DRAKKAR_TANNER.getId(), () -> new InventoryOpenerMob(Icon.LEATHER.get() + " Tanner", EquipmentSet.TANNER,
                MobBehaviors.VILLAGER_MAN_1.getBehavior(), "tanner", null, InventoryId.DRAKKAR_TANNER_SHOP.asInt(), 30)
                .attachDialog(tannerDialog));

        registry.register(MobType.SWAMP_ISLAND_HERBLORE_SHOP.getId(), () -> new InventoryOpenerMob(Icon.HERBLORE.get() + " Herblore Shop", null,
                MobBehaviors.VILLAGER_WOMAN_5.getBehavior(), "swamp_villager", null, InventoryId.SWAMP_ISLAND_HERBLORE_SHOP.asInt(), 30)
                .attachDialog(genericShopDialog));


    }
}
