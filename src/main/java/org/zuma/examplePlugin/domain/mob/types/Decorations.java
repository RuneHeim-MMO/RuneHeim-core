package org.zuma.examplePlugin.domain.mob.types;

import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.StaticMob;

public class Decorations {
    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.STATIC_HORSE.getId(), () -> new StaticMob(EntityType.HORSE, false));
        registry.register(MobType.STATIC_CAMEL.getId(), () -> new StaticMob(EntityType.CAMEL, false));

    }
}
