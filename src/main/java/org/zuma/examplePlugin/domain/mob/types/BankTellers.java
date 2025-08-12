package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.dialog.constants.DialogPreset;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.InventoryOpenerMob;

import java.util.List;
import java.util.function.Supplier;

public class BankTellers {

    private static final Supplier<DialogBuilder> bankerDialog = () -> DialogBuilder.create("banker-dialog")
            .passiveStep(p -> p.randomText(DialogPreset.WELCOME.getList()));

    private static final Supplier<DialogBuilder> investmentBankerDialog = () -> DialogBuilder.create("investment-banker-dialog")
            .passiveStep(p -> p.randomText(List.of("Welcome, {PLAYER_NAME}.",
                    "Good to see you, {PLAYER_NAME}.",
                    "Ready to invest?",
                    "Greetings, {PLAYER_NAME}.",
                    "Let’s talk business.")));


    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.INVESTMENT_BANKER.getId(), () -> new InventoryOpenerMob(Icon.COIN_PILE.get() + " Investment Banker", EquipmentSet.INVESTMENT_BANKER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "investment_banker", null, InventoryId.INVESTMENT_BANKER.asInt(), 25)
                .attachDialog(investmentBankerDialog, "Wanna trade?", "§aSure" + " §f" + Icon.COIN_HANDFUL.get(), "§cNo", "Bye!"));

        registry.register(MobType.BANK_TELLER_1.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "bank_teller_1", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(bankerDialog, "I’ve kept everything just as you left it.", "§aOpen bank" + " §f" + Icon.MONEY_BAG.get(), "§cCancel", "See you!"));

        registry.register(MobType.BANK_TELLER_2.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "bank_teller_2", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(bankerDialog, "Want to see your valuables?", "§aOpen bank" + " §f" + Icon.MONEY_BAG.get(), "§cCancel", "See you!"));

        registry.register(MobType.BANK_TELLER_3.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_WOMAN_3.getBehavior(), "bank_teller_3", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(bankerDialog, "Do you need to make a deposit?", "§aOpen bank" + " §f" + Icon.MONEY_BAG.get(), "§cCancel", "See you!"));


    }
}
