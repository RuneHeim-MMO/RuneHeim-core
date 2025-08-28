package org.zuma.examplePlugin.core.modules.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.modules.ModuleContext;
import org.zuma.examplePlugin.domain.combat.HitHandler;
import org.zuma.examplePlugin.domain.combat.HitListener;
import org.zuma.examplePlugin.domain.mob.MobRegistry;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.player.handlers.HPRegenerationHandler;

import java.util.List;

public class CombatModule implements PluginModule {

    public CombatModule() {
    }

    @Override
    public List<Class<?>> dependencies() {
        return List.of(RunePlayerManager.class, MobRegistry.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        HitHandler hitHandler = new HitHandler();
        HitListener hitListener = new HitListener(hitHandler, context.get(RunePlayerManager.class), context.get(MobRegistry.class));
        HPRegenerationHandler hpRegenerationHandler = new HPRegenerationHandler(context.get(RunePlayerManager.class));
        hpRegenerationHandler.startLoop();

        plugin.getServer().getPluginManager().registerEvents(hitListener, plugin);

        context.bind(HitHandler.class, hitHandler);
        context.bind(HPRegenerationHandler.class, hpRegenerationHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        context.get(HPRegenerationHandler.class).cleanUp();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
