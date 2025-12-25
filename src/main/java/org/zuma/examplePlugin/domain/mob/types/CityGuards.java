package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.CityGuardMob;

public class CityGuards {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.CITY_GUARD_STATIC.getId(), () -> new CityGuardMob("City Guard", null, null, null, 1, 0.23, 0.55));
        registry.register(MobType.CITY_GUARD_WALKING.getId(), () -> new CityGuardMob("City Guard", null, null, null, 128, 0.23, 0.55));
    }
}
