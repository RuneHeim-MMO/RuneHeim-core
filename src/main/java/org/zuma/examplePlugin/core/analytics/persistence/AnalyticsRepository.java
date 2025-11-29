package org.zuma.examplePlugin.core.analytics.persistence;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.analytics.AnalyticsContainer;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.analytics.AnalyticsType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

public class AnalyticsRepository {
    private final HikariPool pool;

    public AnalyticsRepository(HikariPool pool) {
        this.pool = pool;
    }

    public AnalyticsContainer getAnalytics(AnalyticsType type, String key) {
        String tableName = type.getTableName();
        String sql = String.format("""
                SELECT analytics_key, value
                FROM %s
                WHERE owner = ?
                """, tableName);


        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, key);
            var rs = stmt.executeQuery();

            Map<AnalyticsKey, Double> values = new java.util.HashMap<>();
            while (rs.next()) {
                String analyticsKey = rs.getString("analytics_key");
                double value = rs.getDouble("value");

                AnalyticsKey keyEnum = AnalyticsKey.valueOf(analyticsKey);
                values.put(keyEnum, value);
            }
            return type.createContainer(key, values);

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve analytics container. Type: " + type + " key: " + key + " data!", e);
        }
    }

    public void upsertAnalytics(AnalyticsContainer container) {
        String tableName = container.type().getTableName();
        String key = container.key();

        String sql = String.format("""
                INSERT INTO %s (owner, analytics_key, value)
                VALUES (?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    value = VALUES(value),
                    updated_at = CURRENT_TIMESTAMP
                """, tableName);

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (var entry : container.getAllValues().entrySet()) {
                stmt.setString(1, key);
                stmt.setString(2, entry.getKey().toString());
                stmt.setDouble(3, entry.getValue());
                stmt.addBatch();
            }

            stmt.executeBatch();


        } catch (Exception e) {
            throw new RuntimeException("Failed to upsert analytics container. Type: " + container.type() + " key: " + key + " data!", e);
        }


    }

}
