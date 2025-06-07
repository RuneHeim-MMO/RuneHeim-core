package org.zuma.examplePlugin.domain.item.persistence;

import org.zuma.examplePlugin.core.meta.JsonMeta;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

import javax.annotation.Nullable;

public record ItemEntity(AbstractItemMeta meta, @Nullable JsonMeta json) {
}
