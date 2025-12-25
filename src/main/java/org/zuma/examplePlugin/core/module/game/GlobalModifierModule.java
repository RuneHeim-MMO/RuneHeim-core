package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.modifier.persistence.GlobalModifierRepository;
import org.zuma.examplePlugin.domain.modifier.scopes.global.GlobalModifierHandler;

import java.util.List;

public class GlobalModifierModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        GlobalModifierRepository globalModifierRepository = new GlobalModifierRepository(context.get(HikariPool.class));
        GlobalModifierHandler globalModifierHandler = new GlobalModifierHandler(globalModifierRepository);
        globalModifierHandler.startLoop();

        context.bind(GlobalModifierHandler.class, globalModifierHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
