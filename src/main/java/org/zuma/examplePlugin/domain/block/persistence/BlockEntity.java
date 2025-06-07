package org.zuma.examplePlugin.domain.block.persistence;

import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;

public record BlockEntity(AbstractInteractableBlockMeta meta, VersionedJson json) {
}
