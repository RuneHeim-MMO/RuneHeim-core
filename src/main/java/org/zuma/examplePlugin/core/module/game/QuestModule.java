package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.combat.ranged.BowSpeedHandler;
import org.zuma.examplePlugin.domain.combat.ranged.BowVelocityHandler;
import org.zuma.examplePlugin.domain.quest.QuestHandler;

public class QuestModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        QuestHandler questHandler = new QuestHandler();
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
