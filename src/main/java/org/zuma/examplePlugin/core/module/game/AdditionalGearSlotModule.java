package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotHandler;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotListener;

public class AdditionalGearSlotModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        AdditionalGearSlotHandler additionalGearSlotHandler = new AdditionalGearSlotHandler();
        AdditionalGearSlotListener additionalGearSlotListener = new AdditionalGearSlotListener(additionalGearSlotHandler);

        plugin.getServer().getPluginManager().registerEvents(additionalGearSlotListener, plugin);
        context.bind(AdditionalGearSlotHandler.class, additionalGearSlotHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
