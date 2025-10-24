package org.zuma.examplePlugin.domain.shop.meta.v1;

import org.zuma.examplePlugin.domain.shop.persistence.ShopItemConfig;

import java.util.List;

public record ShopMeta(boolean buyAll, List<ShopItemConfig> stockConfiguration) {
}
