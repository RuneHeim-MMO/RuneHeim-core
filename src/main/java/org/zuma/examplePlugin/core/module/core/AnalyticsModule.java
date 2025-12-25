package org.zuma.examplePlugin.core.module.core;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsHandler;
import org.zuma.examplePlugin.core.analytics.persistence.AnalyticsRepository;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;

import java.util.List;

public class AnalyticsModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        AnalyticsRepository repository = new AnalyticsRepository(context.get(HikariPool.class));
        AnalyticsHandler analyticsHandler = new AnalyticsHandler(repository);
        analyticsHandler.startLoop();
        context.bind(AnalyticsHandler.class, analyticsHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Saving server analytics data...");
        AnalyticsHandler handler = context.get(AnalyticsHandler.class);
        handler.saveAnalyticsSync(handler.getAnalytics());
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
