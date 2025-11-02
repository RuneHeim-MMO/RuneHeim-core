package org.zuma.examplePlugin.domain.mob.types;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Cows {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.COW_BASE_7.getId(), () -> new GenericCombatant(
                "Cow", null,
                getBaseSkills(7, 0.5),
                null,
                MobBehaviors.COW.getBehavior(),
                0.25,
                false, false, 8, null, null, DisguiseType.COW,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.COWHIDE.asInt()),
                true));


        registry.register(MobType.CALF_BASE_5.getId(), () -> new GenericCombatant(
                "Calf", null,
                getBaseSkills(5, 0.5),
                null,
                MobBehaviors.EAGLE.getBehavior(),
                0.25,
                false, false, 5, null, null, DisguiseType.COW,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true).setBaby(true));


    }
}
