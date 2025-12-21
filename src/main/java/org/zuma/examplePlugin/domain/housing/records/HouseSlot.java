package org.zuma.examplePlugin.domain.housing.records;

import org.bukkit.Location;

public record HouseSlot(Location location, HouseSlotType type) {
    public enum HouseSlotType {
        INTERACTIVE,
        DECORATION
    }
}
