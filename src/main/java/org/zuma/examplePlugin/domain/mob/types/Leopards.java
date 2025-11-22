package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Leopards {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.LEOPARD_STANDARD_BASE_55.getId(), () -> new GenericCombatant(
                "Leopard", null,
                getBaseSkills(55, 0.5),
                null,
                MobBehaviors.LEOPARD.getBehavior(),
                "nm_leopard",
                0.55, true, false, 50,
                List.of(DropTableId.BIG_BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));

        registry.register(MobType.LEOPARD_STANDARD_BABY_BASE_35.getId(), () -> new GenericCombatant(
                "Baby Leopard", null,
                getBaseSkills(35, 0.5),
                null,
                MobBehaviors.LEOPARD.getBehavior(-0.3F),
                "nm_leopard_baby",
                0.35, false, false, 30,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));

        registry.register(MobType.LEOPARD_SNOW_BASE_60.getId(), () -> new GenericCombatant(
                "Snow Leopard", null,
                getBaseSkills(60, 0.5),
                null,
                MobBehaviors.LEOPARD.getBehavior(-0.3F),
                "nm_leopard_snow",
                0.55, true, false, 55,
                List.of(DropTableId.BIG_BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));

        registry.register(MobType.LEOPARD_SNOW_BABY_BASE_40.getId(), () -> new GenericCombatant(
                "Baby Snow Leopard", null,
                getBaseSkills(40, 0.5),
                null,
                MobBehaviors.LEOPARD.getBehavior(),
                "nm_leopard_snow_baby",
                0.35, false, false, 38,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));

    }
}

