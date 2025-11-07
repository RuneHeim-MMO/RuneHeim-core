package org.zuma.examplePlugin.domain.storage;

import org.zuma.examplePlugin.core.database.HikariPool;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StorageItemContentRepository implements ItemStorageRepository {
    private final HikariPool pool;

    public StorageItemContentRepository(HikariPool pool) {
        this.pool = pool;
    }

    @Nullable
    public String getByUUID(UUID containerUUID) {
        String sql = "SELECT json FROM storage_item_contents WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, containerUUID.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("json");
                } else {
                    return null; // Not found
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve bag json for UUID: " + containerUUID + " " + e);
        }
    }

    public void save(UUID containerUUID, String content) {
        String sql = """
                INSERT INTO storage_item_contents (uuid, json)
                VALUES (?, ?)
                ON DUPLICATE KEY UPDATE
                json = VALUES(json),
                updated_at = CURRENT_TIMESTAMP
                """;

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, containerUUID.toString());
            stmt.setString(2, content);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to upsert storage content ", e);
        }
    }
}
