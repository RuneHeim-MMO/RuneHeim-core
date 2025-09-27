package org.zuma.examplePlugin.domain.map;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

public class StaticMarkers {

    public static List<POIMarker> getStaticMarkers() {
        return List.of(
                //  new POIMarker(new Location(Bukkit.getWorld("world"), 100, 64, 100), MarkerType.FLAX, "Town Center")
        );
    }
}
