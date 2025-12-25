package org.zuma.examplePlugin.domain.shop.persistence;

import org.zuma.examplePlugin.core.meta.VersionedJson;

public record ShopConfig(int id, VersionedJson json) {
}
