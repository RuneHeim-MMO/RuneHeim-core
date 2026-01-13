package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.herblore.ChunkPlantPopulator;
import org.zuma.examplePlugin.domain.herblore.HerbloreListener;
import org.zuma.examplePlugin.domain.herblore.HerbloreManager;
import org.zuma.examplePlugin.domain.herblore.persistence.PlantCandidatesRepository;

import java.util.List;

public class HerbloreModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of();
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        HerbloreManager herbloreManager = new HerbloreManager(new PlantCandidatesRepository(), new ChunkPlantPopulator());
        HerbloreListener listener = new HerbloreListener(herbloreManager);

        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        context.bind(HerbloreManager.class, herbloreManager);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
