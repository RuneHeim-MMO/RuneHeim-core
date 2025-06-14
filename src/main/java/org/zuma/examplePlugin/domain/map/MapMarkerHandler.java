package org.zuma.examplePlugin.domain.map;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.POIMarker;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.POIMarkerable;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class MapMarkerHandler {
    private final HashMap<MarkerType, MarkerSet> markerSets = new HashMap<>();

    public MapMarkerHandler() {
    }


    public void initializeMarkerSets() {
        BlueMapAPI.onEnable(api -> {
            markerSets.clear();

            Arrays.stream(MarkerType.values()).forEach(markerType -> {
                MarkerSet markerSet = MarkerSet.builder()
                        .label(markerType.getLabel())
                        .build();

                markerSets.put(markerType, markerSet);
            });


            api.getWorld(Bukkit.getWorld("world")).ifPresent(world -> {
                for (BlueMapMap map : world.getMaps()) {
                    markerSets.forEach((key, value) -> {
                        map.getMarkerSets().put(key.getId(), value);
                    });
                }
            });

            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + markerSets.size() + " marker sets for BlueMap.");
        });
    }

    public void renderMarkers(Collection<POIMarkerable> entities) {
        entities.stream().filter(entity -> entity.getMarkerType() != null).forEach(entity -> {
            MarkerType markerType = entity.getMarkerType();

            POIMarker marker = POIMarker.builder()
                    .icon(markerType.getIconURL(), 24, 24)
                    .position(entity.getLocation().x(), entity.getLocation().y(), entity.getLocation().z())
                    .maxDistance(500)
                    .label(entity.getMarkerLabel() != null ? entity.getMarkerLabel() : markerType.getLabel())
                    .build();


            MarkerSet set = markerSets.get(markerType);

            if (set != null) {
                set.getMarkers()
                        .put(UUID.randomUUID().toString(), marker);
            } else {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "MarkerSet for " + markerType.getLabel() + " is missing");
            }

        });


    }


}
