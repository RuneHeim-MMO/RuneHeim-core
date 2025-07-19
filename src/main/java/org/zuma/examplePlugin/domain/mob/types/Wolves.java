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

        registry.register(MobType.WOLF_BLACK.getId(), () -> new GenericCombatant(
                "Black Wolf", null,
                getBaseSkills(7),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_black",
                0.32, true, false, 8,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.WOLF_BLONDE.getId(), () -> new GenericCombatant(
                "Blonde Wolf", null,
                getBaseSkills(7),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_blonde",
                0.31, true, false, 8,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));


        registry.register(MobType.WOLF_GRAY_DARK.getId(), () -> new GenericCombatant(
                "Dark Gray Wolf", null,
                getBaseSkills(7),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_gray_dark",
                0.30, true, false, 9,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.WOLF_GRAY_LIGHT.getId(), () -> new GenericCombatant(
                "Light Gray Wolf", null,
                getBaseSkills(7),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_gray_light",
                0.30, true, false, 9,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.WOLF_BLONDE_ALPHA.getId(), () -> new GenericCombatant(
                "Alpha Blonde Wolf", null,
                getBaseSkills(15),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_blonde_alpha",
                0.33, true, false, 11,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.WOLF_GRAY_DARK_ALPHA.getId(), () -> new GenericCombatant(
                "Alpha Dark Gray Wolf", null,
                getBaseSkills(15),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_gray_dark_alpha",
                0.33, true, false, 12,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.WOLF_GRAY_LIGHT_ALPHA.getId(), () -> new GenericCombatant(
                "Alpha Light Gray Wolf", null,
                getBaseSkills(15),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_gray_light_alpha",
                0.32, true, false, 12,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.WOLF_BLACK_ALPHA.getId(), () -> new GenericCombatant(
                "Alpha Black Wolf", null,
                getBaseSkills(15),
                null,
                MobBehaviors.WOLF.getBehavior(),
                "yungwilder_isles_wolf_black_alpha",
                0.34, true, false, 11,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));


    }
}
