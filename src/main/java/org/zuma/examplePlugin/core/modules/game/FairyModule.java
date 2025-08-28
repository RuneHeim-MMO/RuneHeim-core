package org.zuma.examplePlugin.core.modules.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.modules.ModuleContext;
import org.zuma.examplePlugin.domain.fairy.FairyHandler;
import org.zuma.examplePlugin.domain.fairy.FairyListener;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotHandler;

import java.util.List;

public class FairyModule implements PluginModule {

    public FairyModule() {}

    @Override
    public List<Class<?>> dependencies() {
        return List.of(AdditionalGearSlotHandler.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        FairyHandler fairyHandler = new FairyHandler(context.get(AdditionalGearSlotHandler.class));
        FairyListener fairyListener = new FairyListener(fairyHandler);

        plugin.getServer().getPluginManager().registerEvents(fairyListener, plugin);
        fairyHandler.startLoop();

        context.bind(FairyHandler.class,fairyHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context){
        context.get(FairyHandler.class).stopLoop();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
