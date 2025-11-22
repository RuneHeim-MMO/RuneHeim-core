package org.zuma.examplePlugin.domain.mob.types;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Ocelots {

    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.OCELOT_BASE_44.getId(), () -> new GenericCombatant(
                "Ocelot", null, getBaseSkills(44, 0.5),
                null, MobBehaviors.OCELOT.getBehavior(),
                0.3, true, false, 43, null, null, DisguiseType.OCELOT,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.OCELOT.asInt()),
                true));

        registry.register(MobType.OCELOT_BASE_45.getId(), () -> new GenericCombatant(
                "Ocelot", null, getBaseSkills(45, 0.5),
                null, MobBehaviors.OCELOT.getBehavior(),
                0.3, true, false, 42, null, null, DisguiseType.OCELOT,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.OCELOT.asInt()),
                true));


        registry.register(MobType.OCELOT_BASE_48.getId(), () -> new GenericCombatant(
                "Ocelot", null, getBaseSkills(48, 0.5),
                null, MobBehaviors.OCELOT.getBehavior(),
                0.3, true, false, 40, null, null, DisguiseType.OCELOT,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.OCELOT.asInt()),
                true));


    }
}
