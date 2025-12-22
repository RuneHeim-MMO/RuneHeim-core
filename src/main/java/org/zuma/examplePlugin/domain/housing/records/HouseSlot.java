package org.zuma.examplePlugin.domain.housing.records;

import org.bukkit.Location;
import org.zuma.examplePlugin.core.constants.Icon;

public record HouseSlot(Location location, HouseSlotType type) {
    public enum HouseSlotType {
        INTERACTIVE(Icon.GEAR.get()),
        DECORATION(Icon.FLOWER.get());

        private final String displayName;

        HouseSlotType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
