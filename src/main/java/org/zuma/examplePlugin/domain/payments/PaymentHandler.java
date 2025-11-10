package org.zuma.examplePlugin.domain.payments;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.permissions.roles.UpgradedRole;
import org.zuma.examplePlugin.events.payment.RankReloadEvent;
import org.zuma.examplePlugin.events.payment.SupportLevelReloadEvent;

import javax.annotation.Nullable;

public class PaymentHandler {
    private final PaymentsHistoryRepository repository;

    public PaymentHandler(PaymentsHistoryRepository repository) {
        this.repository = repository;
    }

    public void addPayment(Payment payment, @Nullable UpgradedRole newRank, boolean reloadSupportLevels) {
        if (!ConfigManager.isPreviewServer) {
            throw new UnsupportedOperationException("Payments can only be added on preview servers.");
        }

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                // If new rank, write it
                if (newRank != null) {
                    @Nullable UpgradedRole currentRole = RuneQuestPlugin.getPlayerManager().getPlayerRolesFromDatabaseSync(payment.playerUUID()).getValue();

                    if (currentRole != null && newRank.getWeight() > currentRole.getWeight()) {
                        this.repository.addPayment(payment); // Still log the payment even if rank change is invalid
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Processed payment of $" + payment.amount() + " for player " + payment.playerUUID() + " but did not change rank from " + currentRole + " to " + newRank + " due to lower weight.");
                        throw new IllegalArgumentException("New rank " + newRank + " has lower weight than current rank " + currentRole + " for player " + payment.playerUUID());
                    }

                    RuneQuestPlugin.getPlayerManager().writeNewUpgradedRoleToDatabaseSync(payment.playerUUID(), newRank);
                }

                // Try to add payment
                this.repository.addPayment(payment);
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Processed payment of $" + payment.amount() + " for player " + payment.playerUUID() + (newRank != null ? " set new rank " + newRank : "") + ".");

                // Only schedule events if DB operations succeeded
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    if (newRank != null) {
                        RankReloadEvent rankReloadEvent = new RankReloadEvent(new RankReloadEvent.Payload(payment.playerUUID()));
                        Bukkit.getServer().getPluginManager().callEvent(rankReloadEvent);
                    }

                    if (reloadSupportLevels) {
                        SupportLevelReloadEvent supportLevelReloadEvent = new SupportLevelReloadEvent(new SupportLevelReloadEvent.Payload(payment.playerUUID()));
                        Bukkit.getServer().getPluginManager().callEvent(supportLevelReloadEvent);
                    }
                });

            } catch (Exception e) {
                // Log the failure, and do NOT schedule the events
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to process payment for " + payment.playerUUID(), e);
            }
        });

    }
}
