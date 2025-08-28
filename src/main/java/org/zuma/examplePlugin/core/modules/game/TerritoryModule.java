package org.zuma.examplePlugin.core.modules.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.modules.ModuleContext;
import org.zuma.examplePlugin.domain.territory.TerritoryHandler;
import org.zuma.examplePlugin.domain.territory.TerritoryRepository;

import java.util.List;

public class TerritoryModule implements PluginModule {

    public TerritoryModule() {}

    @Override
    public List<Class<?>> dependencies() {
        return List.of();
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        TerritoryHandler territoryHandler = new TerritoryHandler(new TerritoryRepository());
        territoryHandler.initialize();

        context.bind(TerritoryHandler.class,territoryHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context){
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
