package org.zuma.examplePlugin.domain.bank;

import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.domain.block.persistence.BlockEntity;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.*;

public class BankContentRepository implements ItemStorageRepository {
    private final HikariPool pool;

    public BankContentRepository(HikariPool pool) {
        this.pool = pool;
    }

    @Nullable
    public String getByUUID(UUID playerUUID) {
        String sql = "SELECT json FROM player_bank_contents WHERE uuid = ?";

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
            throw new RuntimeException("Failed to retrieve json for UUID: " + playerUUID + " " + e);
        }
    }

    public void save(UUID playerUUID, String content) {
        String sql = """
                INSERT INTO player_bank_contents (uuid, json)
                VALUES (?, ?)
                ON DUPLICATE KEY UPDATE
                json = VALUES(json),
                updated_at = CURRENT_TIMESTAMP
                """;

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerUUID.toString());
            stmt.setString(2, content);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to upsert bank content ", e);
        }
    }
}
