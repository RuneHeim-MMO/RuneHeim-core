package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.domain.combat.ranged.BowSpeedHandler;
import org.zuma.examplePlugin.domain.combat.ranged.BowVelocityHandler;

public class BowModule implements PluginModule {

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
