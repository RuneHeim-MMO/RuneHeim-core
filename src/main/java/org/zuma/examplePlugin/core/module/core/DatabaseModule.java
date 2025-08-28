package org.zuma.examplePlugin.core.module.core;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;

import java.util.List;

public class DatabaseModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        HikariPool pool = new HikariPool();
        pool.initialize(ConfigManager.getDotenv());

        context.bind(HikariPool.class, pool);
    }

    @Override
    public List<Class<?>> provides() {
        return List.of(HikariPool.class);
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
