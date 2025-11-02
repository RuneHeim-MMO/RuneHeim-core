package org.zuma.examplePlugin.domain.mob.types;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Bunnies {

    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.BUNNY_WHITE_BASE_15.getId(), () -> new GenericCombatant(
                "Bunny", null, getBaseSkills(15, 0.5),
                null, MobBehaviors.RABBIT.getBehavior(-0.4F),
                0.38, false, false, 10, null, "hollandlopwhite", null,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));

        registry.register(MobType.BUNNY_BLACK_BASE_12.getId(), () -> new GenericCombatant(
                "Bunny", null, getBaseSkills(12, 0.5),
                null, MobBehaviors.RABBIT.getBehavior(-0.4F),
                0.38, false, false, 7, null, "hollandlopblack", DisguiseType.RABBIT,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));

        registry.register(MobType.BUNNY_BROWN_BASE_14.getId(), () -> new GenericCombatant(
                "Bunny", null, getBaseSkills(14, 0.5),
                null, MobBehaviors.RABBIT.getBehavior(-0.4F),
                0.38, false, false, 8, null, "hollandlopbrown", DisguiseType.RABBIT,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));

        registry.register(MobType.BUNNY_PET_BASE_12.getId(), () -> new GenericCombatant(
                "Bunny", null, getBaseSkills(12, 0.5),
                null, MobBehaviors.RABBIT.getBehavior(-0.4F),
                0.38, false, false, 7, null, "hollandloppet", DisguiseType.RABBIT,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));

        registry.register(MobType.BUNNY_GRAY_BASE_15.getId(), () -> new GenericCombatant(
                "Bunny", null, getBaseSkills(15, 0.5),
                null, MobBehaviors.RABBIT.getBehavior(-0.4F),
                0.38, false, false, 10, null, "hollandlopgray", DisguiseType.RABBIT,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));


    }
}
