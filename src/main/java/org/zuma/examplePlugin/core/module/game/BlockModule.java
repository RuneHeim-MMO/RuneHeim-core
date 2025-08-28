package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.handlers.core.ModelEngineWaiter;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.domain.block.BlockFactory;
import org.zuma.examplePlugin.domain.block.BlockListener;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.domain.item.ItemRegistry;

import java.util.List;

public class BlockModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class, ItemRegistry.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        BlockManager blockManager = new BlockManager();
        BlockListener blockListener = new BlockListener(blockManager, context.get(ItemRegistry.class));

        InteractableBlockRepository blockRepository = new InteractableBlockRepository(context.get(HikariPool.class));
        BlockLoader blockLoader = new BlockLoader(blockManager, blockRepository, new BlockFactory(), new ResourceNodeTypeRepository());
        blockLoader.fetchAllResourceNodeTypes();

        plugin.getServer().getPluginManager().registerEvents(blockListener, plugin);

        context.bind(BlockManager.class, blockManager);
        context.bind(BlockLoader.class, blockLoader);
        context.bind(InteractableBlockRepository.class, blockRepository);

    }


    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        context.get(BlockManager.class).onDisable();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
