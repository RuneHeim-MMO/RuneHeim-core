package org.zuma.examplePlugin.core.modules.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.modules.ModuleContext;
import org.zuma.examplePlugin.domain.combat.ranged.BowSpeedHandler;
import org.zuma.examplePlugin.domain.combat.ranged.BowVelocityHandler;

import java.util.List;

public class BowModule implements PluginModule {

    public BowModule() {}

    @Override
    public List<Class<?>> dependencies() {
        return List.of();
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        BowSpeedHandler bowSpeedHandler = new BowSpeedHandler();
        BowVelocityHandler bowVelocityHandler = new BowVelocityHandler();

        plugin.getServer().getPluginManager().registerEvents(bowSpeedHandler, plugin);
        plugin.getServer().getPluginManager().registerEvents(bowVelocityHandler, plugin);

    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
