package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.fairy.FairyHandler;
import org.zuma.examplePlugin.domain.fairy.FairyListener;
import org.zuma.examplePlugin.domain.mount.MountHandler;
import org.zuma.examplePlugin.domain.mount.MountListener;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotHandler;

import java.util.List;

public class MountModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(AdditionalGearSlotHandler.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        MountHandler mountHandler = new MountHandler(context.get(AdditionalGearSlotHandler.class));
        MountListener mountListener = new MountListener(mountHandler);

        plugin.getServer().getPluginManager().registerEvents(mountListener, plugin);
        mountHandler.startLoop();

        context.bind(MountHandler.class, mountHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        context.get(MountHandler.class).onDisable();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
