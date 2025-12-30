package org.zuma.examplePlugin.core.module.core;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.discord.DiscordVerificationHandler;
import org.zuma.examplePlugin.domain.discord.persistence.VerificationRepository;

import java.util.List;

public class DiscordVerificationModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class);
    }


    @Override
    public void start(Plugin plugin, ModuleContext context) {
        DiscordVerificationHandler handler = new DiscordVerificationHandler(
                new VerificationRepository(context.get(HikariPool.class))
        );

        context.bind(DiscordVerificationHandler.class, handler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        context.get(HikariPool.class).closeConnection();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
