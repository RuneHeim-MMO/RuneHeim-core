package org.zuma.examplePlugin.domain.block.meta.v1;

import org.zuma.examplePlugin.domain.item.ItemRecord;

import java.util.List;

public record ItemCollectorTriggerMeta(String groupId, String triggeredEntityId, int triggeredDurationSeconds,
                                       List<ItemRecord> requiredItems) {
}
