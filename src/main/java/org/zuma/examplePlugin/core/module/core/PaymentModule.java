package org.zuma.examplePlugin.core.module.core;


import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.command.backend.ConsolePaymentCommand;
import org.zuma.examplePlugin.domain.modifier.scopes.server.ServerModifierHandler;
import org.zuma.examplePlugin.domain.payments.handlers.PaymentHandler;
import org.zuma.examplePlugin.domain.payments.persistence.PaymentsHistoryRepository;

import java.util.List;

public class PaymentModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class, ServerModifierHandler.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        PaymentsHistoryRepository paymentsHistoryRepository = new PaymentsHistoryRepository(context.get(HikariPool.class));
        PaymentHandler paymentHandler = new PaymentHandler(paymentsHistoryRepository);

        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("handle_payment", new ConsolePaymentCommand()));

        context.bind(PaymentHandler.class, paymentHandler);
        context.bind(PaymentsHistoryRepository.class, paymentsHistoryRepository);
    }


    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
