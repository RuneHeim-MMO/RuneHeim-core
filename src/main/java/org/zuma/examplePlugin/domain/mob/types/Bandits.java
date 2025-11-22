package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.TalkingCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Bandits {
    private static final String[] banditLines = {
            "Your coin or your life!",
            "Hand over everything you've got!",
            "This forest is mine, and so are your pockets!",
            "You look like an easy target!",
            "You should've stayed home, weakling!",
            "I'll carve a memory into your back!",
            "Say goodbye to your gold... and maybe your head!",
            "For blood and coin!",
            "Time to bleed!",
            "You picked the wrong road to travel!",
            "No mercy for the foolish!",
            "Let's dance, prey!",
            "Feel the steel of my blade!"
    };


    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.BANDIT_BASE_20_1.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.LOW_LEVEL_RANDOM_GEAR, getBaseSkills(20, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 18, List.of(banditLines), "bandit_1", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_LOW_LEVEL_ARMOR.asInt())
        ));

        registry.register(MobType.BANDIT_BASE_20_2.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.LOW_LEVEL_RANDOM_GEAR, getBaseSkills(20, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 18, List.of(banditLines), "bandit_2", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_LOW_LEVEL_ARMOR.asInt())
        ));

        registry.register(MobType.BANDIT_BASE_20_3.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.LOW_LEVEL_RANDOM_GEAR, getBaseSkills(20, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 18, List.of(banditLines), "bandit_3", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_LOW_LEVEL_WEAPONS.asInt())
        ));

        registry.register(MobType.BANDIT_BASE_20_4.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.LOW_LEVEL_RANDOM_GEAR, getBaseSkills(20, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 18, List.of(banditLines), "bandit_4", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_LOW_LEVEL_WEAPONS.asInt())
        ));


        registry.register(MobType.BANDIT_BASE_40_1.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.MEDIUM_LEVEL_RANDOM_GEAR, getBaseSkills(40, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 32, List.of(banditLines), "bandit_1", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_MEDIUM_LEVEL_ARMOR.asInt())
        ));

        registry.register(MobType.BANDIT_BASE_40_2.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.MEDIUM_LEVEL_RANDOM_GEAR, getBaseSkills(40, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 32, List.of(banditLines), "bandit_2", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_MEDIUM_LEVEL_ARMOR.asInt())
        ));

        registry.register(MobType.BANDIT_BASE_40_3.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.MEDIUM_LEVEL_RANDOM_GEAR, getBaseSkills(40, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 32, List.of(banditLines), "bandit_3", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_MEDIUM_LEVEL_WEAPONS.asInt())
        ));

        registry.register(MobType.BANDIT_BASE_40_4.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.MEDIUM_LEVEL_RANDOM_GEAR, getBaseSkills(40, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 32, List.of(banditLines), "bandit_4", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_MEDIUM_LEVEL_WEAPONS.asInt())
        ));


        registry.register(MobType.BANDIT_BASE_60_1.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.HIGH_LEVEL_RANDOM_GEAR, getBaseSkills(60, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 50, List.of(banditLines), "bandit_1", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_HIGH_LEVEL_WEAPONS.asInt())
        ));

        registry.register(MobType.BANDIT_BASE_60_2.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.HIGH_LEVEL_RANDOM_GEAR, getBaseSkills(60, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 50, List.of(banditLines), "bandit_2", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_HIGH_LEVEL_WEAPONS.asInt())
        ));

        registry.register(MobType.BANDIT_BASE_60_3.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.HIGH_LEVEL_RANDOM_GEAR, getBaseSkills(60, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 50, List.of(banditLines), "bandit_3", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_HIGH_LEVEL_ARMOR.asInt())
        ));

        registry.register(MobType.BANDIT_BASE_60_4.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.HIGH_LEVEL_RANDOM_GEAR, getBaseSkills(60, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 50, List.of(banditLines), "bandit_4", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_HIGH_LEVEL_ARMOR.asInt())
        ));


        registry.register(MobType.BANDIT_BASE_80_1.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.HIGH_LEVEL_RANDOM_GEAR, getBaseSkills(80, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 65, List.of(banditLines), "bandit_1", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_HIGH_LEVEL_ARMOR.asInt())
        ));

        registry.register(MobType.BANDIT_BASE_80_2.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.HIGH_LEVEL_RANDOM_GEAR, getBaseSkills(80, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 65, List.of(banditLines), "bandit_2", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_HIGH_LEVEL_ARMOR.asInt())
        ));

        registry.register(MobType.BANDIT_BASE_80_3.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.HIGH_LEVEL_RANDOM_GEAR, getBaseSkills(80, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 65, List.of(banditLines), "bandit_3", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_HIGH_LEVEL_WEAPONS.asInt())
        ));

        registry.register(MobType.BANDIT_BASE_80_4.getId(), () -> new TalkingCombatant(
                "Bandit", EquipmentSet.HIGH_LEVEL_RANDOM_GEAR, getBaseSkills(80, 0.5),
                null, MobBehaviors.BANDIT.getBehavior(),
                0.3, 65, List.of(banditLines), "bandit_4", null, null,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_MEDIUM.asInt(), DropTableId.BANDIT_HIGH_LEVEL_WEAPONS.asInt())
        ));


    }
}

