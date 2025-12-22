package org.zuma.examplePlugin.core.module.game;


import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.combat.HitHandler;
import org.zuma.examplePlugin.domain.command.HouseCommand;
import org.zuma.examplePlugin.domain.housing.HousingManager;
import org.zuma.examplePlugin.domain.housing.persistence.HousingRepository;
import org.zuma.examplePlugin.domain.housing.world.WorldManager;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.features.admin.commands.AdminCommand;

import java.util.List;

public class PlayerHousingModule implements PluginModule {
    WorldManager manager;
    HousingManager housingManager;

    @Override
    public List<Class<?>> dependencies() {
        return List.of(BlockManager.class, HikariPool.class);
    }


    @Override
    public void start(Plugin plugin, ModuleContext context) {
        manager = new WorldManager();
        manager.init();

        housingManager = new HousingManager(manager, new HousingRepository(context.get(HikariPool.class)));
        housingManager.init();

        plugin.getServer().getPluginManager().registerEvents(housingManager, plugin);
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> event.registrar().register("house", new HouseCommand()));

        context.bind(HousingManager.class, housingManager);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        manager.cleanup();
        if (housingManager != null) {
            housingManager.onDisable();
        }
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
