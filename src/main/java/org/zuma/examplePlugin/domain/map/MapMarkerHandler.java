package org.zuma.examplePlugin.domain.map;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.POIMarker;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.BlockManager;

import java.util.Arrays;
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

            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + markerSets.values().toArray().length + " marker sets for BlueMap.");
        });
    }

    public void initResourceNodes(BlockManager blockManager) {
        blockManager.getAllBlocks().stream().filter(block -> block.getMarkerType() != null).forEach(block -> {
            MarkerType markerType = block.getMarkerType();

            POIMarker marker = POIMarker.builder()
                    .label(block.getMarkerLabel())
                    .icon(markerType.getIconURL(), 24, 24)
                    .position(block.getLocation().x(), block.getLocation().y(), block.getLocation().z())
                    .maxDistance(500)
                    .build();


            MarkerSet set = markerSets.get(markerType);

            if (set != null) {
                set.getMarkers()
                        .put(UUID.randomUUID().toString(), marker);
            }

        });


    }


}
