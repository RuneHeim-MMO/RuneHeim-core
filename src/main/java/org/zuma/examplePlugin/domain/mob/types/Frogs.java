package org.zuma.examplePlugin.domain.mob.types;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Frogs {

    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.FROG_BASE_25.getId(), () -> new GenericCombatant(
                "Swamp Frog", null, getBaseSkills(25),
                null, MobBehaviors.FROG.getBehavior(),
                0.3, true, false, 14, null, null, DisguiseType.FROG,
                List.of(DropTableId.BONES.asInt())
        ));

        registry.register(MobType.FROG_BASE_26.getId(), () -> new GenericCombatant(
                "Swamp Frog", null, getBaseSkills(26),
                null, MobBehaviors.FROG.getBehavior(),
                0.25, true, false, 15, null, null, DisguiseType.FROG,
                List.of(DropTableId.BONES.asInt())
        ));

        registry.register(MobType.FROG_BASE_25_NO_AGGRO.getId(), () -> new GenericCombatant(
                "Swamp Frog", null, getBaseSkills(25),
                null, MobBehaviors.FROG.getBehavior(),
                0.3, false, false, 14, null, null, DisguiseType.FROG,
                List.of(DropTableId.BONES.asInt())
        ));

        registry.register(MobType.FROG_BASE_26_NO_AGGRO.getId(), () -> new GenericCombatant(
                "Swamp Frog", null, getBaseSkills(26),
                null, MobBehaviors.FROG.getBehavior(),
                0.25, false, false, 15, null, null, DisguiseType.FROG,
                List.of(DropTableId.BONES.asInt())
        ));


    }
}
