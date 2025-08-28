package org.zuma.examplePlugin.core.modules.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.modules.ModuleContext;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneHandler;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneListener;
import org.zuma.examplePlugin.domain.spawnzone.persistence.SpawnZoneRepository;

import java.util.List;

public class SpawnZoneModule implements PluginModule {

    public SpawnZoneModule() {}

    @Override
    public List<Class<?>> dependencies() {
        return List.of();
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        SpawnZoneHandler spawnZoneHandler = new SpawnZoneHandler(new SpawnZoneRepository());
        SpawnZoneListener spawnZoneListener = new SpawnZoneListener(spawnZoneHandler);

        plugin.getServer().getPluginManager().registerEvents(spawnZoneListener, plugin);
        spawnZoneHandler.initializeFromDb();
        spawnZoneHandler.startLoop();

        context.bind(SpawnZoneHandler.class,spawnZoneHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context){
        context.get(SpawnZoneHandler.class).onDisable();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
