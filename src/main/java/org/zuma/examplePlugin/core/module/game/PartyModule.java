package org.zuma.examplePlugin.core.module.game;


import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.party.PartyListener;
import org.zuma.examplePlugin.domain.party.PartyManager;
import org.zuma.examplePlugin.domain.party.commands.PartyCommand;

public class PartyModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        PartyManager partyManager = new PartyManager();
        PartyListener partyListener = new PartyListener();

        plugin.getServer().getPluginManager().registerEvents(partyListener, plugin);

        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("party", new PartyCommand()));

        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("p", new PartyCommand()));

        context.bind(PartyManager.class, partyManager);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
