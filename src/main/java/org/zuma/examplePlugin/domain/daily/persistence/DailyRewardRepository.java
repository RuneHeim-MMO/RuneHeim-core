package org.zuma.examplePlugin.domain.daily.persistence;

import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.domain.daily.DailyRewards;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.UUID;

public class DailyRewardRepository {
    private final HikariPool pool;

    public DailyRewardRepository(HikariPool pool) {
        this.pool = pool;
    }

    public void upsertRewardData(UUID uuid, DailyRewards rewardData) {
        String streakSql = """
                INSERT INTO player_daily_streaks (uuid, streak, today_claimed, updated_at)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                streak = VALUES(streak),
                today_claimed = VALUES(today_claimed),
                updated_at = VALUES(updated_at);
                """;

        String rewardSql = """
                INSERT INTO player_daily_rewards (uuid, streak_day, item_id, min, max, created_at)
                VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                item_id = VALUES(item_id),
                min = VALUES(min),
                max = VALUES(max),
                created_at = CURRENT_TIMESTAMP;
                """;

        Connection conn = null;

        try {
            conn = pool.getDataSource().getConnection();
            conn.setAutoCommit(false);

            try (var streakStmt = conn.prepareStatement(streakSql)) {
                streakStmt.setString(1, uuid.toString());
                streakStmt.setInt(2, rewardData.getCurrentStreak());
                streakStmt.setBoolean(3, rewardData.isTodayClaimed());

                long epochSeconds = rewardData.getLastUpdateTimestamp() / 1000;
                Timestamp ts = new Timestamp(epochSeconds * 1000);
                streakStmt.setTimestamp(4, ts);

                streakStmt.executeUpdate();
            }

            try (var rewardStmt = conn.prepareStatement(rewardSql)) {
                for (var entry : rewardData.getRewardEntries().entrySet()) {
                    int streakDay = entry.getKey();
                    var rewardEntry = entry.getValue();

                    rewardStmt.setString(1, uuid.toString());
                    rewardStmt.setInt(2, streakDay);
                    rewardStmt.setInt(3, rewardEntry.itemId());
                    rewardStmt.setInt(4, rewardEntry.min());
                    rewardStmt.setInt(5, rewardEntry.max());
                    rewardStmt.addBatch();
                }
                rewardStmt.executeBatch();
            }

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception ignored) {
                }
            }
            throw new RuntimeException("Failed to upsert daily reward data for UUID: " + uuid, e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ignored) {
                }
            }
        }


    }

    public DailyRewards getByUUID(UUID playerUUID) {
        String rewardsSql = "SELECT streak, today_claimed, updated_at FROM player_daily_streaks WHERE uuid = ?";
        String streaksSql = "SELECT streak_day, item_id, min, max FROM player_daily_rewards WHERE uuid = ?";

        try {
            Connection conn = pool.getDataSource().getConnection();

            int currentStreak = 1;
            boolean todayClaimed = false;
            long updatedAt = System.currentTimeMillis();

            try (var streakStmt = conn.prepareStatement(rewardsSql)) {
                streakStmt.setString(1, playerUUID.toString());

                try (var rs = streakStmt.executeQuery()) {
                    if (rs.next()) {
                        currentStreak = rs.getInt("streak");
                        todayClaimed = rs.getBoolean("today_claimed");
                        updatedAt = rs.getTimestamp("updated_at").getTime();
                    }
                }
            }

            var rewardEntries = new java.util.HashMap<Integer, org.zuma.examplePlugin.domain.daily.DailyRewardEntry>();

            try (var rewardsStmt = conn.prepareStatement(streaksSql)) {
                rewardsStmt.setString(1, playerUUID.toString());

                try (var rs = rewardsStmt.executeQuery()) {
                    while (rs.next()) {
                        int streakDay = rs.getInt("streak_day");
                        int itemId = rs.getInt("item_id");
                        int min = rs.getInt("min");
                        int max = rs.getInt("max");

                        rewardEntries.put(streakDay, new org.zuma.examplePlugin.domain.daily.DailyRewardEntry(itemId, min, max));
                    }
                }
            }

            conn.close();

            return new DailyRewards(currentStreak, updatedAt, todayClaimed, rewardEntries);

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve daily reward data for UUID: " + playerUUID, e);
        }

    }
}
