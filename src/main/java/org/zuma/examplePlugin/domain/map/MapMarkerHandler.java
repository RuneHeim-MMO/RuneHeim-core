package org.zuma.examplePlugin.domain.map;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.markers.ExtrudeMarker;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.POIMarker;
import de.bluecolored.bluemap.api.markers.ShapeMarker;
import de.bluecolored.bluemap.api.math.Color;
import de.bluecolored.bluemap.api.math.Shape;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.POIMarkerable;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.MinecraftColorConverter;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.territory.Territory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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
        AtomicInteger count = new AtomicInteger();
        entities.stream().filter(entity -> entity.getMarkerType() != null).forEach(entity -> {
            MarkerType markerType = entity.getMarkerType();

            POIMarker marker = POIMarker.builder()
                    .icon(markerType.getIconURL(), 24, 24)
                    .position(entity.getLocation().x(), entity.getLocation().y(), entity.getLocation().z())
                    .maxDistance(1500)
                    .label(entity.getMarkerLabel() != null ? entity.getMarkerLabel() : markerType.getLabel())
                    .build();


            MarkerSet set = markerSets.get(markerType);

            if (set != null) {
                set.getMarkers()
                        .put(UUID.randomUUID().toString(), marker);
                count.getAndIncrement();
            } else {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "MarkerSet for " + markerType.getLabel() + " is missing");
            }

        });


        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + count.get() + " POI markers for BlueMap.");
    }

    public void renderAreas(Collection<Territory> entities) {
        AtomicInteger count = new AtomicInteger();

        entities.forEach(entity -> {
            MarkerType markerType = MarkerType.AREAS;
            Shape circleShape = MapShapeGenerator.generateCircleShapeAsShape((int) entity.getCenterPoint().x(), (int) entity.getCenterPoint().z(), entity.getRadius(), 20);

            String label = entity.getName().replaceAll("§.", "");
            String avgLevelColorCode = TextUtils.getLegacyColorCodeForLevel(entity.getMinLevel() + entity.getMaxLevel() / 2);
            Color lineColor = MinecraftColorConverter.fromMinecraftColorCode(avgLevelColorCode, 0.8F);
            Color fillColor = MinecraftColorConverter.fromMinecraftColorCode(avgLevelColorCode, 0.2F);

            ExtrudeMarker marker = ExtrudeMarker.builder()
                    .position(entity.getCenterPoint().x(), entity.getCenterPoint().y(), entity.getCenterPoint().z())
                    .shape(circleShape, 0, 256)
                    .lineColor(lineColor)
                    .fillColor(fillColor)
                    .listed(true)
                    .detail("<b>" + label + "</b><br/>" + entity.getDescription() + "<br/><br/> Level range: <b>" + entity.getMinLevel() + " - " + entity.getMaxLevel() + "</b>")
                    .label(label)
                    .build();

            MarkerSet set = markerSets.get(markerType);

            if (set != null) {
                set.getMarkers()
                        .put(UUID.randomUUID().toString(), marker);
                count.getAndIncrement();
            } else {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "MarkerSet for " + markerType.getLabel() + " is missing");
            }

        });

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + count.get() + " area markers for BlueMap.");
    }

    public void clearMarkers() {
        this.markerSets.forEach((type, set) -> {
            set.getMarkers().clear();
        });
    }


}
