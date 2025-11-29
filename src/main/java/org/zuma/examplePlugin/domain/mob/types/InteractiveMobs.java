
package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.dialog.Dialogs;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.CombatDummy;
import org.zuma.examplePlugin.domain.mob.impl.DialogMob;
import org.zuma.examplePlugin.domain.mob.impl.FusionMaster;
import org.zuma.examplePlugin.domain.mob.impl.InventoryOpenerMob;

public class InteractiveMobs {


    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.COLLECTOR_OF_DAYS.getId(), () -> new InventoryOpenerMob(Icon.TREASURE_CHEST.get() + " Collector of Days", EquipmentSet.COLLECTOR_OF_DAYS,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "collector_of_days", null, InventoryId.DAILY_REWARDS.asInt(), 0)
                .attachDialog(Dialogs.collectorOfDaysDialog));

        registry.register(MobType.INVESTMENT_BANKER.getId(), () -> new InventoryOpenerMob(Icon.COIN_PILE.get() + " Investment Banker", EquipmentSet.INVESTMENT_BANKER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "investment_banker", null, InventoryId.INVESTMENT_BANKER.asInt(), 25)
                .attachDialog(Dialogs.investmentBankerDialog));

        registry.register(MobType.DRAKKAR_BANK_TELLER_1.getId(), () -> new InventoryOpenerMob(Icon.DOLLAR.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "bank_teller_1", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.drakkarBankerDialog));

        registry.register(MobType.DRAKKAR_BANK_TELLER_2.getId(), () -> new InventoryOpenerMob(Icon.DOLLAR.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "bank_teller_2", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.drakkarBankerDialog));

        registry.register(MobType.DRAKKAR_BANK_TELLER_3.getId(), () -> new InventoryOpenerMob(Icon.DOLLAR.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_WOMAN_3.getBehavior(), "bank_teller_3", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.drakkarBankerDialog));

        registry.register(MobType.BANK_TELLER_1.getId(), () -> new InventoryOpenerMob(Icon.DOLLAR.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "bank_teller_1", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_2.getId(), () -> new InventoryOpenerMob(Icon.DOLLAR.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "bank_teller_2", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_3.getId(), () -> new InventoryOpenerMob(Icon.DOLLAR.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_WOMAN_3.getBehavior(), "bank_teller_3", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_4.getId(), () -> new InventoryOpenerMob(Icon.DOLLAR.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_1.getBehavior(), "bank_teller_4", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_5.getId(), () -> new InventoryOpenerMob(Icon.DOLLAR.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "bank_teller_5", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_6.getId(), () -> new InventoryOpenerMob(Icon.DOLLAR.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "bank_teller_6", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_7.getId(), () -> new InventoryOpenerMob(Icon.DOLLAR.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "bank_teller_7", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_8.getId(), () -> new InventoryOpenerMob(Icon.DOLLAR.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "bank_teller_8", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_9.getId(), () -> new InventoryOpenerMob(Icon.DOLLAR.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "bank_teller_9", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));


        registry.register(MobType.ABRAHAM_FAIRY_TEACHER.getId(), () -> new DialogMob("Abraham", null,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "abraham_fairy_teacher", null, 0, Dialogs.fairyTeacher));

        registry.register(MobType.BROWN_FARMHOUSE_MOUNT_SHOP.getId(), () -> new DialogMob(Icon.TRADE.get() + " Farm Horse Trader", EquipmentSet.HORSE_DEALER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "farm_horse_dealer", null, 0, Dialogs.farmHorseTrader));

        registry.register(MobType.COMBAT_DUMMY_FACE_SOUTH.getId(), () -> new CombatDummy(0));
        registry.register(MobType.FUSION_MASTER.getId(), () -> new FusionMaster(Icon.SCROLL_RED.get() + " Fusion Master", MobBehaviors.FUSION_MASTER.getBehavior(), 0));

        registry.register(MobType.AUCTIONEER.getId(), () -> new InventoryOpenerMob(Icon.TRADE.get() + " Auctioneer", EquipmentSet.AUCTIONEER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "auctioneer", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.auctioneerDialog));

        registry.register(MobType.DRAKKAR_COACHMAN.getId(), () -> new DialogMob(Icon.BOOTS.get() + " Coachman", EquipmentSet.CARROT,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "coachman", null, 0, Dialogs.drakkarCoachmanDialog));

        registry.register(MobType.SAHARIJIN_ARENA_NPC.getId(), () -> new DialogMob(Icon.CROSSED_SWORDS.get() + " Arena Overseer", null,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "saharijin_arena_overseer", null, 0, Dialogs.saharijinArenaGuide));

        registry.register(MobType.ROGUES_HAVEN_SAILOR.getId(), () -> new DialogMob(Icon.ANCHOR.get() + " Sailor", EquipmentSet.COMPASS,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "sailor", null, 0, Dialogs.sailorRoguesHaven));

        registry.register(MobType.ISLENOR_SAILOR.getId(), () -> new DialogMob(Icon.ANCHOR.get() + " Sailor", EquipmentSet.COMPASS,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "sailor", null, 0, Dialogs.sailorIslenorDialog));

        registry.register(MobType.MURKWATCH_SAILOR.getId(), () -> new DialogMob(Icon.ANCHOR.get() + " Sailor", EquipmentSet.COMPASS,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "sailor", null, 0, Dialogs.sailorMurkwatchDialog));


        registry.register(MobType.SAILOR_BUFFER.getId(), () -> new DialogMob(Icon.ANCHOR.get() + " Sailor", EquipmentSet.COMPASS,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "sailor", null, 40, Dialogs.sailorBufferDialog));


    }
}
