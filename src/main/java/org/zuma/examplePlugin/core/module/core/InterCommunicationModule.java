package org.zuma.examplePlugin.core.module.core;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.handlers.core.ScheduledRestartHandler;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.game.GUIModule;
import org.zuma.examplePlugin.core.redis.CatchAllListener;
import org.zuma.examplePlugin.core.redis.GlobalEventHandler;
import org.zuma.examplePlugin.core.redis.RedisConnection;
import org.zuma.examplePlugin.domain.gui.GUIHandler;
import org.zuma.examplePlugin.domain.mob.MobRegistry;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.events.core.NetworkRestartEvent;
import org.zuma.examplePlugin.events.inventory.RuneStorageInventoryOpenEvent;

import java.util.List;

public class InterCommunicationModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(GUIHandler.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        initializeRedis(context);

        plugin.getServer().getPluginManager().registerEvents(new CatchAllListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ScheduledRestartHandler(), plugin);
    }

    private void initializeRedis(ModuleContext context) {
        try {
            RedisConnection.init(ConfigManager.getDotenv());
            GlobalEventHandler.registerEvent(RuneStorageInventoryOpenEvent.class, RuneStorageInventoryOpenEvent.Payload.class);

            GlobalEventHandler.addListener(NetworkRestartEvent.class, e -> ScheduledRestartHandler.scheduleRestart(e.restartAt, e.reason));
            GlobalEventHandler.addListener(RuneStorageInventoryOpenEvent.class, e -> context.get(GUIHandler.class).onStorageContainerOpenGlobalEvent(e.containerUUID));
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
