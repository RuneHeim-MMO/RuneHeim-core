package org.zuma.examplePlugin.domain.shop.persistence;

import org.zuma.examplePlugin.core.meta.VersionedJson;

import java.util.List;

public record ShopConfig(int id, VersionedJson json) {
}
