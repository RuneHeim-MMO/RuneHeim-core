package org.zuma.examplePlugin.domain.mob.types;

import org.bukkit.entity.Item;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.dialog.Dialogs;
import org.zuma.examplePlugin.domain.dialog.constants.DialogPreset;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.heimscript.action.ScriptedAction;
import org.zuma.examplePlugin.domain.heimscript.condition.ScriptedCondition;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.InventoryOpenerMob;

import java.util.List;
import java.util.function.Supplier;

public class BankTellers {


    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.INVESTMENT_BANKER.getId(), () -> new InventoryOpenerMob(Icon.COIN_PILE.get() + " Investment Banker", EquipmentSet.INVESTMENT_BANKER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "investment_banker", null, InventoryId.INVESTMENT_BANKER.asInt(), 25)
                .attachDialog(Dialogs.investmentBankerDialog));

        registry.register(MobType.BANK_TELLER_1.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "bank_teller_1", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_2.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "bank_teller_2", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_3.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_WOMAN_3.getBehavior(), "bank_teller_3", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_4.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_1.getBehavior(), "bank_teller_4", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_5.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "bank_teller_5", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_6.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "bank_teller_6", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_7.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "bank_teller_7", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_8.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "bank_teller_8", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

        registry.register(MobType.BANK_TELLER_9.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "bank_teller_9", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(Dialogs.bankerDialog));

    }
}
