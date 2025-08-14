package org.zuma.examplePlugin.domain.player.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.security.auth.login.AccountNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class RunePlayerRepository {
    private static final Gson gson = new GsonBuilder().create();
    private final HikariPool pool;

    public RunePlayerRepository(HikariPool pool) {
        this.pool = pool;
    }

    public RunePlayer getPlayerByUUID(UUID uuid) throws AccountNotFoundException, IllegalAccessException {
        String sql = "SELECT uuid, last_username, online, json FROM players WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new AccountNotFoundException("Player not found with UUID: " + uuid);
                }

                String json = rs.getString("json");

                if (rs.getBoolean("online")) {
                    throw new IllegalAccessException("Player is already online.");
                }

                return RunePlayerSerializer.deserializePlayer(uuid, gson.fromJson(json, VersionedJson.class));
            }

        } catch (IllegalAccessException | AccountNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve player by UUID", e);
        }
    }

    public void upsertPlayer(RunePlayer player, String lastUsername, String inventoryContent) {
        String playerSql = """
                INSERT INTO players (uuid, last_username, online, json, updated_at)
                VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                    last_username = VALUES(last_username),
                    online = VALUES(online),
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

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement playerStmt = conn.prepareStatement(playerSql);
             PreparedStatement inventoryStmt = conn.prepareStatement(inventorySql)) {

            // Start transaction
            conn.setAutoCommit(false);

            // Upsert player
            playerStmt.setString(1, player.getUUID().toString());
            playerStmt.setString(2, lastUsername);
            playerStmt.setBoolean(3, false);
            playerStmt.setString(4, gson.toJson(RunePlayerSerializer.serializePlayer(player)));
            playerStmt.executeUpdate();

            // Upsert inventory
            inventoryStmt.setString(1, player.getUUID().toString());
            inventoryStmt.setString(2, inventoryContent);
            inventoryStmt.executeUpdate();

            // Commit transaction
            conn.commit();

        } catch (Exception e) {
            try {
                if (pool.getDataSource().getConnection() != null) {
                    pool.getDataSource().getConnection().rollback();
                }
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException("Failed to upsert player and inventory data", e);
        }
    }


    public void setPlayerOnline(UUID uuid, boolean online) {
        String sql = "UPDATE players SET online = ? WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, online);
            stmt.setString(2, uuid.toString());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update player online status", e);
        }
    }

    public String getInventoryByUUID(UUID uuid) throws AccountNotFoundException {
        String sql = "SELECT uuid, json FROM player_inventory_contents WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new AccountNotFoundException("Players inventory not found with UUID: " + uuid);
                }

                return rs.getString("json");
            }

        } catch (AccountNotFoundException e) {
            return "{}";
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve players inventory by UUID", e);
        }
    }

}
