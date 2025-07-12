package org.zuma.examplePlugin.domain.mob.meta;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;

import javax.annotation.Nullable;
import java.util.List;

public record AbstractMobMeta(boolean ai, boolean silent, Component displayName,
                              EntityType baseEntity) {
}
