package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneHandler;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneListener;
import org.zuma.examplePlugin.domain.spawnzone.persistence.SpawnZoneRepository;

import java.util.List;

public class SpawnZoneModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        SpawnZoneHandler spawnZoneHandler = new SpawnZoneHandler(new SpawnZoneRepository());
        SpawnZoneListener spawnZoneListener = new SpawnZoneListener(spawnZoneHandler);

        plugin.getServer().getPluginManager().registerEvents(spawnZoneListener, plugin);
        spawnZoneHandler.initializeFromDb();
        spawnZoneHandler.startLoop();

        context.bind(SpawnZoneHandler.class, spawnZoneHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        context.get(SpawnZoneHandler.class).onDisable();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
