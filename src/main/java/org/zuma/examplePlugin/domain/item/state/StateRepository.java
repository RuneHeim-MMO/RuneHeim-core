package org.zuma.examplePlugin.domain.item.state;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.domain.player.persistence.SerializedPlayerContainer;

import javax.security.auth.login.AccountNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.UUID;

public class StateRepository {
    private final HikariPool pool;
    private final Gson gson;

    public StateRepository(HikariPool pool) {
        this.pool = pool;
        this.gson = new Gson();
    }

    public Map.Entry<Long, AbstractStateMeta> getByUUID(UUID uuid) {
        String sql = "SELECT created_at, json FROM item_states WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String json = rs.getString("json");
                long createdAt = rs.getDate("created_at").getTime();

                return Map.entry(createdAt, gson.fromJson(json, AbstractStateMeta.class));
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to get items state.", e);
        }
    }

    public void upsert(UUID uuid, StateType type, String json) {
        String sql = "INSERT INTO item_states (uuid, type, json, created_at, updated_at) " +
                "VALUES (?, ?, ?, NOW(), NOW()) " +
                "ON DUPLICATE KEY UPDATE json = VALUES(json), updated_at = NOW()";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());
            stmt.setString(2, type.name());
            stmt.setString(3, json);

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to upsert item state.", e);
        }
    }

    public void deleteState(UUID uuid) {
        String sql = "DELETE FROM item_states WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete item state.", e);
        }
    }

}
