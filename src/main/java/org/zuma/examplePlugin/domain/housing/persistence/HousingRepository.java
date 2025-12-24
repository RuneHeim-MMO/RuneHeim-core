package org.zuma.examplePlugin.domain.housing.persistence;

import org.bukkit.block.BlockFace;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.housing.records.HouseFurnitureEntry;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;
import org.zuma.examplePlugin.domain.housing.HousingRegion;
import org.zuma.examplePlugin.domain.housing.records.HouseEntry;
import org.zuma.examplePlugin.domain.housing.template.HouseTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HousingRepository {
    private final HikariPool pool;

    public HousingRepository(HikariPool pool) {
        this.pool = pool;
    }

    public List<HouseEntry> getPlayerHouses(UUID uuid) throws SQLException {
        String sql = """
                SELECT
                    ph.uuid AS house_uuid,
                    ph.owner_uuid,
                    ph.region,
                    ph.template,
                    hf.uuid AS furniture_uuid,
                    hf.item_id,
                    hf.slot_index,
                    hf.face,
                    hf.type,
                    hf.json
                FROM player_houses ph
                LEFT JOIN house_furniture hf
                    ON hf.uuid = ph.uuid
                WHERE ph.owner_uuid = ?;
                """;

        Map<UUID, HouseEntry> houseMap = new HashMap<>();

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        UUID houseUUID = UUID.fromString(rs.getString("house_uuid"));
                        HouseEntry house = houseMap.get(houseUUID);

                        if (!houseMap.containsKey(houseUUID)) {
                            house = new HouseEntry(
                                    houseUUID,
                                    UUID.fromString(rs.getString("owner_uuid")),
                                    HousingRegion.valueOf(rs.getString("region")),
                                    HouseTemplate.valueOf(rs.getString("template")),
                                    new HashMap<>()
                            );

                            houseMap.put(houseUUID, house);
                        }


                        String furnitureUUIDStr = rs.getString("furniture_uuid");

                        if (furnitureUUIDStr != null) {
                            HouseFurnitureEntry furniture = new HouseFurnitureEntry(
                                    UUID.fromString(furnitureUUIDStr),
                                    rs.getInt("item_id"),
                                    rs.getInt("slot_index"),
                                    BlockFace.valueOf(rs.getString("face")),
                                    HouseSlot.HouseSlotType.valueOf(rs.getString("type")),
                                    rs.getString("json")
                            );

                            house.furniture().put(rs.getInt("slot_index"), furniture);
                        }

                    } catch (Exception e) {
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to parse house entry for player " + uuid + "!", e);
                    }
                }
            }
        }

        return new ArrayList<>(houseMap.values());
    }

    public void savePlayerHouses(List<HouseEntry> houses) throws SQLException {
        if (houses.isEmpty()) return;

        String houseSql = """
                INSERT INTO player_houses (uuid, owner_uuid, region, template)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    owner_uuid = VALUES(owner_uuid),
                    region = VALUES(region),
                    template = VALUES(template)
                """;

        String furnitureSql = """
                INSERT INTO house_furniture
                (uuid, item_id, slot_index, face, type, json)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = pool.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement houseStmt = conn.prepareStatement(houseSql);
                 PreparedStatement furnitureStmt = conn.prepareStatement(furnitureSql)) {

                // --- UPSERT HOUSES ---
                for (HouseEntry house : houses) {
                    houseStmt.setString(1, house.uuid().toString());
                    houseStmt.setString(2, house.ownerUUID().toString());
                    houseStmt.setString(3, house.region().name());
                    houseStmt.setString(4, house.template().name());
                    houseStmt.addBatch();
                }
                houseStmt.executeBatch();

                // --- DELETE ALL FURNITURE FOR THESE HOUSES IN ONE STATEMENT ---
                StringBuilder deleteSql = new StringBuilder("DELETE FROM house_furniture WHERE uuid IN (");
                for (int i = 0; i < houses.size(); i++) {
                    deleteSql.append("?");
                    if (i < houses.size() - 1) deleteSql.append(",");
                }
                deleteSql.append(")");

                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql.toString())) {
                    for (int i = 0; i < houses.size(); i++) {
                        deleteStmt.setString(i + 1, houses.get(i).uuid().toString());
                    }
                    deleteStmt.executeUpdate();
                }

                // --- INSERT ALL FURNITURE IN ONE BATCH ---
                for (HouseEntry house : houses) {
                    for (HouseFurnitureEntry furniture : house.furniture().values()) {
                        furnitureStmt.setString(1, house.uuid().toString());
                        furnitureStmt.setInt(2, furniture.itemId());
                        furnitureStmt.setInt(3, furniture.slotIndex());
                        furnitureStmt.setString(4, furniture.face().name());
                        furnitureStmt.setString(5, furniture.type().name());
                        furnitureStmt.setString(6, furniture.json());
                        furnitureStmt.addBatch();
                    }
                }
                furnitureStmt.executeBatch();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void deleteHouse(UUID houseUUID) throws SQLException {
        String sql = "DELETE FROM player_houses WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, houseUUID.toString());
            stmt.executeUpdate();
        }
    }
}
