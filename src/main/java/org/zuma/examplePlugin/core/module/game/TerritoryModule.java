package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.domain.territory.TerritoryHandler;
import org.zuma.examplePlugin.domain.territory.TerritoryRepository;

import java.util.List;

public class TerritoryModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        TerritoryHandler territoryHandler = new TerritoryHandler(new TerritoryRepository());
        territoryHandler.initialize();

        context.bind(TerritoryHandler.class, territoryHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
