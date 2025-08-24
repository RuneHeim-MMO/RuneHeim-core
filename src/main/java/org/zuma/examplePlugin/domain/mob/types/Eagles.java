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


        registry.register(MobType.EAGLE_BROWNHEAD_BASE_55.getId(), () -> new GenericCombatant(
                "Eagle", null,
                getBaseSkills(55),
                null,
                MobBehaviors.EAGLE.getBehavior(),
                "brownheadeagle",
                0.40, false, false, 32,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));


        registry.register(MobType.EAGLE_BROWN_BASE_59.getId(), () -> new GenericCombatant(
                "Eagle", null,
                getBaseSkills(59),
                null,
                MobBehaviors.EAGLE.getBehavior(),
                "browneagle",
                0.40, false, false, 35,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));


        registry.register(MobType.EAGLE_BLACKBASE_57.getId(), () -> new GenericCombatant(
                "Eagle", null,
                getBaseSkills(57),
                null,
                MobBehaviors.EAGLE.getBehavior(),
                "blackeagle",
                0.40, false, false, 32,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));


    }
}
