package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Boars {

    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.WILD_PIG_BASE_48.getId(), () -> new GenericCombatant(
                "Wild Pig", null,
                getBaseSkills(48, 0.5),
                null,
                MobBehaviors.WILD_PIG.getBehavior(),
                "boar_generic",
                0.30, false, false, 34,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.BOAR.asInt()),
                true));

        registry.register(MobType.WILD_PIG_BASE_45.getId(), () -> new GenericCombatant(
                "Wild Pig", null,
                getBaseSkills(45, 0.5),
                null,
                MobBehaviors.WILD_PIG.getBehavior(0.5F),
                "boar_female",
                0.30, true, false, 30,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.BOAR.asInt()),
                true));

        registry.register(MobType.WILD_PIG_BABY_BASE_40.getId(), () -> new GenericCombatant(
                "Wild Pig Baby", null,
                getBaseSkills(40, 0.5),
                null,
                MobBehaviors.WILD_PIG.getBehavior(1F),
                "boar_baby",
                0.30, false, false, 18,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.BOAR.asInt()),
                true));

        registry.register(MobType.BOAR_BROWN_BASE_3.getId(), () -> new GenericCombatant(
                "Brown Boar", null,
                getBaseSkills(3, 0.5),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_brown",
                0.33, true, false, 8,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.BOAR.asInt()),
                true));

        registry.register(MobType.BOAR_LIGHT_BROWN_BASE_3.getId(), () -> new GenericCombatant(
                "Light Brown Boar", null,
                getBaseSkills(3, 0.5),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_light_brown",
                0.34, true, false, 8,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.BOAR.asInt()),
                true));

        registry.register(MobType.BOAR_GRAY_BASE_3.getId(), () -> new GenericCombatant(
                "Gray Boar", null,
                getBaseSkills(3, 0.5),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_gray",
                0.35, true, false, 9,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.BOAR.asInt()),
                true));

        registry.register(MobType.BOAR_BROWN_ALPHA_BASE10.getId(), () -> new GenericCombatant(
                "Alpha Brown Boar", null,
                getBaseSkills(10, 0.5),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_brown_alpha",
                0.35, true, false, 12,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.BOAR.asInt()),
                true));


        registry.register(MobType.BOAR_GRAY_ALPHA_BASE_12.getId(), () -> new GenericCombatant(
                "Alpha Gray Boar", null,
                getBaseSkills(12, 0.5),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_gray_alpha",
                0.34, true, false, 13,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.BOAR.asInt()),
                true));

        registry.register(MobType.BOAR_LIGHT_BROWN_ALPHA_BASE_14.getId(), () -> new GenericCombatant(
                "Alpha Light Brown Boar", null,
                getBaseSkills(14, 0.5),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_light_brown_alpha",
                0.32, true, false, 11,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.BOAR.asInt()),
                true));

    }
}
