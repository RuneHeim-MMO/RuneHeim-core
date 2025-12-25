package org.zuma.examplePlugin.core.module.core;


import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.config.EnvironmentType;
import org.zuma.examplePlugin.core.handlers.core.WebHook;
import org.zuma.examplePlugin.core.interfaces.POIMarkerable;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.map.MapMarkerHandler;
import org.zuma.examplePlugin.domain.map.StaticMarkers;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneHandler;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.domain.territory.Territory;
import org.zuma.examplePlugin.domain.territory.TerritoryHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class MapModule implements PluginModule {
    private static final Plugin blueMap = Bukkit.getPluginManager().getPlugin("BlueMap");

    public MapModule() {
    }

    @Override
    public List<Class<?>> dependencies() {
        return List.of(BlockManager.class, TerritoryHandler.class, SpawnZoneHandler.class);
    }

    private void initializeMap(ModuleContext moduleContext) {
        if (blueMap == null || !blueMap.isEnabled()) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Blue map plugin not found, cannot attach markers.");
            return;
        }

        MapMarkerHandler markerHandler = new MapMarkerHandler();
        markerHandler.initializeMarkerSets();

        moduleContext.bind(MapMarkerHandler.class, markerHandler);
    }

    public void reloadMarkers(ModuleContext moduleContext) {
        if (blueMap == null || !blueMap.isEnabled()) {
            return;
        }

        Collection<POIMarkerable> poiMarkers = new ArrayList<>(
                new HashSet<>(moduleContext.get(BlockManager.class).getAllBlocks()) // filter out duplicates based on instance equality
        );
        poiMarkers.addAll(StaticMarkers.getStaticMarkers());

        Collection<AbstractSpawnZone> zones = new ArrayList<>();

        for (AbstractSpawnZone zone : moduleContext.get(SpawnZoneHandler.class).getZones()) {
            if (zone instanceof POIMarkerable) {
                poiMarkers.add((POIMarkerable) zone);
            } else {
                zones.add(zone);
            }
        }

        Collection<Territory> territories = new ArrayList<>(moduleContext.get(TerritoryHandler.class).getTerritoryList());

        MapMarkerHandler markerHandler = moduleContext.get(MapMarkerHandler.class);
        markerHandler.clearMarkers();
        markerHandler.renderMarkers(poiMarkers);
        markerHandler.renderTerritories(territories);

        if (ConfigManager.environment.equals(EnvironmentType.DEVELOPMENT)) {
            markerHandler.renderSpawnZones(zones);
        }

    }


    @Override
    public void start(Plugin plugin, ModuleContext context) {
        if (!ConfigManager.getIsPreviewServer()) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Skipping BlueMap initialization, this is not a preview server.");
            return;
        }

        initializeMap(context);
        context.bind(MapModule.class, this);
    }


    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        WebHook.cleanUp();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
