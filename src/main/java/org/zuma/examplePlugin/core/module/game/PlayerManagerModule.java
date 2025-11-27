package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.domain.daily.DailyRewardHandler;
import org.zuma.examplePlugin.domain.player.RunePlayerListener;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.player.handlers.FlyingHandler;
import org.zuma.examplePlugin.domain.player.handlers.TemporaryEffectHandler;
import org.zuma.examplePlugin.domain.player.persistence.RunePlayerRepository;
import org.zuma.examplePlugin.domain.quest.QuestHandler;

import java.util.List;

public class PlayerManagerModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class, QuestModule.class, DailyRewardModule.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        RunePlayerRepository runePlayerRepository = new RunePlayerRepository(context.get(HikariPool.class));
        RunePlayerManager runePlayerManager = new RunePlayerManager(runePlayerRepository, context.get(QuestHandler.class), context.get(DailyRewardHandler.class));
        RunePlayerListener runePlayerListener = new RunePlayerListener(runePlayerManager);

        new TemporaryEffectHandler(runePlayerManager).startLoop();
        new FlyingHandler(runePlayerManager).startLoop();

        plugin.getServer().getPluginManager().registerEvents(runePlayerListener, plugin);
        runePlayerManager.handleOnEnable();

        context.bind(RunePlayerManager.class, runePlayerManager);
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
