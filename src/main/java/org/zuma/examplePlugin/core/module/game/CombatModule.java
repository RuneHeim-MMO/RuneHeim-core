package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.combat.HitHandler;
import org.zuma.examplePlugin.domain.combat.HitListener;
import org.zuma.examplePlugin.domain.mob.MobRegistry;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.player.handlers.RegenerationHandler;

import java.util.List;

public class CombatModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(RunePlayerManager.class, MobRegistry.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        HitHandler hitHandler = new HitHandler();
        HitListener hitListener = new HitListener(hitHandler, context.get(RunePlayerManager.class), context.get(MobRegistry.class));
        RegenerationHandler regenerationHandler = new RegenerationHandler(context.get(RunePlayerManager.class));
        regenerationHandler.startLoop();

        plugin.getServer().getPluginManager().registerEvents(hitListener, plugin);

        context.bind(HitHandler.class, hitHandler);
        context.bind(RegenerationHandler.class, regenerationHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        context.get(RegenerationHandler.class).cleanUp();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
