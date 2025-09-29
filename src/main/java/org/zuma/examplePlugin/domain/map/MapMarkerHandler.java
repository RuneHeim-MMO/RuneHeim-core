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
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneType;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.impl.AreaSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.impl.ChunkSpawnZone;
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

    public void renderTerritories(Collection<Territory> territories) {
        AtomicInteger count = new AtomicInteger();

        territories.forEach(territory -> {
            if (!territory.isRenderOnMap()) return;

            MarkerType markerType = MarkerType.AREAS;
            Shape circleShape = MapShapeGenerator.generateCircleShapeAsShape((int) territory.getCenterPoint().x(), (int) territory.getCenterPoint().z(), territory.getRadius(), 20);

            String label = territory.getName().replaceAll("§.", "");
            String avgLevelColorCode = TextUtils.getLegacyColorCodeForLevel(territory.getMinLevel() + territory.getMaxLevel() / 2);
            Color lineColor = territory.isPvpZone() ? new Color(255, 0, 0, 1F) : MinecraftColorConverter.fromMinecraftColorCode(avgLevelColorCode, 1F);
            Color fillColor = territory.isPvpZone() ? new Color(255, 0, 0, 0.5F) : MinecraftColorConverter.fromMinecraftColorCode(avgLevelColorCode, 0.2F);

            String additionalDescription = territory.isPvpZone() ?
                    "<b><span style='color:red'>PvP Zone</span></b>" + (territory.isDropItemsOnDeath() ? "</br><b><span style='color:red'>* All items dropped on death *</span></b>" : "") :
                    "Level range: <b>" + territory.getMinLevel() + " - " + territory.getMaxLevel() + "</b>";

            ExtrudeMarker marker = ExtrudeMarker.builder()
                    .position(territory.getCenterPoint().x(), territory.getCenterPoint().y(), territory.getCenterPoint().z())
                    .shape(circleShape, 0, 256)
                    .lineColor(lineColor)
                    .fillColor(fillColor)
                    .listed(true)
                    .detail("<b>" + label + "</b><br/>" + territory.getDescription() + "<br/><br/>" + additionalDescription)
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

    public void renderSpawnZones(Collection<AbstractSpawnZone> zones) {
        AtomicInteger count = new AtomicInteger();

        zones.forEach(zone -> {
            MarkerType markerType = MarkerType.SPAWN_ZONE;
            int radius = 1;

            if (zone instanceof AreaSpawnZone areaSpawnZone) {
                radius = areaSpawnZone.getRadius();
            } else if (zone instanceof ChunkSpawnZone chunkSpawnZone) {
                radius = 16;
            }

            Shape circleShape = MapShapeGenerator.generateCircleShapeAsShape((int) zone.getCenterPoint().x(), (int) zone.getCenterPoint().z(), radius, 20);

            String label = "Spawn Zone ID: " + zone.getId();

            String avgLevelColorCode = zone.getMobRecords().isEmpty() ? "§1" : "§b";
            Color lineColor = MinecraftColorConverter.fromMinecraftColorCode(avgLevelColorCode, 1F);
            Color fillColor = MinecraftColorConverter.fromMinecraftColorCode(avgLevelColorCode, 0.2F);

            final String[] list = {""};

            zone.getMobRecords().forEach(spawnZoneMobRecord -> {
                list[0] = list[0] + "</br>" + spawnZoneMobRecord.amount() + "x " + spawnZoneMobRecord.mobId() + ": " + spawnZoneMobRecord.spawnChancePercentage() + "%";
            });

            ShapeMarker marker = ShapeMarker.builder()
                    .position(zone.getCenterPoint().x(), zone.getCenterPoint().y(), zone.getCenterPoint().z())
                    .shape(circleShape, 256)
                    .lineColor(lineColor)
                    .fillColor(fillColor)
                    .listed(true)
                    .detail("<b>" + label + "</b>" + list[0])
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

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + count.get() + " spawn zone markers for BlueMap.");
    }

    public void clearMarkers() {
        this.markerSets.forEach((type, set) -> {
            set.getMarkers().clear();
        });
    }


}
