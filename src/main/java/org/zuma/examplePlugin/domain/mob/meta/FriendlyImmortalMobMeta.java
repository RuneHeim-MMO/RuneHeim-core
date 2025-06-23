package org.zuma.examplePlugin.domain.mob.meta;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;

public record FriendlyImmortalMobMeta(int typeId, Component displayName, EntityType baseEntity,
                                      boolean silent) {
}
