package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.dialog.constants.DialogPreset;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.heimscript.action.ScriptedAction;
import org.zuma.examplePlugin.domain.heimscript.condition.ScriptedCondition;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.InventoryOpenerMob;

import java.util.List;
import java.util.function.Supplier;

public class BankTellers {

    private static final Supplier<DialogBuilder> bankerDialog = () -> DialogBuilder.create("banker-dialog")
            .passiveStep(p -> p.randomText(DialogPreset.WELCOME.getList()))
            .conditionStep(s -> s.condition(ScriptedCondition.combatGreaterThanEquals(50)).condition(ScriptedCondition.combatGreaterThan(70))
                    .falsyFollowUp(g -> g.passiveStep(t -> t.text(List.of("Nope! Your level is too low", "No special treatment for you!", "Go away now, {PLAYER_NAME}"))
                            .action("give_item_{id_1700}_{amount_1}")))
                    .truthyFollowUp(g -> g.passiveStep(t -> t.text("Hey, you are so high level!")
                            .action(ScriptedAction.giveItem(ItemId.COINS, 123)))))
            .interactiveStep(c -> c.text("Open bank?")
                    .option("§aYes" + " §f" + Icon.MONEY_BAG.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNo", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));

    private static final Supplier<DialogBuilder> investmentBankerDialog = () -> DialogBuilder.create("investment-banker-dialog")
            .passiveStep(p -> p.randomText(List.of("Welcome, {PLAYER_NAME}.",
                    "Good to see you, {PLAYER_NAME}.",
                    "Ready to invest?",
                    "Greetings, {PLAYER_NAME}.",
                    "Let’s talk business.")))
            .interactiveStep(c -> c.randomText(DialogPreset.WANT_TO_SEE_STOCK.getList())
                    .option("§aSure" + " §f" + Icon.COIN_HANDFUL.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNot now", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));


    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.INVESTMENT_BANKER.getId(), () -> new InventoryOpenerMob(Icon.COIN_PILE.get() + " Investment Banker", EquipmentSet.INVESTMENT_BANKER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "investment_banker", null, InventoryId.INVESTMENT_BANKER.asInt(), 25)
                .attachDialog(investmentBankerDialog));

        registry.register(MobType.BANK_TELLER_1.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "bank_teller_1", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(bankerDialog));

        registry.register(MobType.BANK_TELLER_2.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "bank_teller_2", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(bankerDialog));

        registry.register(MobType.BANK_TELLER_3.getId(), () -> new InventoryOpenerMob(Icon.MONEY_BAG.get() + " Bank Teller", EquipmentSet.BANK_TELLER,
                MobBehaviors.VILLAGER_WOMAN_3.getBehavior(), "bank_teller_3", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(bankerDialog));


    }
}
