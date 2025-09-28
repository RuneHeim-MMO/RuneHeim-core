
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

        registry.register(MobType.SAILOR_ROGUES_HAVEN.getId(), () -> new DialogMob(Icon.ANCHOR.get() + " Sailor", null,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "sailor", null, 0, Dialogs.sailorBufferDialog));

        registry.register(MobType.SAILOR_BUFFER.getId(), () -> new DialogMob(Icon.ANCHOR.get() + " Sailor", null,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "sailor", null, 40, Dialogs.sailorBufferDialog));


    }
}
