package org.zuma.examplePlugin.domain.referral.persistence;

import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.referral.ReferralEntry;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReferralRepository {
    private final HikariPool pool;

    public ReferralRepository(HikariPool pool) {
        this.pool = pool;
    }

    public void abandonMentorForMentee(UUID menteeUUID) throws SQLException, IllegalStateException {
        String deleteSql = """
                DELETE FROM player_referrals
                WHERE mentee_uuid = ? AND mentor_reward_claimed = FALSE AND mentee_reward_claimed = FALSE
                """;

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSql)) {

            stmt.setString(1, menteeUUID.toString());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalStateException(
                        "Cannot abandon mentorship because rewards have already been claimed or no such mentorship exists."
                );
            }
        }
    }

    public void addPlayerAsMentor(UUID mentorUUID, UUID menteeUUID) throws SQLException, IllegalStateException {
        String existingCheck = """
                SELECT 1 FROM player_referrals
                WHERE mentee_uuid = ?
                LIMIT 1
                """;

        String insertSql = """
                INSERT INTO player_referrals (mentor_uuid, mentee_uuid)
                VALUES (?, ?)
                """;

        try (Connection conn = pool.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(existingCheck)) {
                checkStmt.setString(1, menteeUUID.toString());

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        conn.rollback();
                        throw new IllegalStateException(
                                "You already have a mentor assigned."
                        );
                    }
                }
            }


            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, mentorUUID.toString());
                insertStmt.setString(2, menteeUUID.toString());
                insertStmt.executeUpdate();
            }

            conn.commit();
        }
    }

    public List<ReferralEntry> getMenteesForMentor(UUID mentorUUID) throws SQLException {
        String sql = """
                SELECT
                    pr.mentor_uuid,
                    pr.mentee_uuid,
                    pr.mentor_reward_claimed,
                    pr.mentee_reward_claimed,
                    pr.created_at,
                    mentor.last_username AS mentor_name,
                    mentee.last_username AS mentee_name,
                    ps.xp
                FROM player_referrals pr
                INNER JOIN players mentor ON pr.mentor_uuid = mentor.uuid
                INNER JOIN players mentee ON pr.mentee_uuid = mentee.uuid
                LEFT JOIN player_skills ps ON pr.mentee_uuid = ps.uuid
                WHERE pr.mentor_uuid = ?
                """;

        Map<UUID, ReferralEntry> mentees = new LinkedHashMap<>();

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mentorUUID.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UUID menteeUUID = UUID.fromString(rs.getString("mentee_uuid"));
                    int level = XpUtils.getLevelFromXp(rs.getDouble("xp"));


                    if (mentees.containsKey(menteeUUID)) {
                        // accumulate total level
                        ReferralEntry existing = mentees.get(menteeUUID);
                        int newTotal = existing.totalLevel() + level;

                        // replace with updated total level
                        mentees.put(menteeUUID, new ReferralEntry(
                                existing.mentorUsername(),
                                existing.menteeUsername(),
                                existing.mentorUUID(),
                                existing.menteeUUID(),
                                newTotal,
                                existing.isMentorRewardClaimed(),
                                existing.isMenteeRewardClaimed(),
                                existing.creationDate()
                        ));
                    } else {
                        // first time we see this mentee
                        mentees.put(menteeUUID, new ReferralEntry(
                                rs.getString("mentor_name"),
                                rs.getString("mentee_name"),
                                UUID.fromString(rs.getString("mentor_uuid")),
                                menteeUUID,
                                level,
                                rs.getBoolean("mentor_reward_claimed"),
                                rs.getBoolean("mentee_reward_claimed"),
                                rs.getTimestamp("created_at")
                        ));
                    }
                }
            }
        }

        return new ArrayList<>(mentees.values());
    }

    public UUID getPlayerUUIDByUsername(String partialUsername) throws SQLException, IllegalStateException {
        String sql = "SELECT uuid FROM players WHERE last_username LIKE ? LIMIT 1";

        try (Connection conn = pool.getDataSource().getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, partialUsername);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return UUID.fromString(rs.getString("uuid"));
                    } else {
                        throw new IllegalStateException(
                                "Player with username '" + partialUsername + "' not found."
                        );
                    }
                }
            }
        }
    }

    public int getPlayerTotalLevel(UUID playerUUID) throws SQLException {
        String skillsSql = "SELECT xp FROM player_skills WHERE uuid = ?";

        int totalLevel = 0;

        try (Connection conn = pool.getDataSource().getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(skillsSql)) {
                stmt.setString(1, playerUUID.toString());

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        double xp = rs.getDouble("xp");
                        totalLevel += XpUtils.getLevelFromXp(xp);
                    }
                }
            }
        }

        return totalLevel;
    }

    @Nullable
    public ReferralEntry getMentorForMentee(UUID menteeUUID) throws SQLException {
        String referralSql = """
                    SELECT
                        pr.mentor_uuid AS mentor_uuid,
                        pr.mentee_uuid AS mentee_uuid,
                        pr.mentor_reward_claimed,
                        pr.mentee_reward_claimed,
                        pr.created_at,
                        mentor.last_username AS mentor_name,
                        mentee.last_username AS mentee_name
                    FROM player_referrals pr
                    INNER JOIN players mentor ON pr.mentor_uuid = mentor.uuid
                    INNER JOIN players mentee ON pr.mentee_uuid = mentee.uuid
                    WHERE pr.mentee_uuid = ?
                    LIMIT 1
                """;


        int totalLevel = 0;

        try (Connection conn = pool.getDataSource().getConnection()) {
            totalLevel = getPlayerTotalLevel(menteeUUID);

            try (PreparedStatement stmt = conn.prepareStatement(referralSql)) {
                stmt.setString(1, menteeUUID.toString());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new ReferralEntry(
                                rs.getString("mentor_name"),
                                rs.getString("mentee_name"),
                                UUID.fromString(rs.getString("mentor_uuid")),
                                UUID.fromString(rs.getString("mentee_uuid")),
                                totalLevel,
                                rs.getBoolean("mentor_reward_claimed"),
                                rs.getBoolean("mentee_reward_claimed"),
                                rs.getTimestamp("created_at")
                        );
                    }
                }
            }
        }


        return null;
    }

    public void claimMentorReward(UUID mentorUUID, UUID menteeUUID) throws SQLException {
        String sql = """
                UPDATE player_referrals
                SET mentor_reward_claimed = TRUE
                WHERE mentor_uuid = ? AND mentee_uuid = ? AND mentor_reward_claimed = FALSE
                """;

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mentorUUID.toString());
            stmt.setString(2, menteeUUID.toString());
            int rowsChanged = stmt.executeUpdate();

            if (rowsChanged == 0) {
                throw new IllegalStateException(
                        "No mentorship found to claim reward from."
                );
            }
        }
    }

    public void claimMenteeReward(UUID menteeUUID, UUID mentorUUID) throws SQLException {
        String sql = """
                UPDATE player_referrals
                SET mentee_reward_claimed = TRUE
                WHERE mentee_uuid = ? AND mentor_uuid = ? and mentee_reward_claimed = FALSE
                """;

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, menteeUUID.toString());
            stmt.setString(2, mentorUUID.toString());
            int rowsChanged = stmt.executeUpdate();

            if (rowsChanged == 0) {
                throw new IllegalStateException(
                        "No mentorship found to claim reward from."
                );
            }
        }
    }
}
