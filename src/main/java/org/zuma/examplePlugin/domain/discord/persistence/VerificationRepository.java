package org.zuma.examplePlugin.domain.discord.persistence;

import org.zuma.examplePlugin.core.database.HikariPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class VerificationRepository {
    private final HikariPool pool;

    public VerificationRepository(HikariPool pool) {
        this.pool = pool;
    }

    public void addVerificationCode(UUID playerUUID, String code) throws SQLException {
        String sql = """
                INSERT INTO discord_verification (uuid, code)
                VALUES (?, ?)""";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerUUID.toString());
            stmt.setString(2, code);
            stmt.executeUpdate();

        }
    }

    public void overwriteVerificationCode(UUID playerUUID, String code) throws SQLException {
        String sql = """
                UPDATE discord_verification
                SET code = ?
                WHERE uuid = ?""";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            stmt.setString(2, playerUUID.toString());
            stmt.executeUpdate();

        }
    }

    public boolean doesVerificationCodeExist(UUID playerUUID) throws SQLException {
        String sql = "SELECT 1 FROM discord_verification WHERE uuid = ? LIMIT 1";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerUUID.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean isPlayerVerified(UUID playerUUID) throws SQLException {
        String sql = "SELECT 1 FROM discord_verification WHERE uuid = ? AND discord_tag IS NOT NULL LIMIT 1";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerUUID.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

}
