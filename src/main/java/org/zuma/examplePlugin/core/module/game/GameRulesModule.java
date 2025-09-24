package org.zuma.examplePlugin.core.module.game;

import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.handlers.game.*;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;

public class GameRulesModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        InteractionDisabler interactionDisabler = new InteractionDisabler();
        plugin.getServer().getPluginManager().registerEvents(new InteractionDisabler(), plugin);
        interactionDisabler.disableTourViewPermission();
        
        plugin.getServer().getPluginManager().registerEvents(new IllegalItemWatchdog(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockProtector(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DefaultMobDisabler(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new VanillaDamageDisabler(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DurabilityDisabler(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new HungerLocker(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new FirstJoinTutorialTeleporter(), plugin);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
