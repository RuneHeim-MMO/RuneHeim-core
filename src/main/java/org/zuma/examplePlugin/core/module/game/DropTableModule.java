package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.domain.droptable.DropTableHandler;
import org.zuma.examplePlugin.domain.droptable.persistence.DropTableRepository;

import java.util.List;

public class DropTableModule implements PluginModule {

    @Override
    public List<Class<?>> provides() {
        return List.of(DropTableHandler.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        DropTableHandler dropTableHandler = new DropTableHandler(new DropTableRepository());
        dropTableHandler.initialize();

        context.bind(DropTableHandler.class, dropTableHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
