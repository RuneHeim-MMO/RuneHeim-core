package org.zuma.examplePlugin.domain.item.persistence;

import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

import javax.annotation.Nullable;

public record ItemEntity(AbstractItemMeta meta, @Nullable VersionedJson json) {
}
