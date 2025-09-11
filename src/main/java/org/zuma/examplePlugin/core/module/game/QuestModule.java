package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.quest.QuestHandler;
import org.zuma.examplePlugin.domain.quest.QuestRegistry;
import org.zuma.examplePlugin.domain.quest.persistence.QuestProgressRepository;

import java.util.List;

public class QuestModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        QuestRegistry registry = new QuestRegistry();
        QuestHandler questHandler = new QuestHandler(new QuestProgressRepository(context.get(HikariPool.class)), registry);

        context.bind(QuestHandler.class, questHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
