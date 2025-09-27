package org.zuma.examplePlugin.core.module.game;


import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.handlers.game.*;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;

public class PacketsModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false)
                .checkForUpdates(false)
                .bStats(false);

        PacketEvents.getAPI().getEventManager().registerListener(new ScoreboardNumberDisabler(),
                PacketListenerPriority.LOW);

        PacketEvents.getAPI().init();
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
