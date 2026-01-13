package org.zuma.examplePlugin.domain.item.meta.v1;

import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.CustomModelId;

import javax.annotation.Nullable;
import java.util.List;

public record BulkItemMeta(@Nullable List<ItemRecord> items, CustomModelId modelId) {
}
