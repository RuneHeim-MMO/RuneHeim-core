package org.zuma.examplePlugin.core.modules.game;

import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.handlers.game.*;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.modules.ModuleContext;

import java.util.List;

public class GameRulesModule implements PluginModule {

    public GameRulesModule() {}

    @Override
    public List<Class<?>> dependencies() {
        return List.of();
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        plugin.getServer().getPluginManager().registerEvents(new IllegalItemWatchdog(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InteractionDisabler(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockProtector(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DefaultMobDisabler(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new VanillaDamageDisabler(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DurabilityDisabler(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new HungerLocker(), plugin);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
