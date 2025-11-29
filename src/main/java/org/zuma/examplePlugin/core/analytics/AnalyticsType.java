package org.zuma.examplePlugin.core.analytics;

import org.zuma.examplePlugin.core.analytics.impl.PlayerAnalytics;
import org.zuma.examplePlugin.core.analytics.impl.ServerAnalytics;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public enum AnalyticsType {
    SERVER("server_analytics"),
    PLAYER("player_analytics");

    private final String tableName;

    AnalyticsType(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public AnalyticsContainer createContainer(String key, @Nullable Map<AnalyticsKey, Double> initialValues) {
        return switch (this) {
            case SERVER -> new ServerAnalytics(initialValues);
            case PLAYER -> new PlayerAnalytics(UUID.fromString(key), initialValues);
            default -> throw new IllegalArgumentException("Unknown AnalyticsType: " + this);
        };
    }
}
