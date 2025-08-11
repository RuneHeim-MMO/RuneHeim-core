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

public class Shopkeepers {

    private static final Supplier<DialogBuilder> generalStoreDialog = () -> DialogBuilder.create("general-store-dialog")
            .passiveStep(p -> p.text("Hey, {PLAYER_NAME}!"));

    private static final Supplier<DialogBuilder> blacksmithDialog = () -> DialogBuilder.create("blacksmith-dialog")
            .passiveStep(p -> p.text("Hi!"));

    private static final Supplier<DialogBuilder> jewlerDialog = () -> DialogBuilder.create("jeweler-dialog")
            .passiveStep(p -> p.text("Have you seen my sapphire?"));


    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.GENERAL_STORE.getId(), () -> new InventoryOpenerMob(Icon.CHEST.get() + " General Store", EquipmentSet.SHOPKEEPER_1,
                MobBehaviors.VILLAGER_MAN_1.getBehavior(), "shopkeeper", null, InventoryId.GENERAL_STORE.asInt(), 40)
                .attachDialog(generalStoreDialog, "Want to see what's in stock?", "§aYes" + " §f" + Icon.COIN_HANDFUL.get(), "§cNo", "See you!"));

        registry.register(MobType.DRAKKAR_BLACKSMITH.getId(), () -> new InventoryOpenerMob(Icon.TOOL.get() + " Blacksmith", EquipmentSet.BLACKSMITH,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "blacksmith", null, InventoryId.DRAKKAR_BLACKSMITH.asInt(), 40)
                .attachDialog(blacksmithDialog, "Wanna trade?", "§aSure" + " §f" + Icon.COIN_HANDFUL.get(), "§cNo", "Oh ok."));

        registry.register(MobType.JEWLER.getId(), () -> new InventoryOpenerMob(Icon.BLUE_GEM.get() + " Jeweler", EquipmentSet.JEWELER,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "jeweler", null, InventoryId.JEWELER.asInt(), 40)
                .attachDialog(jewlerDialog, "Where did I put it?", "§aOpen shop" + " §f" + Icon.COIN_HANDFUL.get(), "§cNo", "Bye!"));


    }
}
