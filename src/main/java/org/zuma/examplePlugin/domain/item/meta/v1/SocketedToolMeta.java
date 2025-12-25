package org.zuma.examplePlugin.domain.item.meta.v1;

import javax.annotation.Nullable;
import java.util.List;

public record SocketedToolMeta(EquipableTypeMeta equipableTypeMeta, @Nullable List<Integer> defaultGemIds,
                               int gemSlots) {
}
