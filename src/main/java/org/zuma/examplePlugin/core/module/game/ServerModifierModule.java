package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.modifier.scopes.global.GlobalModifierHandler;
import org.zuma.examplePlugin.domain.modifier.scopes.server.ServerModifierHandler;

import java.util.List;

public class ServerModifierModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        ServerModifierHandler modifierHandler = new ServerModifierHandler();
        modifierHandler.startLoop();

        plugin.getServer().getPluginManager().registerEvents(modifierHandler, plugin);
        context.bind(ServerModifierHandler.class, modifierHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
