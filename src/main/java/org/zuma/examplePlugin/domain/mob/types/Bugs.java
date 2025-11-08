package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Bugs {
    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.CENTIPEDE_BASE_20.getId(), () -> new GenericCombatant(
                "Centipede", null,
                getBaseSkills(20, 0.5),
                null,
                MobBehaviors.CENTIPEDE.getBehavior(),
                "nm_centipede",
                0.30, false, false, 17,
                List.of(),
                true));

        registry.register(MobType.CRICKET_BROWN_BASE_5.getId(), () -> new GenericCombatant(
                "Cricket", null,
                getBaseSkills(5, 0.5),
                null,
                MobBehaviors.CRICKET.getBehavior(),
                "nm_cricket_brown",
                0.30, false, false, 5,
                List.of(),
                true));


        registry.register(MobType.CRICKET_GREEN_BASE_7.getId(), () -> new GenericCombatant(
                "Cricket", null,
                getBaseSkills(7, 0.5),
                null,
                MobBehaviors.CRICKET.getBehavior(),
                "nm_cricket_green",
                0.30, false, false, 7,
                List.of(),
                true));

        registry.register(MobType.COCKROACH_BASE_8.getId(), () -> new GenericCombatant(
                "Cockroach", null,
                getBaseSkills(8, 0.5),
                null,
                MobBehaviors.COCKROACH.getBehavior(),
                "nm_cockroach",
                0.30, false, false, 8,
                List.of(),
                true));

        registry.register(MobType.ANT_BLACK_BASE_5.getId(), () -> new GenericCombatant(
                "Ant", null,
                getBaseSkills(5, 0.5),
                null,
                MobBehaviors.ANT.getBehavior(),
                "nm_ant_black",
                0.30, false, true, 2,
                List.of(),
                true));


        registry.register(MobType.MANTIS_BASE_25.getId(), () -> new GenericCombatant(
                "Mantis", null,
                getBaseSkills(25, 0.5),
                null,
                MobBehaviors.MANTIS.getBehavior(),
                "nm_mantis",
                0.30, false, false, 20,
                List.of(),
                true));


        registry.register(MobType.SCORPION_POISONOUS_DESERT_BASE_35.getId(), () -> new GenericCombatant(
                "Poisonous Scorpion", null,
                getBaseSkills(35, 0.5),
                null,
                MobBehaviors.SCORPION.getBehavior(),
                "nm_scorpion_desert",
                0.30, true, false, 25,
                List.of(),
                true));

        registry.register(MobType.SCORPION_BASE_30.getId(), () -> new GenericCombatant(
                "Scorpion", null,
                getBaseSkills(30, 0.5),
                null,
                MobBehaviors.SCORPION.getBehavior(),
                "nm_scorpion_black",
                0.30, true, false, 22,
                List.of(),
                true));


    }
}
