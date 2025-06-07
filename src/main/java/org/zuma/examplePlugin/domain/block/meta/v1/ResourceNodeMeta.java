package org.zuma.examplePlugin.domain.block.meta.v1;

import javax.annotation.Nullable;

public record ResourceNodeMeta(int resourceMetaId, @Nullable String schematicName,
                               @Nullable String depletedSchematicName) {
}
