package org.zuma.examplePlugin.domain.player.persistence;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.permissions.Role;
import org.zuma.examplePlugin.core.permissions.roles.BaseRole;
import org.zuma.examplePlugin.core.permissions.roles.UpgradedRole;

import javax.annotation.Nullable;
import javax.security.auth.login.AccountNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RunePlayerRepository {
    private final HikariPool pool;

    public RunePlayerRepository(HikariPool pool) {
        this.pool = pool;
    }

    public SerializedPlayerContainer getPlayerByUUID(UUID uuid) throws AccountNotFoundException, IllegalAccessException {
        String sql = "SELECT p.uuid, p.last_username, p.base_role, p.upgraded_role, p.online, p.location, p.json, pic.json AS inventory_json " +
                "FROM players p " +
                "INNER JOIN player_inventory_contents pic ON p.uuid = pic.uuid " +
                "WHERE p.uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new AccountNotFoundException("Player not found with UUID: " + uuid);
                }

                String json = rs.getString("json");
                String lastUsername = rs.getString("last_username");
                String baseRole = rs.getString("base_role");
                String upgradedRole = rs.getString("upgraded_role");
                String inventoryData = rs.getString("inventory_json");
                String location = rs.getString("location");
                boolean isOnline = rs.getBoolean("online");

                if (isOnline) {
                    throw new IllegalAccessException("Player is already online.");
                }

                return new SerializedPlayerContainer(uuid, lastUsername, baseRole, upgradedRole, location, json, inventoryData == null ? "{}" : inventoryData);
            }

        } catch (IllegalAccessException | AccountNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve player." + System.currentTimeMillis(), e);
        }
    }

    public void upsertPlayer(SerializedPlayerContainer container) {
        String playerSql = """
                INSERT INTO players (uuid, last_username, base_role, upgraded_role, online, location, json, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                    last_username = VALUES(last_username),
                    base_role = VALUES(base_role),
                    upgraded_role = VALUES(upgraded_role),
                    online = VALUES(online),
                    location = VALUES(location),
                    json = VALUES(json),
                    updated_at = CURRENT_TIMESTAMP
                """;

        String inventorySql = """
                INSERT INTO player_inventory_contents (uuid, json, updated_at)
                VALUES (?, ?, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                    json = VALUES(json),
                    updated_at = CURRENT_TIMESTAMP
                """;

        Connection conn = null;
        try {
            conn = pool.getDataSource().getConnection();
            conn.setAutoCommit(false); // start transaction

            try (PreparedStatement playerStmt = conn.prepareStatement(playerSql);
                 PreparedStatement inventoryStmt = conn.prepareStatement(inventorySql)) {

                // Upsert player
                playerStmt.setString(1, container.uuid().toString());
                playerStmt.setString(2, container.username());
                playerStmt.setString(3, container.baseRole());
                playerStmt.setString(4, container.upgradedRole());
                playerStmt.setBoolean(5, false);
                playerStmt.setString(6, container.location());
                playerStmt.setString(7, container.playerData());
                playerStmt.executeUpdate();

                // Upsert inventory
                inventoryStmt.setString(1, container.uuid().toString());
                inventoryStmt.setString(2, container.inventoryData());
                inventoryStmt.executeUpdate();

                // Commit transaction
                conn.commit();
            }

        } catch (Exception e) {
            // Rollback transaction if something failed
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception rollbackEx) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,
                            "Rollback failed for player " + container.username() + "!", rollbackEx);
                }
            }
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,
                    "Failed to upsert player " + container.username() + " data!", e);
        } finally {
            // Close connection safely
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // reset autocommit
                    conn.close();
                } catch (Exception closeEx) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,
                            "Failed to close connection for player " + container.username() + "!", closeEx);
                }
            }
        }
    }

    public void setPlayerOnline(UUID uuid, boolean online) {
        String sql = "UPDATE players SET online = ?, updated_at = NOW() WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, online);
            stmt.setString(2, uuid.toString());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update player online status", e);
        }
    }

    public void setPlayerUpgradedRole(UUID uuid, UpgradedRole newRole) {
        try {
            String sql = "UPDATE players SET upgraded_role = ?, updated_at = NOW() WHERE uuid = ?";

            try (Connection conn = pool.getDataSource().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, newRole.toString());
                stmt.setString(2, uuid.toString());

                stmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update player upgraded role", e);
        }
    }

    public List<Role> getPlayerRoles(UUID uuid) {
        try {
            String sql = "SELECT base_role, upgraded_role FROM players WHERE uuid = ?";
            List<Role> roles = new ArrayList<>();

            try (Connection conn = pool.getDataSource().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, uuid.toString());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        roles.add(BaseRole.valueOf(rs.getString("base_role")));
                        String upgradedRoleStr = rs.getString("upgraded_role");
                        roles.add(upgradedRoleStr != null ? UpgradedRole.valueOf(upgradedRoleStr) : null);
                    }
                }

                return roles;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve player roles", e);
        }
    }

}
