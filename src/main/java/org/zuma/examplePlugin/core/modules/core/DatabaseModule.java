package org.zuma.examplePlugin.core.modules.core;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.handlers.core.ScheduledRestartHandler;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.modules.ModuleContext;
import org.zuma.examplePlugin.core.redis.CatchAllListener;
import org.zuma.examplePlugin.core.redis.GlobalEventHandler;
import org.zuma.examplePlugin.core.redis.RedisConnection;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.events.core.NetworkRestartEvent;

import java.util.List;

public class DatabaseModule implements PluginModule {

    public DatabaseModule() {}

    @Override
    public List<Class<?>> dependencies() {
        return List.of();
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        HikariPool pool = new HikariPool();
        pool.initialize(ConfigManager.getDotenv());

        context.bind(HikariPool.class,pool);
    }


    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        context.get(HikariPool.class).closeConnection();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
