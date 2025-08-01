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

        registry.register(MobType.BOAR_BROWN.getId(), () -> new GenericCombatant(
                "Brown Boar", null,
                getBaseSkills(3),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_brown",
                0.33, true, false, 8,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.BOAR_LIGHT_BROWN.getId(), () -> new GenericCombatant(
                "Light Brown Boar", null,
                getBaseSkills(3),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_light_brown",
                0.34, true, false, 8,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.BOAR_GRAY.getId(), () -> new GenericCombatant(
                "Gray Boar", null,
                getBaseSkills(3),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_gray",
                0.35, true, false, 9,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.BOAR_BROWN_ALPHA.getId(), () -> new GenericCombatant(
                "Alpha Brown Boar", null,
                getBaseSkills(7),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_brown_alpha",
                0.35, true, false, 12,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));


        registry.register(MobType.BOAR_GRAY_ALPHA.getId(), () -> new GenericCombatant(
                "Alpha Gray Boar", null,
                getBaseSkills(12),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_gray_alpha",
                0.34, true, false, 13,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.BOAR_LIGHT_BROWN_ALPHA.getId(), () -> new GenericCombatant(
                "Alpha Light Brown Boar", null,
                getBaseSkills(14),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_light_brown_alpha",
                0.32, true, false, 11,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

    }
}
