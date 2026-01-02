package org.zuma.examplePlugin.domain.block.meta.v1;

import org.zuma.examplePlugin.domain.item.ItemRecord;

import java.util.List;

public record ButtonTriggerMeta(String groupId, String triggeredEntityId, int triggeredDurationSeconds, String title) {
}
