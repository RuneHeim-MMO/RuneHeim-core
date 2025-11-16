package org.zuma.examplePlugin.domain.player.persistence;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.permissions.Role;
import org.zuma.examplePlugin.core.permissions.roles.BaseRole;
import org.zuma.examplePlugin.core.permissions.roles.UpgradedRole;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.player.PlayerBombsContainer;
import org.zuma.examplePlugin.domain.player.settings.PlayerSetting;
import org.zuma.examplePlugin.domain.player.settings.PlayerSettings;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.security.auth.login.AccountNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RunePlayerRepository {
    private final HikariPool pool;

    public RunePlayerRepository(HikariPool pool) {
        this.pool = pool;
    }

    private void checkMissingSkills(Connection conn, UUID uuid, List<Skill> currentSkills) throws SQLException {
        // Ensure all skills are present in the database
        Set<SkillType> missingSkills = new HashSet<>();
        for (SkillType type : SkillType.values()) {
            if (currentSkills.stream().noneMatch(s -> s.getType() == type)) {
                missingSkills.add(type);
            }
        }

        if (!missingSkills.isEmpty()) {
            String insertSkillSQL =
                    "INSERT INTO player_skills (uuid, skill, xp, level, updated_at) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSkillSQL)) {
                for (SkillType type : missingSkills) {
                    insertStmt.setString(1, uuid.toString());
                    insertStmt.setString(2, type.toString());
                    insertStmt.setInt(3, type.getDefaultXp());
                    insertStmt.setInt(4, XpUtils.getLevelFromXp(type.getDefaultXp()));
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }
        }
    }

    public SerializedPlayerContainer getPlayerByUUID(UUID uuid)
            throws AccountNotFoundException, IllegalAccessException {

        String playerSQL =
                "SELECT p.uuid, p.last_username, p.base_role, p.upgraded_role, p.online, p.location, p.json, " +
                        "pic.json AS inventory_json, " +
                        "pb.double_combat_xp, pb.double_skill_xp, pb.mob_drops, pb.resource_drops, pb.perfect_strike, pb.megaphone_uses, " +
                        "pqd.tracking_quest_id " +
                        "FROM players p " +
                        "INNER JOIN player_inventory_contents pic ON p.uuid = pic.uuid " +
                        "INNER JOIN player_bonus_bombs pb ON p.uuid = pb.uuid " +
                        "INNER JOIN player_quest_data pqd ON p.uuid = pqd.uuid " +
                        "WHERE p.uuid = ?";

        String skillsSQL =
                "SELECT skill, xp FROM player_skills WHERE uuid = ?";

        String settingsSQL =
                "SELECT setting, value FROM player_settings WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection()) {

            String json, lastUsername, baseRole, upgradedRole, inventoryData, location, trackedQuestId;
            boolean isOnline;
            PlayerBombsContainer bombsContainer;

            // Fetch main player record
            try (PreparedStatement stmt = conn.prepareStatement(playerSQL)) {
                stmt.setString(1, uuid.toString());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new AccountNotFoundException("Player not found with UUID: " + uuid);
                    }

                    json = rs.getString("json");
                    lastUsername = rs.getString("last_username");
                    baseRole = rs.getString("base_role");
                    upgradedRole = rs.getString("upgraded_role");
                    inventoryData = rs.getString("inventory_json");
                    location = rs.getString("location");
                    isOnline = rs.getBoolean("online");
                    trackedQuestId = rs.getString("tracking_quest_id");

                    if (isOnline) {
                        throw new IllegalAccessException("Player is already online.");
                    }

                    // Bombs
                    int doubleCombatXP = rs.getInt("double_combat_xp");
                    int doubleSkillXP = rs.getInt("double_skill_xp");
                    int mobDrops = rs.getInt("mob_drops");
                    int resourceDrops = rs.getInt("resource_drops");
                    int perfectStrike = rs.getInt("perfect_strike");
                    int megaphoneUses = rs.getInt("megaphone_uses");

                    bombsContainer = new PlayerBombsContainer(
                            doubleCombatXP, doubleSkillXP, mobDrops,
                            resourceDrops, perfectStrike, megaphoneUses
                    );
                }
            }

            List<Skill> skills = new ArrayList<>();

            try (PreparedStatement stmt = conn.prepareStatement(skillsSQL)) {
                stmt.setString(1, uuid.toString());

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        skills.add(new Skill(SkillType.valueOf(rs.getString("skill")), rs.getInt("xp")));
                    }
                }
            }

            checkMissingSkills(conn, uuid, skills);

            PlayerSettings settings = new PlayerSettings();
            try (PreparedStatement stmt = conn.prepareStatement(settingsSQL)) {
                stmt.setString(1, uuid.toString());

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        settings.setSetting(PlayerSetting.valueOf(rs.getString("setting")), rs.getBoolean("value"));
                    }
                }
            }

            return new SerializedPlayerContainer(
                    uuid,
                    lastUsername,
                    skills,
                    baseRole,
                    upgradedRole,
                    location,
                    null,
                    json,
                    inventoryData == null ? "{}" : inventoryData,
                    bombsContainer,
                    settings,
                    trackedQuestId
            );

        } catch (IllegalAccessException | AccountNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve player. " + System.currentTimeMillis(), e);
        }
    }

    public void upsertPlayer(SerializedPlayerContainer container, boolean unmount) {
        String playerSql = """
                INSERT INTO players (uuid, last_username, base_role, upgraded_role, online, location, last_territory, json, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                    last_username = VALUES(last_username),
                    base_role = VALUES(base_role),
                    upgraded_role = VALUES(upgraded_role),
                    online = CASE WHEN ? THEN NULL ELSE online END,
                    location = VALUES(location),
                    last_territory = VALUES(last_territory),
                    json = VALUES(json),
                    updated_at = CURRENT_TIMESTAMP
                """;

        String skillUpsertSql = """
                INSERT INTO player_skills (uuid, skill, xp, level)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    xp = VALUES(xp),
                    level = VALUES(level),
                    updated_at = CURRENT_TIMESTAMP
                """;

        String inventorySql = """
                INSERT INTO player_inventory_contents (uuid, json, updated_at)
                VALUES (?, ?, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                    json = VALUES(json),
                    updated_at = CURRENT_TIMESTAMP
                """;

        String bonusBombsSql = """
                INSERT INTO player_bonus_bombs (uuid, double_combat_xp, double_skill_xp, mob_drops, resource_drops, perfect_strike, megaphone_uses, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                    double_combat_xp = VALUES(double_combat_xp),
                    double_skill_xp = VALUES(double_skill_xp),
                    mob_drops = VALUES(mob_drops),
                    resource_drops = VALUES(resource_drops),
                    perfect_strike = VALUES(perfect_strike),
                    megaphone_uses = VALUES(megaphone_uses),
                    updated_at = CURRENT_TIMESTAMP
                """;

        String settingsSql = """
                INSERT INTO player_settings (uuid, setting, value, updated_At)
                VALUES (?, ?, ?, CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                    setting = VALUES(setting),
                    value = VALUES(value),
                    updated_at = CURRENT_TIMESTAMP
                """;

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement playerStmt = conn.prepareStatement(playerSql);
             PreparedStatement bombsStmt = conn.prepareStatement(bonusBombsSql);
             PreparedStatement inventoryStmt = conn.prepareStatement(inventorySql);
             PreparedStatement skillStmt = conn.prepareStatement(skillUpsertSql);
             PreparedStatement settingsStmt = conn.prepareStatement(settingsSql)) {

            // 1. Upsert player
            playerStmt.setString(1, container.uuid().toString());
            playerStmt.setString(2, container.username());
            playerStmt.setString(3, container.baseRole());
            playerStmt.setString(4, container.upgradedRole());
            playerStmt.setBoolean(5, false); // for INSERT (default to false)
            playerStmt.setString(6, container.location());
            playerStmt.setString(7, container.lastTerritory());
            playerStmt.setString(8, container.playerData());
            playerStmt.setBoolean(9, unmount); // for the CASE statement in UPDATE
            playerStmt.executeUpdate();

            // Skills - sort by skill name to ensure consistent ordering across transactions
            List<Skill> sortedSkills = new ArrayList<>(container.skills());
            sortedSkills.sort(Comparator.comparing(a -> a.getType().toString()));

            for (Skill skill : sortedSkills) {
                skillStmt.setString(1, container.uuid().toString());
                skillStmt.setString(2, skill.getType().toString());
                skillStmt.setDouble(3, skill.getXp());
                skillStmt.setInt(4, skill.getBaseLevel());
                skillStmt.addBatch();
            }

            skillStmt.executeBatch();

            // 3. Upsert inventory
            inventoryStmt.setString(1, container.uuid().toString());
            inventoryStmt.setString(2, container.inventoryData());
            inventoryStmt.executeUpdate();

            // 4. Upsert bombs
            bombsStmt.setString(1, container.uuid().toString());
            bombsStmt.setInt(2, container.bombsContainer().getDoubleCombatXPBombs());
            bombsStmt.setInt(3, container.bombsContainer().getDoubleSkillXPBombs());
            bombsStmt.setInt(4, container.bombsContainer().getIncreasedMobDropsBombs());
            bombsStmt.setInt(5, container.bombsContainer().getIncreasedResourceDropsBombs());
            bombsStmt.setInt(6, container.bombsContainer().getIncreasedPerfectStrikeBombs());
            bombsStmt.setInt(7, container.bombsContainer().getMegaphoneUses());
            bombsStmt.executeUpdate();

            Map<String, Boolean> sortedSettings = new TreeMap<>(container.settings().toMap());
            for (Map.Entry<String, Boolean> entry : sortedSettings.entrySet()) {
                settingsStmt.setString(1, container.uuid().toString());
                settingsStmt.setString(2, entry.getKey());
                settingsStmt.setBoolean(3, entry.getValue());
                settingsStmt.addBatch();
            }

            settingsStmt.executeBatch();

        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,
                    "Failed to upsert player " + container.username() + " data!", e);
        }
    }

    public void setPlayerOnline(UUID uuid, boolean online) {
        String sql = "UPDATE players SET online = ?, updated_at = NOW() WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, online ? ConfigManager.serverId : null);
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

    public PlayerBombsContainer getPlayerBombsContainer(UUID uuid) {
        String sql = "SELECT double_combat_xp, double_skill_xp, mob_drops, resource_drops, perfect_strike, megaphone_uses FROM player_bonus_bombs WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int doubleCombatXP = rs.getInt("double_combat_xp");
                    int doubleSkillXP = rs.getInt("double_skill_xp");
                    int mobDrops = rs.getInt("mob_drops");
                    int resourceDrops = rs.getInt("resource_drops");
                    int perfectStrike = rs.getInt("perfect_strike");
                    int megaphoneUses = rs.getInt("megaphone_uses");

                    return new PlayerBombsContainer(doubleCombatXP, doubleSkillXP, mobDrops, resourceDrops, perfectStrike, megaphoneUses);
                } else {
                    throw new RuntimeException("Player bombs container not found for UUID: " + uuid);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve player bombs container", e);
        }
    }

    public void updatePlayerBombsContainer(UUID uuid, PlayerBombsContainer bombsContainer) {
        String sql = "UPDATE player_bonus_bombs SET double_combat_xp = ?, double_skill_xp = ?, mob_drops = ?, resource_drops = ?, perfect_strike = ?, megaphone_uses = ?, updated_at = NOW() WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bombsContainer.getDoubleCombatXPBombs());
            stmt.setInt(2, bombsContainer.getDoubleSkillXPBombs());
            stmt.setInt(3, bombsContainer.getIncreasedMobDropsBombs());
            stmt.setInt(4, bombsContainer.getIncreasedResourceDropsBombs());
            stmt.setInt(5, bombsContainer.getIncreasedPerfectStrikeBombs());
            stmt.setInt(6, bombsContainer.getMegaphoneUses());
            stmt.setString(7, uuid.toString());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update player bombs container", e);
        }
    }

}
