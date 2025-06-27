package org.zuma.examplePlugin.domain.item.meta.v1;

import javax.annotation.Nullable;
import java.util.List;

public record GearWithSocketsMeta(EquipableMeta equipableMeta, @Nullable List<Integer> defaultGemIds, int gemSlots) {
}
