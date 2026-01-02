package org.zuma.examplePlugin.domain.mob.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.mob.base.FriendlyImmortalMob;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;

public class StaticMob extends FriendlyImmortalMob {
    public StaticMob(EntityType type, boolean silent) {
        super(new FriendlyImmortalMobMeta(Component.empty(), type, silent, false));
        setMaxWalkDistance(0);
    }
}
