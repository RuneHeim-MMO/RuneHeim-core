package org.zuma.examplePlugin.domain.mob.types;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Foxes {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.FOX_BASE_8.getId(), () -> new GenericCombatant(
                "Fox", null, getBaseSkills(8),
                null, MobBehaviors.FOX.getBehavior(),
                0.3, false, false, 9, null, null, DisguiseType.FOX,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt())
        ));

        registry.register(MobType.FOX_BASE_10.getId(), () -> new GenericCombatant(
                "Fox", null, getBaseSkills(15),
                null, MobBehaviors.FOX.getBehavior(),
                0.3, false, false, 10, null, null, DisguiseType.FOX,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt())
        ));

        registry.register(MobType.FOX_BASE_22.getId(), () -> new GenericCombatant(
                "Fox", null, getBaseSkills(22),
                null, MobBehaviors.FOX.getBehavior(),
                0.3, false, false, 14, null, null, DisguiseType.FOX,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt())
        ));
    }
}

