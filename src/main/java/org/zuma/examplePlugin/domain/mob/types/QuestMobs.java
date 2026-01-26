package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.dialog.Dialogs;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.DialogMob;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;
import org.zuma.examplePlugin.domain.mob.impl.InventoryOpenerMob;
import org.zuma.examplePlugin.domain.mob.impl.ToggleableCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

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

        // bones of leviathan quest
        registry.register(MobType.MORVANE_PRIEST.getId(), () -> new ToggleableCombatant("Morvane", EquipmentSet.MORVANE_PRIEST,
                getBaseSkills(45, 0.5), null, MobBehaviors.MORVANE.getBehavior(), "morvane",
                0.25, 60, List.of(), false, Dialogs.movanePriest, 100, 1500));

        registry.register(MobType.MARLINUS_HERMIT.getId(), () -> new DialogMob("Marlinus the Hermit", null,
                MobBehaviors.VILLAGER_MAN_2.getBehavior(), "marlinus", null, 30, Dialogs.marlinusTheHermit));

        registry.register(MobType.GHOSTLING.getId(), () -> new DialogMob("Ghost", null,
                MobBehaviors.GHOST.getBehavior(), null, "ghostling", 100, Dialogs.ghostlingDialog));

        // Saltbound sleep quest
        registry.register(MobType.SAILORS_SPIRIT.getId(), () -> new DialogMob("Sailors Spirit", null,
                MobBehaviors.GHOST.getBehavior(), null, "ghostling", 50, Dialogs.sailorGhost));

        registry.register(MobType.UNDEAD_SAILOR_BASE_40.getId(), () -> new GenericCombatant(
                "Undead Sailor", null, getBaseSkills(40, 0.5),
                null, MobBehaviors.RAVAGER.getBehavior(),
                0.30, false, true, 40, "undead_sailor", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt()),
                false).setMaxWalkDistance(500));

        registry.register(MobType.UNDEAD_CAPTAIN_BASE_50.getId(), () -> new ToggleableCombatant("Undead Captain", EquipmentSet.RUNE_SWORD,
                getBaseSkills(50, 0.5), null, MobBehaviors.MORVANE.getBehavior(), "undead_captain",
                0.32, 80, List.of(DropTableId.BIG_BONES.asInt()), false, Dialogs.ghostCaptain, 10, 1500));

    }
}
