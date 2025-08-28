package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.domain.mob.*;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

import java.util.List;

public class MobModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(RunePlayerManager.class);
    }

    @Override
    public List<Class<?>> provides() {
        return List.of(MobRegistry.class, MobHandler.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        MobTypeRegistry mobTypeRegistry = new MobTypeRegistry();
        MobTypesLoader.registerMobs(mobTypeRegistry);

        MobSessionManager mobSessionManager = new MobSessionManager();
        MobRegistry mobRegistry = new MobRegistry(mobTypeRegistry);
        MobHandler mobHandler = new MobHandler(mobRegistry);
        MobListener mobListener = new MobListener(context.get(RunePlayerManager.class), mobHandler, mobRegistry);
        CityGuardHandler cityGuardHandler = new CityGuardHandler();

        mobHandler.cleanup();

        plugin.getServer().getPluginManager().registerEvents(cityGuardHandler, plugin);
        plugin.getServer().getPluginManager().registerEvents(mobListener, plugin);
        plugin.getServer().getPluginManager().registerEvents(mobSessionManager, plugin);

        context.bind(MobRegistry.class, mobRegistry);
        context.bind(MobHandler.class, mobHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        context.get(MobHandler.class).cleanup();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
