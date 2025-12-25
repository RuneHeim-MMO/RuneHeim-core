package org.zuma.examplePlugin.core.module.game;

import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.item.ItemFactory;
import org.zuma.examplePlugin.domain.item.ItemHandler;
import org.zuma.examplePlugin.domain.item.ItemListener;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.persistence.ItemLoader;
import org.zuma.examplePlugin.domain.item.persistence.ItemRepository;
import org.zuma.examplePlugin.domain.item.state.StateFactory;
import org.zuma.examplePlugin.domain.item.state.StateListener;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.item.state.StateRepository;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

import java.util.List;

public class ItemModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class, RunePlayerManager.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        ItemRegistry itemRegistry = new ItemRegistry();
        ItemRepository itemRepository = new ItemRepository();
        ItemFactory itemFactory = new ItemFactory();

        ItemLoader itemLoader = new ItemLoader(itemRegistry, itemFactory, itemRepository);
        itemLoader.loadItemsFromDatabase();

        ItemHandler itemHandler = new ItemHandler(itemRegistry);
        StateRepository stateRepository = new StateRepository(context.get(HikariPool.class));
        StateManager itemStateManager = new StateManager(new StateFactory(), itemHandler, stateRepository);
        StateListener stateListener = new StateListener(itemStateManager);
        ItemListener itemListener = new ItemListener(itemHandler, context.get(RunePlayerManager.class), itemStateManager);

        plugin.getServer().getPluginManager().registerEvents(itemListener, plugin);
        plugin.getServer().getPluginManager().registerEvents(stateListener, plugin);

        context.bind(ItemRegistry.class, itemRegistry);
        context.bind(StateManager.class, itemStateManager);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
