package org.zuma.examplePlugin.domain.block.meta.v1;

import org.zuma.examplePlugin.domain.item.ItemRecord;

public record ItemSpawnBlockMeta(ItemRecord item, int respawnDurationSeconds) {
}
