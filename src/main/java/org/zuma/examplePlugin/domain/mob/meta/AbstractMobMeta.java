package org.zuma.examplePlugin.domain.mob.meta;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;

public record AbstractMobMeta(boolean ai, boolean silent, Component displayName,
                              EntityType baseEntity) {
}
