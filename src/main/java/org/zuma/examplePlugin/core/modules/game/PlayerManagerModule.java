package org.zuma.examplePlugin.core.modules.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.modules.ModuleContext;
import org.zuma.examplePlugin.domain.player.RunePlayerListener;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.player.persistence.RunePlayerRepository;

import java.util.List;

public class PlayerManagerModule implements PluginModule {

    public PlayerManagerModule() {}

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        RunePlayerRepository runePlayerRepository = new RunePlayerRepository(context.get(HikariPool.class));
        RunePlayerManager runePlayerManager = new RunePlayerManager(runePlayerRepository);
        RunePlayerListener runePlayerListener = new RunePlayerListener(runePlayerManager);

        plugin.getServer().getPluginManager().registerEvents(runePlayerListener, plugin);
        runePlayerManager.handleOnEnable();

        context.bind(RunePlayerManager.class,runePlayerManager);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        context.get(RunePlayerManager.class).handleOnDisable();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
