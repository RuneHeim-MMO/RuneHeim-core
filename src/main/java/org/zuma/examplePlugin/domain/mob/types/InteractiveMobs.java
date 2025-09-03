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


    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.COMBAT_DUMMY_FACE_SOUTH.getId(), () -> new CombatDummy(0));
        registry.register(MobType.FUSION_MASTER.getId(), () -> new FusionMaster(Icon.SCROLL_RED.get() + " Fusion Master", MobBehaviors.FUSION_MASTER.getBehavior(), 0));

        registry.register(MobType.AUCTIONEER.getId(), () -> new InventoryOpenerMob(Icon.TRADE.get() + " Auctioneer", EquipmentSet.AUCTIONEER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "auctioneer", null, InventoryId.BANK.asInt(), 0)
                .attachDialog(auctioneerDialog));
    }
}
