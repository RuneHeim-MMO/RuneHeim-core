package org.zuma.examplePlugin.domain.mob.types;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Armadillo {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.ARMADILLO_BASE_55.getId(), () -> new GenericCombatant(
                "Armadillo", null, getBaseSkills(55),
                null, MobBehaviors.ARMADILLO.getBehavior(),
                0.3, true, false, 37, null, null, DisguiseType.ARMADILLO,
                List.of(DropTableId.BONES.asInt())
        ));

        registry.register(MobType.ARMADILLO_NON_AGGRO_BASE_15.getId(), () -> new GenericCombatant(
                "Armadillo", null, getBaseSkills(15),
                null, MobBehaviors.ARMADILLO.getBehavior(),
                0.3, false, false, 10, null, null, DisguiseType.ARMADILLO,
                List.of(DropTableId.BONES.asInt())
        ));

    }
}

