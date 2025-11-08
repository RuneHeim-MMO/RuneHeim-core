package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.domain.storage.persistence.BankContentRepository;
import org.zuma.examplePlugin.domain.storage.RepositoryQueryLockController;
import org.zuma.examplePlugin.domain.storage.persistence.StorageItemContentRepository;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.gui.GUIHandler;
import org.zuma.examplePlugin.domain.gui.GUIListener;
import org.zuma.examplePlugin.domain.gui.GUIRegistry;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

import java.util.List;

public class GUIModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class, RunePlayerManager.class, BlockLoader.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        GUIHandler guiHandler = new GUIHandler(new GUIRegistry(), context.get(RunePlayerManager.class));
        GUIListener guiListener = new GUIListener(guiHandler);

        guiHandler.closeAllInventories();
        guiHandler.initializeInventories(context.get(BlockLoader.class),
                new RepositoryQueryLockController(new BankContentRepository(context.get(HikariPool.class))),
                new RepositoryQueryLockController(new StorageItemContentRepository(context.get(HikariPool.class))));

        plugin.getServer().getPluginManager().registerEvents(guiListener, plugin);

        context.bind(GUIHandler.class, guiHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        context.get(GUIHandler.class).closeAllInventories();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
