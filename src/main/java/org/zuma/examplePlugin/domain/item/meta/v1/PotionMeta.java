package org.zuma.examplePlugin.domain.item.meta.v1;

import org.zuma.examplePlugin.domain.item.StatsEffect;
import org.zuma.examplePlugin.domain.item.constants.CustomModelId;

public record PotionMeta(int maxUsages, int usages, StatsEffect effect, CustomModelId potionModel) {
}
