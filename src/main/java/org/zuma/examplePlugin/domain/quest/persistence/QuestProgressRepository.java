package org.zuma.examplePlugin.domain.quest.persistence;

import org.zuma.examplePlugin.core.database.HikariPool;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class QuestProgressRepository {
    private final HikariPool pool;

    public QuestProgressRepository(HikariPool pool) {
        this.pool = pool;
    }

    @Nullable
    public String getByUUID(UUID playerUUID) {
        String sql = "SELECT json FROM player_quest_data WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerUUID.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("json");
                } else {
                    return null; // Not found
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve quest data json for UUID: " + playerUUID, e);
        }
    }

    public void save(UUID playerUUID, @Nullable String trackedQuestId, String content) {
        String sql = """
                INSERT INTO player_quest_data (uuid, tracking_quest_id, json)
                VALUES (?, ?, ?)
                ON DUPLICATE KEY UPDATE
                tracking_quest_id = VALUES(tracking_quest_id),
                json = VALUES(json),
                updated_at = CURRENT_TIMESTAMP
                """;

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerUUID.toString());
            stmt.setString(2, trackedQuestId);
            stmt.setString(3, content);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to upsert quest data for UUID: " + playerUUID, e);
        }
    }
}
