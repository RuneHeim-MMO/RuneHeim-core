package org.zuma.examplePlugin.domain.recipes;

import org.zuma.examplePlugin.domain.item.constants.ItemId;

public record IngredientRecord(ItemId itemId, int amount, boolean isConsumedOnCraft) {
}
