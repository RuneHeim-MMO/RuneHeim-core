package org.zuma.examplePlugin.domain.droptable.persistence;

import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.droptable.DropItemEntry;

import java.util.List;

public record DropTableDBEntry(int id, VersionedJson json) {
}
