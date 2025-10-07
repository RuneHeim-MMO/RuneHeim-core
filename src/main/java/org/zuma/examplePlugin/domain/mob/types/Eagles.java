package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Eagles {

    public static void registerMobs(MobTypeRegistry registry) {


        registry.register(MobType.EAGLE_BLACK_BASE_50.getId(), () -> new GenericCombatant(
                "Eagle", null,
                getBaseSkills(50),
                null,
                MobBehaviors.EAGLE.getBehavior(),
                "brownheadeagle",
                0.40, false, false, 32,
                List.of(DropTableId.BONES.asInt(), DropTableId.FEATHERS.asInt())
        ));


        registry.register(MobType.EAGLE_BROWN_BASE_48.getId(), () -> new GenericCombatant(
                "Eagle", null,
                getBaseSkills(48),
                null,
                MobBehaviors.EAGLE.getBehavior(),
                "browneagle",
                0.40, false, false, 35,
                List.of(DropTableId.BONES.asInt(), DropTableId.FEATHERS.asInt())
        ));


        registry.register(MobType.EAGLE_BROWNHEAD_BASE_55.getId(), () -> new GenericCombatant(
                "Eagle", null,
                getBaseSkills(55),
                null,
                MobBehaviors.EAGLE.getBehavior(),
                "blackeagle",
                0.40, false, false, 32,
                List.of(DropTableId.BONES.asInt(), DropTableId.FEATHERS.asInt())
        ));


    }
}
