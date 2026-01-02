package org.zuma.examplePlugin.domain.mob.meta;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;

public record FriendlyImmortalMobMeta(Component displayName, EntityType baseEntity,
                                      boolean silent, boolean hideName) {
}
