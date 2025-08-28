package org.zuma.examplePlugin.core.modules.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.modules.ModuleContext;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotHandler;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotListener;

import java.util.List;

public class AdditionalGearSlotModule implements PluginModule {

    public AdditionalGearSlotModule() {}

    @Override
    public List<Class<?>> dependencies() {
        return List.of();
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        AdditionalGearSlotHandler additionalGearSlotHandler = new AdditionalGearSlotHandler();
        AdditionalGearSlotListener additionalGearSlotListener = new AdditionalGearSlotListener(additionalGearSlotHandler);

        plugin.getServer().getPluginManager().registerEvents(additionalGearSlotListener, plugin);

        context.bind(AdditionalGearSlotHandler.class,additionalGearSlotHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
