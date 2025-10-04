package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Wolves {

    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.WOLF_BLACK_BASE_20.getId(), () -> new GenericCombatant(
                "Black Wolf", null,
                getBaseSkills(20),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_black",
                0.32, true, false, 20,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.WOLF_BLONDE_BASE_22.getId(), () -> new GenericCombatant(
                "Blonde Wolf", null,
                getBaseSkills(22),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_blonde",
                0.31, true, false, 22,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));


        registry.register(MobType.WOLF_GRAY_DARK_BASE_24.getId(), () -> new GenericCombatant(
                "Dark Gray Wolf", null,
                getBaseSkills(24),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_gray_dark",
                0.30, true, false, 24,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.WOLF_GRAY_LIGHT_BASE_26.getId(), () -> new GenericCombatant(
                "Light Gray Wolf", null,
                getBaseSkills(26),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_gray_light",
                0.30, true, false, 26,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.WOLF_BLONDE_ALPHA_BASE_30.getId(), () -> new GenericCombatant(
                "Alpha Blonde Wolf", null,
                getBaseSkills(30),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_blonde_alpha",
                0.33, true, false, 30,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.WOLF_GRAY_DARK_ALPHA_BASE_32.getId(), () -> new GenericCombatant(
                "Alpha Dark Gray Wolf", null,
                getBaseSkills(32),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_gray_dark_alpha",
                0.33, true, false, 30,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34.getId(), () -> new GenericCombatant(
                "Alpha Light Gray Wolf", null,
                getBaseSkills(34),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_gray_light_alpha",
                0.32, true, false, 30,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.WOLF_BLACK_ALPHA_BASE_36.getId(), () -> new GenericCombatant(
                "Alpha Black Wolf", null,
                getBaseSkills(36),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_black_alpha",
                0.34, true, false, 30,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));


        registry.register(MobType.TUNDRA_WOLF_BASE_45.getId(), () -> new GenericCombatant(
                "Tundra Wolf", null,
                getBaseSkills(45),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_gray_dark_alpha",
                0.33, true, false, 40,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.TUNDRA_WOLF_BASE_48.getId(), () -> new GenericCombatant(
                "Tundra Wolf", null,
                getBaseSkills(48),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_gray_light_alpha",
                0.32, true, false, 43,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.TUNDRA_WOLF_BASE_47.getId(), () -> new GenericCombatant(
                "Tundra Wolf", null,
                getBaseSkills(47),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_blonde_alpha",
                0.34, true, false, 42,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));


    }
}
