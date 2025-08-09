package org.zuma.examplePlugin.domain.recipe;

import org.zuma.examplePlugin.domain.item.constants.ItemId;

public record IngredientRecord(ItemId itemId, int amount, boolean isConsumedOnSuccess,
                               boolean isConsumedOnFail) {
}
