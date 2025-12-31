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
        // Tutorial island
        registry.register(MobType.PEASANT_MINER.getId(), () -> new DialogMob("Peasant Miner", EquipmentSet.BRONZE_PICKAXE,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "peasant", null, 0, Dialogs.tutorial_peasantMinerDialog));

        registry.register(MobType.TUTOR_BLACKSMITH.getId(), () -> new DialogMob("Blacksmith", EquipmentSet.BLACKSMITH,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "tutor_blacksmith", null, 0, Dialogs.tutorial_blacksmithDialog));

        registry.register(MobType.TUTOR_LUMBERJACK.getId(), () -> new DialogMob("Lumberjack", EquipmentSet.BRONZE_AXE,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "lumberjack", null, 0, Dialogs.tutorial_lumberjack));

        registry.register(MobType.TUTOR_SURVIVAL.getId(), () -> new DialogMob("Survival Guide", EquipmentSet.RUNE_FULL_CROSSBOW,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "survival_guide", null, 0, Dialogs.tutorial_survivalGuide));

        registry.register(MobType.SHEPHERD.getId(), () -> new DialogMob("Shepherd", EquipmentSet.SHEPHERD,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "shepherd", null, 0, Dialogs.sheepShearerDialog));

        registry.register(MobType.DOCTOR_BRAM.getId(), () -> new DialogMob("Doctor Bram", EquipmentSet.DOCTOR_BRAM,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "doctor_bram", null, 20, Dialogs.doctorBramDialog));

        // The sick camels quest
        registry.register(MobType.RAFIQ_CAMEL_TRADER.getId(), () -> new DialogMob("Rafiq", null,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "rafiq_camel_trader", null, 50, Dialogs.rafiqCamelTrader));

        registry.register(MobType.SHAMIRA_SHAMAN.getId(), () -> new InventoryOpenerMob(Icon.HERBLORE.get() + " Shamira", null,
                MobBehaviors.VILLAGER_WOMAN_5.getBehavior(), "shamira_shaman", null, InventoryId.ZARALI_HERBLORE_SHOP.asInt(), 50)
                .attachDialog(Dialogs.shamiraShaman));

        // Drakkar mayor
        registry.register(MobType.DRAKKAR_MAYOR.getId(), () -> new DialogMob("Drakkar Mayor", EquipmentSet.PAPER_IN_HAND,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "drakkar_mayor", null, 0, Dialogs.drakkarMayorDialog));

    }
}
