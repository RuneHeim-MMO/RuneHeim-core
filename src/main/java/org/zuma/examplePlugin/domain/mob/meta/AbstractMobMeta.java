package org.zuma.examplePlugin.domain.mob.meta;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;

public record AbstractMobMeta(boolean ai, boolean silent, boolean hideName, Component displayName,
                              EntityType baseEntity) {
}
