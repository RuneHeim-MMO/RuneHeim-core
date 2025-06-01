package org.zuma.examplePlugin.domain.item.meta;

import org.zuma.examplePlugin.domain.item.constants.CustomModelId;

public record PotionMeta(int maxUsages, int usages, String someEffect, CustomModelId potionModel) {
}
