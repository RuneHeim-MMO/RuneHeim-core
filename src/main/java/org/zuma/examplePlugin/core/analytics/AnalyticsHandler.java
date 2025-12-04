package org.zuma.examplePlugin.core.analytics;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.impl.ServerAnalytics;
import org.zuma.examplePlugin.core.analytics.persistence.AnalyticsRepository;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

public class AnalyticsHandler {
    private static final long SERVER_ANALYTICS_SAVE_INTERVAL_MS = 1000 * 60 * 5; // Every 5 minutes.
    private final AnalyticsRepository repository;
    private final AnalyticsContainer analytics;

    public AnalyticsHandler(AnalyticsRepository repository) {
        this.repository = repository;
        this.analytics = repository.getAnalytics(AnalyticsType.SERVER, String.valueOf(ConfigManager.getServerId()));
    }

    public void startLoop() {
        long intervalTicks = SERVER_ANALYTICS_SAVE_INTERVAL_MS / 50;

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Starting analytics saving loop. Interval: " + SERVER_ANALYTICS_SAVE_INTERVAL_MS / 1000 / 60 + " minutes");

        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), () -> {
            Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
                try {
                    saveAnalyticsSync(analytics);
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Server analytics have been saved.");
                } catch (Exception e) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to save analytics!", e);
                }
            });
        }, intervalTicks, intervalTicks);

    }

    public AnalyticsContainer getAnalytics() {
        return this.analytics;
    }

    public void saveAnalyticsSync(AnalyticsContainer container) {
        repository.upsertAnalytics(container);
    }

    public AnalyticsContainer loadAnalyticsSync(AnalyticsType type, String key) {
        return repository.getAnalytics(type, key);
    }


}
