package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Moles {

    public static void registerMobs(MobTypeRegistry registry) {


        registry.register(MobType.MOLE_BASE_50.getId(), () -> new GenericCombatant(
                "Mole", null,
                getBaseSkills(50),
                null,
                MobBehaviors.MOLE.getBehavior(),
                "mole",
                0.2, false, false, 25,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));


        registry.register(MobType.MOLE_BASE_70.getId(), () -> new GenericCombatant(
                "Mole", null,
                getBaseSkills(70),
                null,
                MobBehaviors.MOLE.getBehavior(),
                "mole",
                0.2, false, false, 35,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));


        registry.register(MobType.MOLE_BASE_30.getId(), () -> new GenericCombatant(
                "Mole", null,
                getBaseSkills(30),
                null,
                MobBehaviors.MOLE.getBehavior(),
                "mole",
                0.2, false, false, 15,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));


    }
}
