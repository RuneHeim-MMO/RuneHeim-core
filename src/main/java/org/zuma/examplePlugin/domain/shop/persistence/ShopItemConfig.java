package org.zuma.examplePlugin.domain.shop.persistence;

import javax.annotation.Nullable;

public record ShopItemConfig(int itemId,
                             int defaultStock,
                             @Nullable Integer stockReplenishmentSeconds, // null if not replenished
                             @Nullable Long buyPrice,// if buy price is null, the item is not visible in the gui
                             @Nullable Long sellPrice) {
}
