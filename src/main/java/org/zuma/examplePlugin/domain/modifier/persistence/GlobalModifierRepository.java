package org.zuma.examplePlugin.domain.modifier.persistence;

import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.domain.modifier.GlobalModifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GlobalModifierRepository {
    private final HikariPool pool;

    public GlobalModifierRepository(HikariPool pool) {
        this.pool = pool;
    }

    public List<Map.Entry<Integer, GlobalModifier>> getAll() {
        String sql = "SELECT id, type, label, amount, active_until_ms FROM global_modifiers";
        List<Map.Entry<Integer, GlobalModifier>> result = new ArrayList<>();

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(
                        new java.util.AbstractMap.SimpleEntry<>(
                                rs.getInt("id"),
                                new GlobalModifier(
                                        ModifierType.valueOf(rs.getString("type")),
                                        rs.getString("label"),
                                        rs.getDouble("amount"),
                                        rs.getTimestamp("active_until_ms").getTime()
                                )
                        )
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch global modifiers", e);
        }

        return result;
    }

}
