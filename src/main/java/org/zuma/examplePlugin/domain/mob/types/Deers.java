package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Deers {

    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.DEER_BABY_BASE_15.getId(), () -> new GenericCombatant(
                "Baby Deer", null,
                getBaseSkills(15, 0.5),
                null,
                MobBehaviors.DEER_BABY.getBehavior(),
                "babydeer",
                0.36, false, false, 8,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));

        registry.register(MobType.DEER_FEMALE_BASE_29.getId(), () -> new GenericCombatant(
                "Deer", null,
                getBaseSkills(29, 0.5),
                null,
                MobBehaviors.DEER.getBehavior(0.5F),
                "femaledeer",
                0.36, false, true, 20,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));

        registry.register(MobType.DEER_BASE_34.getId(), () -> new GenericCombatant(
                "Deer", null,
                getBaseSkills(34, 0.5),
                null,
                MobBehaviors.DEER.getBehavior(1F),
                "deer",
                0.36, false, true, 25,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.DEER_ANTLER.asInt()),
                true));


    }
}
