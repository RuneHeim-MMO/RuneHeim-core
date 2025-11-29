package org.zuma.examplePlugin.core.module.core;


import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.announcements.AnnouncementsHandler;
import org.zuma.examplePlugin.domain.announcements.persistence.AnnouncementsRepository;
import org.zuma.examplePlugin.domain.command.backend.ConsolePaymentCommand;
import org.zuma.examplePlugin.domain.modifier.scopes.server.ServerModifierHandler;
import org.zuma.examplePlugin.domain.payments.handlers.PaymentHandler;
import org.zuma.examplePlugin.domain.payments.persistence.PaymentsHistoryRepository;

import java.util.List;

public class AnnouncementModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        AnnouncementsRepository repository = new AnnouncementsRepository(context.get(HikariPool.class));
        AnnouncementsHandler announcementsHandler = new AnnouncementsHandler(repository);

        announcementsHandler.startAnnouncementBroadcastInterval(3);
        announcementsHandler.startRefetchInterval(10);

        context.bind(AnnouncementsRepository.class, repository);
        context.bind(AnnouncementsHandler.class, announcementsHandler);
    }


    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
