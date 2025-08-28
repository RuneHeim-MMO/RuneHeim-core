package org.zuma.examplePlugin.core.modules.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.modules.ModuleContext;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.modifier.GearModifierHandler;
import org.zuma.examplePlugin.domain.modifier.GearModifierListener;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotHandler;

import java.util.List;

public class PlayerModifierHandler implements PluginModule {

    public PlayerModifierHandler() {}

    @Override
    public List<Class<?>> dependencies() {
        return List.of(RunePlayerManager.class,AdditionalGearSlotHandler.class, ItemRegistry.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        GearModifierHandler gearModifierHandler = new GearModifierHandler(context.get(ItemRegistry.class), context.get(AdditionalGearSlotHandler.class));
        GearModifierListener gearModifierListener = new GearModifierListener(gearModifierHandler, context.get(RunePlayerManager.class));

        plugin.getServer().getPluginManager().registerEvents(gearModifierListener, plugin);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
