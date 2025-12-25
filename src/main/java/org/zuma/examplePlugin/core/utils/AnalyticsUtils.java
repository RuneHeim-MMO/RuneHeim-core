package org.zuma.examplePlugin.core.utils;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class AnalyticsUtils {

    public static void incrementServerAndPlayer(RunePlayer player, AnalyticsKey key, double amount) {
        player.getAnalyticsContainer().increment(key, amount);
        RuneQuestPlugin.getAnalytics().increment(key, amount);
    }

    public static void decrementServerAndPlayer(RunePlayer player, AnalyticsKey key, double amount) {
        player.getAnalyticsContainer().decrement(key, amount);
        RuneQuestPlugin.getAnalytics().decrement(key, amount);
    }
}
