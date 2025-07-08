package org.zuma.examplePlugin.domain.item.meta.v1;

import org.zuma.examplePlugin.domain.item.impl.EmptyContainer;

import java.util.List;

public record EmptyContainerMeta(List<EmptyContainer.EmptyContainerConfig> config) {
}
