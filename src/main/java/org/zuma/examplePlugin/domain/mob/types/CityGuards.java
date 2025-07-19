package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.base.FriendlyImmortalMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.CityGuardMob;
import org.zuma.examplePlugin.domain.mob.impl.FusionMaster;
import org.zuma.examplePlugin.domain.mob.impl.ShearableSheep;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class CityGuards {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.CITY_GUARD_CLOSE_DISTANCE.getId(), () -> new CityGuardMob("City Guard", null, null, null, 64));
        registry.register(MobType.CITY_GUARD_MEDIUM_DISTANCE.getId(), () -> new CityGuardMob("City Guard", null, null, null, 128));
        registry.register(MobType.CITY_GUARD_LONG_DISTANCE.getId(), () -> new CityGuardMob("City Guard", null, null, null, 256));

    }
}
