package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.housing.HousingManager;
import org.zuma.examplePlugin.domain.housing.world.WorldManager;
import org.zuma.examplePlugin.domain.item.ItemRegistry;

import java.util.List;

public class PlayerHousingModule implements PluginModule {
    WorldManager manager;

    @Override
    public List<Class<?>> dependencies() {
        return List.of(BlockManager.class, HikariPool.class);
    }


    @Override
    public void start(Plugin plugin, ModuleContext context) {
        manager = new WorldManager();
        manager.init();

        HousingManager housingManager = new HousingManager(manager);
        housingManager.loadTemplatesMeta();

        plugin.getServer().getPluginManager().registerEvents(housingManager, plugin);

        context.bind(HousingManager.class, housingManager);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        manager.cleanup();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
