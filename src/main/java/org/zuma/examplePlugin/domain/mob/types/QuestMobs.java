package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.dialog.Dialogs;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.DialogMob;
import org.zuma.examplePlugin.domain.mob.impl.InventoryOpenerMob;

public class QuestMobs {


    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.SHEPHERD.getId(), () -> new DialogMob("Shepherd", EquipmentSet.SHEPHERD,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "shepherd", null, 0, Dialogs.sheepShearerDialog));
    }
}
