package org.zuma.examplePlugin.domain.announcements.persistence;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.announcements.Announcement;
import org.zuma.examplePlugin.domain.payments.Payment;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnnouncementsRepository {
    private final HikariPool pool;

    public AnnouncementsRepository(HikariPool pool) {
        this.pool = pool;
    }

    public List<Announcement> getAll() {
        String sql = "SELECT id, message FROM announcements";
        List<Announcement> result = new ArrayList<>();

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new Announcement(rs.getInt("id"), rs.getString("message")));

                }
            }


        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,
                    "Failed to fetch announcements!", e);
        }

        return result;
    }

}
