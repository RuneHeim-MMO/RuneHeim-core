package org.zuma.examplePlugin.core.module.core;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.handlers.core.WebHook;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;

public class HTTPHookModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        if (!ConfigManager.getIsPreviewServer()) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Skipping hook HTTP server initialization, this is not a preview server.");
            return;
        }

        WebHook.initializeServer(ConfigManager.getDotenv());
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
