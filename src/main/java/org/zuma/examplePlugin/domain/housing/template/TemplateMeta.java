package org.zuma.examplePlugin.domain.housing.template;

import org.bukkit.Location;

public record TemplateMeta(int interactiveSlotCount, int decorationSlotCount, Location relativeSpawnPoint) {
}
