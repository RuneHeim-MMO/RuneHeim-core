package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.domain.dialog.Dialogs;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.DialogMob;

public class QuestMobs {


    public static void registerMobs(MobTypeRegistry registry) {


        // Tutorial island
        registry.register(MobType.PEASANT_MINER.getId(), () -> new DialogMob("Peasant Miner", EquipmentSet.BRONZE_PICKAXE,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "peasant", null, 0, Dialogs.tutorial_peasant_minerDialog));

        registry.register(MobType.TUTOR_BLACKSMITH.getId(), () -> new DialogMob("Blacksmith", EquipmentSet.BLACKSMITH,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "tutor_blacksmith", null, 0, Dialogs.tutorial_blacksmithDialog));

        registry.register(MobType.TUTOR_LUMBERJACK.getId(), () -> new DialogMob("Lumberjack", EquipmentSet.BRONZE_AXE,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "lumberjack", null, 0, Dialogs.tutorial_lumberjack));

        registry.register(MobType.TUTOR_SURVIVAL.getId(), () -> new DialogMob("Survival Guide", EquipmentSet.RUNE_FULL_CROSSBOW,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "survival_guide", null, 0, Dialogs.tutorial_survivalGuide));

        registry.register(MobType.SHEPHERD.getId(), () -> new DialogMob("Shepherd", EquipmentSet.SHEPHERD,
                MobBehaviors.VILLAGER_MAN_5.getBehavior(), "shepherd", null, 0, Dialogs.sheepShearerDialog));

    }
}
