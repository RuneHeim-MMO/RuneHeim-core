package org.zuma.examplePlugin.core.interfaces;


import org.bukkit.Location;
import org.zuma.examplePlugin.domain.map.MarkerType;

import javax.annotation.Nullable;

public interface POIMarkerable {
    Location getLocation();

    @Nullable
    MarkerType getMarkerType();

    @Nullable
    String getMarkerLabel();

    @Nullable
    default String getDescription() {
        return null;
    }

}