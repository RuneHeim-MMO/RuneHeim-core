package org.zuma.examplePlugin.core.module.core;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.handlers.core.WebHook;
import org.zuma.examplePlugin.core.handlers.core.WhitelistHandler;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.redis.CatchAllListener;

public class WhitelistModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        WhitelistHandler handler = new WhitelistHandler();
        plugin.getServer().getPluginManager().registerEvents(handler, plugin);
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
