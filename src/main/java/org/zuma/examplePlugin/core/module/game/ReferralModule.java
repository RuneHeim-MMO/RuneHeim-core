package org.zuma.examplePlugin.core.module.game;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.command.MentorCommand;
import org.zuma.examplePlugin.domain.referral.ReferralManager;
import org.zuma.examplePlugin.domain.referral.persistence.ReferralRepository;

import java.util.List;

public class ReferralModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        ReferralManager manager = new ReferralManager(new ReferralRepository(context.get(HikariPool.class)));
        plugin.getServer().getPluginManager().registerEvents(manager, plugin);

        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("mentor", new MentorCommand()));

        context.bind(ReferralManager.class, manager);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
