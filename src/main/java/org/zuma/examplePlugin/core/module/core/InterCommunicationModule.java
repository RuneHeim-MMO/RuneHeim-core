package org.zuma.examplePlugin.core.module.core;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.handlers.core.ScheduledRestartHandler;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.redis.CatchAllListener;
import org.zuma.examplePlugin.core.redis.GlobalEventHandler;
import org.zuma.examplePlugin.core.redis.RedisConnection;
import org.zuma.examplePlugin.events.core.NetworkRestartEvent;

public class InterCommunicationModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        initializeRedis();

        plugin.getServer().getPluginManager().registerEvents(new CatchAllListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ScheduledRestartHandler(), plugin);
    }

    private void initializeRedis() {
        try {
            RedisConnection.init(ConfigManager.getDotenv());
            GlobalEventHandler.addListener(NetworkRestartEvent.class, e -> ScheduledRestartHandler.scheduleRestart(e.restartAt, e.reason));
        } catch (Throwable t) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Redis init failed, continuing anyway.", t);
        }
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        GlobalEventHandler.clearHandlers();
        RedisConnection.onDisable();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
