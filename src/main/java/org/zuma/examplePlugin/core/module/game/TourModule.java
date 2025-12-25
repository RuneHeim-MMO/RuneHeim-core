package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.tour.TourHandler;
import org.zuma.examplePlugin.domain.tour.TourListener;

public class TourModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        TourHandler tourHandler = new TourHandler();
        TourListener tourListener = new TourListener(tourHandler);

        context.bind(TourHandler.class, tourHandler);
        plugin.getServer().getPluginManager().registerEvents(tourListener, plugin);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
