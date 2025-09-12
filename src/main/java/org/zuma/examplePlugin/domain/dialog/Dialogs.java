package org.zuma.examplePlugin.domain.dialog;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.dialog.constants.DialogPreset;
import org.zuma.examplePlugin.domain.heimscript.action.ScriptedAction;
import org.zuma.examplePlugin.domain.heimscript.condition.ScriptedCondition;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.quest.constants.QuestId;

import java.util.List;
import java.util.function.Supplier;

public class Dialogs {

    public static final Supplier<DialogBuilder> sheepShearerDialog = () -> DialogBuilder.create("sheep-shearer-dialog")
            .passiveStep(p -> p.randomText(DialogPreset.WELCOME.getList()))
            .conditionStep(c -> c.condition(ScriptedCondition.questDialogStepActive("sheep-shearer.finish"))
                    .truthyFollowUp(g -> g.conditionStep(h -> h.condition(ScriptedCondition.removeInventoryItemSuccessful(new ItemRecord(ItemId.WOOL, 5)))
                            .truthyFollowUp(z -> z.passiveStep(l -> l.text("Hey, thanks!").action(ScriptedAction.dialogQuestStepFinish("sheep-shearer.finish"))))
                            .falsyFollowUp(x -> x.passiveStep(v -> v.text("What? Where did u lose it already?")))))
                    .falsyFollowUp(v -> v.passiveStep(b -> b.text("Ok, let's see..."))
                            .conditionStep(s -> s.condition(ScriptedCondition.questFinished(QuestId.SHEEP_SHEARER))
                                    .truthyFollowUp(g -> g.passiveStep(t -> t.text(List.of("Bro, u already finished it."))))
                                    .falsyFollowUp(g -> g.conditionStep(t -> t.condition(ScriptedCondition.questStarted(QuestId.SHEEP_SHEARER))
                                            .truthyFollowUp(q -> q.passiveStep(w -> w.text("Bro, get back to me when u get the wool")))
                                            .falsyFollowUp(e -> e.conditionStep(y -> y.condition(ScriptedCondition.questStartSuccessful(QuestId.SHEEP_SHEARER))
                                                    .truthyFollowUp(q -> q.passiveStep(w -> w.text("Godspeed!")))
                                                    .falsyFollowUp(u -> u.passiveStep(i -> i.text("Nah, ur too low level for this."))))))))));

    public static final Supplier<DialogBuilder> bankerDialog = () -> DialogBuilder.create("banker-dialog")
            .passiveStep(p -> p.randomText(DialogPreset.WELCOME.getList()))
            .conditionStep(s -> s.condition(ScriptedCondition.combatGreaterThanEquals(50)).condition(ScriptedCondition.combatGreaterThan(70))
                    .falsyFollowUp(g -> g.passiveStep(t -> t.text(List.of("Nope! Your level is too low", "No special treatment for you!", "Go away now, {PLAYER_NAME}"))
                            .action("give_item_{id_1700}_{amount_1}")))
                    .truthyFollowUp(g -> g.passiveStep(t -> t.text("Hey, you are so high level!")
                            .action(ScriptedAction.giveItem(new ItemRecord(ItemId.COINS, 123))))))
            .interactiveStep(c -> c.text("Open bank?")
                    .option("§aYes" + " §f" + Icon.MONEY_BAG.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNo", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));

    public static final Supplier<DialogBuilder> investmentBankerDialog = () -> DialogBuilder.create("investment-banker-dialog")
            .passiveStep(p -> p.randomText(List.of("Welcome, {PLAYER_NAME}.",
                    "Good to see you, {PLAYER_NAME}.",
                    "Ready to invest?",
                    "Greetings, {PLAYER_NAME}.",
                    "Let’s talk business.")))
            .interactiveStep(c -> c.randomText(DialogPreset.WANT_TO_SEE_STOCK.getList())
                    .option("§aSure" + " §f" + Icon.COIN_HANDFUL.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNot now", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));


    public static final Supplier<DialogBuilder> auctioneerDialog = () -> DialogBuilder.create("auctioneer-dialog")
            .passiveStep(p -> p.randomText(DialogPreset.WELCOME.getList()))
            .interactiveStep(c -> c.text("Open auction?")
                    .option("§aYes" + " §f" + Icon.TRADE.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNo", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));


    public static final Supplier<DialogBuilder> farmHorseTrader = () -> DialogBuilder.create("farm-horse-trader-dialog")
            .passiveStep(p -> p.randomText(DialogPreset.WELCOME.getList()))
            .passiveStep(b -> b.text(List.of(
                    "Looking for a trusty steed?",
                    "This brown farm horse is strong and steady."
            )))
            .passiveStep(b -> b.text(List.of(
                    "§6500 gold coins§f, fair price.",
                    "Interested?"
            )))
            .interactiveStep(c -> c.text("Buy Brown Farm Horse for §6500gp?")
                    .option("§aYes" + " §f" + Icon.MONEY_BAG.get(), q -> q.followupSteps(w ->
                            w.conditionStep(e -> e.condition(ScriptedCondition.removePouchCoinsSuccessful(500))
                                    .truthyFollowUp(g -> g.passiveStep(t ->
                                                    t.action(ScriptedAction.giveItem(new ItemRecord(ItemId.BROWN_FARM_HORSE_MOUNT, 1))))
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

    public static final Supplier<DialogBuilder> fairyTeacher = () -> DialogBuilder.create("fairy-teacher-dialog")
            .passiveStep(p -> p.text(java.util.List.of(
                    "Psst… hear that chime?",
                    "Not wind. Fairies.",
                    "Small. Shy. Real.",
                    "I’ve chased them all my life."
            )))
            .conditionStep(c -> c.condition(ScriptedCondition.inventoryContainsAnyOf(
                                    java.util.List.of(
                                            new ItemRecord(ItemId.FAIRY_OF_DARKNESS, 1),
                                            new ItemRecord(ItemId.FAIRY_OF_FROST, 1),
                                            new ItemRecord(ItemId.FAIRY_OF_HOPE, 1),
                                            new ItemRecord(ItemId.FAIRY_OF_LIGHT, 1),
                                            new ItemRecord(ItemId.FAIRY_OF_SILENCE, 1)
                                    )
                            ))
                            .truthyFollowUp(g -> g
                                    .passiveStep(t -> t.text(java.util.List.of(
                                            "By the stars… you’ve got one!",
                                            "A true fairy. Rare and fragile.",
                                            "I can hardly believe my eyes."
                                    )))
                                    .passiveStep(a -> a.text(java.util.List.of(
                                            "C-can I touch it?"
                                    )))
                                    .passiveStep(a -> a.text(java.util.List.of(
                                            "No, Abraham! That’s theirs!",
                                            "Fine, fine… then let me teach you."
                                    )))
                                    // --- tutorial part unchanged ---
                                    .interactiveStep(a -> a.text("What would you like to know?").loop()
                                            .option(Icon.SWORD.get() + " Training", b -> b.followupSteps(q -> q
                                                    .passiveStep(d -> d.text(java.util.List.of(
                                                            "Fairies gain XP slowly",
                                                            "When their XP bar fills, they can eat a fruit.",
                                                            "Each fruit raises their level — but only if ready."
                                                    )))
                                                    .passiveStep(d -> d.text(java.util.List.of(
                                                            "Don’t rush it. If they’re not ready, fruit won’t work.",
                                                            "Patience makes them stronger."
                                                    )))
                                            ))
                                            .option(Icon.FAIRY.get() + " Feeding", b -> b.followupSteps(q -> q
                                                    .passiveStep(d -> d.text(java.util.List.of(
                                                            "Fairies lose stamina when active.",
                                                            "If it hits zero, they stop gaining XP or giving bonuses.",
                                                            "Feed them to restore stamina up to 100."
                                                    )))
                                                    .passiveStep(d -> d.text(java.util.List.of(
                                                            "Keep them fed, and they’ll keep learning.",
                                                            "Too hungry = no progress."
                                                    )))
                                            ))
                                            .option(Icon.BLUE_GEM.get() + " Fruits", b -> b.followupSteps(q -> q
                                                    .passiveStep(d -> d.text(java.util.List.of(
                                                            "Each fairy fruit is unique.",
                                                            "When consumed by fairy, it adds a special upgrades.",
                                                            "That’s how fairies gain new powers."
                                                    )))
                                                    .passiveStep(d -> d.text(java.util.List.of(
                                                            "A maxed fairy has all its upgrades.",
                                                            "Generations raise the ceiling even higher."
                                                    )))
                                            ))
                                            .option(Icon.ERROR.get() + " Never mind")
                                    )
                            )
                            .falsyFollowUp(y -> y
                                    .passiveStep(u -> u.text(java.util.List.of(
                                            "They give real power.",
                                            "But no one believes me.",
                                            "No one else sees them."
                                    )))
                                    .passiveStep(v -> v.text(java.util.List.of(
                                            "Still… one might appear to you.",
                                            "Look at twilight. Listen for chimes.",
                                            "Belief lights the path."
                                    )))
                            )
            )

            // Parting lines
            .passiveStep(end -> end.text(java.util.List.of(
                    "Farewell, wanderer.",
                    "Eyes open at dusk."
            )));
}
