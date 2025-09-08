
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
import org.zuma.examplePlugin.domain.mob.impl.CombatDummy;
import org.zuma.examplePlugin.domain.mob.impl.DialogMob;
import org.zuma.examplePlugin.domain.mob.impl.FusionMaster;
import org.zuma.examplePlugin.domain.mob.impl.InventoryOpenerMob;

import java.util.List;
import java.util.function.Supplier;

public class InteractiveMobs {

    private static final Supplier<DialogBuilder> auctioneerDialog = () -> DialogBuilder.create("auctioneer-dialog")
            .passiveStep(p -> p.randomText(DialogPreset.WELCOME.getList()))
            .interactiveStep(c -> c.text("Open auction?")
                    .option("§aYes" + " §f" + Icon.TRADE.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNo", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));


    private static final Supplier<DialogBuilder> farmHorseTrader = () -> DialogBuilder.create("farm-horse-trader-dialog")
            .passiveStep(p -> p.randomText(DialogPreset.WELCOME.getList()))
            .passiveStep(b -> b.text(List.of(
                    "Looking for a trusty steed?",
                    "This brown farm horse is strong and steady."
            )))
            .passiveStep(b -> b.text(List.of(
                    "500 gold coins, fair price.",
                    "Interested?"
            )))
            .interactiveStep(c -> c.text("Buy Brown Farm Horse for 500gp?")
                    .option("§aYes" + " §f" + Icon.MONEY_BAG.get(), q -> q.followupSteps(w ->
                            w.conditionStep(e -> e.condition(ScriptedCondition.removePouchCoinsSuccessful(500))
                                    .truthyFollowUp(g -> g.passiveStep(t ->
                                                    t.action(ScriptedAction.giveItem(ItemId.BROWN_FARM_HORSE_MOUNT, 1)))
                                            .passiveStep(o -> o.text(List.of(
                                                    "Pleasure doing business!",
                                                    "Take good care of your new mount."
                                            )))
                                            .passiveStep(i -> i.text(List.of(
                                                    "Ride far and fast,",
                                                    "and she’ll grow strong with you."
                                            )))
                                    )
                                    .falsyFollowUp(y -> y.passiveStep(u -> u.text(
                                            "Looks like business isn’t your strong suit!"
                                    )))
                            )))
                    .option("§cNo", n -> n.followupSteps(d -> d.passiveStep(p -> p.text(
                            "Suit yourself. Safe travels!"
                    )))));

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.BROWN_FARMHOUSE_TRADER.getId(), () -> new DialogMob(Icon.TRADE.get() + " Farm Horse Trader", EquipmentSet.HORSE_DEALER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "farm_horse_dealer", null, 0, farmHorseTrader));

        registry.register(MobType.COMBAT_DUMMY_FACE_SOUTH.getId(), () -> new CombatDummy(0));
        registry.register(MobType.FUSION_MASTER.getId(), () -> new FusionMaster(Icon.SCROLL_RED.get() + " Fusion Master", MobBehaviors.FUSION_MASTER.getBehavior(), 0));

        registry.register(MobType.AUCTIONEER.getId(), () -> new InventoryOpenerMob(Icon.TRADE.get() + " Auctioneer", EquipmentSet.AUCTIONEER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "auctioneer", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(auctioneerDialog));
    }
}
