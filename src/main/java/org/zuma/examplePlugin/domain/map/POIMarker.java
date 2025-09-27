package org.zuma.examplePlugin.domain.map;

import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.core.interfaces.POIMarkerable;

public class POIMarker implements POIMarkerable {
    private final Location location;
    private final @Nullable MarkerType markerType;
    private final @Nullable String markerLabel;

    public POIMarker(Location location, @Nullable MarkerType markerType, @Nullable String markerLabel) {
        this.location = location.clone();
        this.markerType = markerType;
        this.markerLabel = markerLabel;
    }

    @Override
    public Location getLocation() {
        return location.clone();
    }

    @Override
    public @Nullable MarkerType getMarkerType() {
        return markerType;
    }

    @Override
    public @Nullable String getMarkerLabel() {
        return markerLabel;
    }
}
