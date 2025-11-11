package org.zuma.examplePlugin.domain.payments.handlers;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.payments.Payment;
import org.zuma.examplePlugin.domain.payments.persistence.PaymentsHistoryRepository;
import org.zuma.examplePlugin.events.payment.SupportLevelQueryEvent;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentHandler {
    private final PaymentsHistoryRepository repository;

    public PaymentHandler(PaymentsHistoryRepository repository) {
        this.repository = repository;
    }

    public void addPayment(Payment payment, boolean reloadSupportLevels) {
        if (!ConfigManager.isPreviewServer) {
            throw new UnsupportedOperationException("Payments can only be added on preview servers.");
        }

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {

                // Try to add payment
                this.repository.addPayment(payment);
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[DONATE] Processed payment of $" + payment.amount() + " for player " + payment.playerUUID() + ".");

                // Only schedule events if DB operations succeeded
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    if (reloadSupportLevels) {
                        SupportLevelQueryEvent supportLevelQueryEvent = new SupportLevelQueryEvent(new SupportLevelQueryEvent.Payload(payment.playerUUID()));
                        Bukkit.getServer().getPluginManager().callEvent(supportLevelQueryEvent);
                    }
                });

            } catch (Exception e) {
                // Log the failure, and do NOT schedule the events
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "[DONATE] Failed to process payment for " + payment.playerUUID(), e);
            }
        });
    }

    public BigDecimal getTotalPayments(UUID UUID) {
        return this.repository.getTotalPayments(UUID);
    }
}
