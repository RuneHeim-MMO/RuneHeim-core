package org.zuma.examplePlugin.domain.droptable.persistence;

import org.zuma.examplePlugin.core.meta.VersionedJson;

public record DropTableDBEntry(int id, VersionedJson json) {
}
