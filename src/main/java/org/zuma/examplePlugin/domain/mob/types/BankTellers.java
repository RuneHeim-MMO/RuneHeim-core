package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.InventoryOpenerMob;

import java.util.function.Supplier;

public class BankTellers {

    private static final Supplier<DialogBuilder> bankerDialog = () -> DialogBuilder.create("banker-dialog")
            .passiveStep(p -> p.text("Hello, {PLAYER_NAME}!"));

    private static final Supplier<DialogBuilder> investmentBankerDialog = () -> DialogBuilder.create("investment-banker-dialog")
            .passiveStep(p -> p.text("Tulip prices jumped 293% in just two days!"));


    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.INVESTMENT_BANKER.getId(), () -> new InventoryOpenerMob(Icon.COIN_PILE.get() + " Investment Banker", EquipmentSet.INVESTMENT_BANKER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "investment_banker", null, InventoryId.INVESTMENT_BANKER.asInt(), 25)
                .attachDialog(investmentBankerDialog, "Wanna trade?", "§aSure", "§cNo", "Bye!"));

        registry.register(MobType.BANK_TELLER_1.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "bank_teller_1", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(bankerDialog, "I’ve kept everything just as you left it.", "§aOpen bank", "§cCancel", "See you!"));

        registry.register(MobType.BANK_TELLER_2.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "bank_teller_2", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(bankerDialog, "Your valuables are safe and sound. Want to see them?", "§aOpen bank", "§cCancel", "See you!"));

        registry.register(MobType.BANK_TELLER_3.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_WOMAN_3.getBehavior(), "bank_teller_3", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(bankerDialog, "Do you need to make a deposit or withdrawal?", "§aOpen bank", "§cCancel", "See you!"));


    }
}
