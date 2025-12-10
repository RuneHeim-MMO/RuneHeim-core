package org.zuma.examplePlugin.domain.block.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Door;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.adapters.LocationAdapter;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.HologramSide;
import org.zuma.examplePlugin.domain.block.meta.v1.DoorBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.v1.ItemCollectorTriggerMeta;
import org.zuma.examplePlugin.domain.block.meta.v1.QuestLockedMeta;
import org.zuma.examplePlugin.domain.block.meta.v1.TeleportDoorMeta;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InteractableBlockRepository {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Location.class, new LocationAdapter())
            .create();
    private final HikariPool pool;

    public InteractableBlockRepository(HikariPool pool) {
        this.pool = pool;
    }

    public List<Long> insertBlocks(List<BlockEntity> entities) {
        String sql = "INSERT INTO blocks (block_type, block_face, world_name, x, y, z, hologram_location, json) VALUES (?,?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (BlockEntity entity : entities) {
                stmt.setString(1, entity.getType().toString());
                stmt.setString(2, entity.getFace().toString());
                stmt.setString(3, entity.getLocation().getWorld().getName());
                stmt.setDouble(4, entity.getLocation().x());
                stmt.setDouble(5, entity.getLocation().y());
                stmt.setDouble(6, entity.getLocation().z());
                stmt.setString(7, entity.getHologramLocation() != null ? gson.toJson(entity.getHologramLocation()) : null);
                stmt.setString(8, gson.toJson(entity.getJson()));
                stmt.addBatch();
            }

            stmt.executeBatch();

            List<Long> insertedIds = new ArrayList<>();
            ResultSet rs = stmt.getGeneratedKeys();

            while (rs.next()) {
                insertedIds.add(rs.getLong(1));
            }

            return insertedIds;

        } catch (Exception e) {
            throw new RuntimeException("Failed to insert block entities", e);
        }
    }


    public List<BlockEntity> getAll() {
        String sql = "SELECT id, block_type, block_face, world_name, x, y, z, hologram_location, json FROM blocks";
        List<BlockEntity> result = new ArrayList<>();

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BlockEntity entity = deserializeBlockEntity(rs);
                result.add(entity);
            }

            result.add(new BlockEntity(
                    new AbstractInteractableBlockMeta(9999, BlockType.Door, BlockFace.EAST, new Location(Bukkit.getWorld("world"), 645, 40, -230), HologramSide.FRONT),
                    new VersionedJson(new Gson().toJson(new DoorBlockMeta("lava-dungeon-door", 7, 5)), 1)));

            result.add(new BlockEntity(
                    new AbstractInteractableBlockMeta(9998, BlockType.ItemTrigger, BlockFace.EAST, new Location(Bukkit.getWorld("world"), 640, 39, -242), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new ItemCollectorTriggerMeta("lava-dungeon-door-group", "lava-dungeon-door", 15, List.of(new ItemRecord(ItemId.LAVA_DUNGEON_KEY_D, 1)))), 1)));

            result.add(new BlockEntity(
                    new AbstractInteractableBlockMeta(9997, BlockType.ItemTrigger, BlockFace.EAST, new Location(Bukkit.getWorld("world"), 640, 39, -239), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new ItemCollectorTriggerMeta("lava-dungeon-door-group", "lava-dungeon-door", 15, List.of(new ItemRecord(ItemId.LAVA_DUNGEON_KEY_C, 1)))), 1)));

            result.add(new BlockEntity(
                    new AbstractInteractableBlockMeta(9996, BlockType.ItemTrigger, BlockFace.EAST, new Location(Bukkit.getWorld("world"), 640, 39, -236), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new ItemCollectorTriggerMeta("lava-dungeon-door-group", "lava-dungeon-door", 15, List.of(new ItemRecord(ItemId.LAVA_DUNGEON_KEY_B, 1)))), 1)));

            result.add(new BlockEntity(
                    new AbstractInteractableBlockMeta(9995, BlockType.ItemTrigger, BlockFace.EAST, new Location(Bukkit.getWorld("world"), 640, 39, -233), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new ItemCollectorTriggerMeta("lava-dungeon-door-group", "lava-dungeon-door", 15, List.of(new ItemRecord(ItemId.LAVA_DUNGEON_KEY_A, 1)))), 1)));

            result.add(new BlockEntity(
                    new AbstractInteractableBlockMeta(9994, BlockType.TeleportDoor, BlockFace.WEST, new Location(Bukkit.getWorld("world"), 796, 15, -221), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new TeleportDoorMeta("lava-dungeon-exit", "Exit", "world", 800, 86, -218, 127, Door.Hinge.RIGHT, null)), 1)));

            result.add(new BlockEntity(
                    new AbstractInteractableBlockMeta(9993, BlockType.TeleportDoor, BlockFace.WEST, new Location(Bukkit.getWorld("world"), 255, 73, -54), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new TeleportDoorMeta("drakkar-bank-back-office", "Back office", "world", 258, 73, -56, -170, Door.Hinge.LEFT,
                            new QuestLockedMeta("This door appears to be locked.", null, null, List.of("welcome-to-drakkar.kill.rats", "welcome-to-drakkar.enter.back-office")))), 1)));

            result.add(new BlockEntity(
                    new AbstractInteractableBlockMeta(9992, BlockType.TeleportDoor, BlockFace.NORTH, new Location(Bukkit.getWorld("world"), 171, 73, -30), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new TeleportDoorMeta("drakkar-smeltery-door", "", "world", 3264, 7, 3227, 90, Door.Hinge.RIGHT, new QuestLockedMeta("This door appears to be locked.", null, null, null))), 1)));

            result.add(new BlockEntity(
                    new AbstractInteractableBlockMeta(9991, BlockType.TeleportDoor, BlockFace.NORTH, new Location(Bukkit.getWorld("world"), 170, 73, -30), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new TeleportDoorMeta("drakkar-smeltery-door", "", "world", 3264, 7, 3227, 90, Door.Hinge.LEFT, new QuestLockedMeta("This door appears to be locked.", null, null, null))), 1)));


            result.add(new BlockEntity(
                    new AbstractInteractableBlockMeta(9990, BlockType.TeleportDoor, BlockFace.EAST, new Location(Bukkit.getWorld("world"), 3265, 7, 3226), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new TeleportDoorMeta("drakkar-smeltery-to-drakkar-door", "", "world", 171, 73, -30, -180, Door.Hinge.LEFT, new QuestLockedMeta("This door appears to be locked.", null, null, null))), 1)));

            result.add(new BlockEntity(
                    new AbstractInteractableBlockMeta(9989, BlockType.TeleportDoor, BlockFace.EAST, new Location(Bukkit.getWorld("world"), 3265, 7, 3227), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new TeleportDoorMeta("drakkar-smeltery-to-drakkar-door", "", "world", 171, 73, -30, -180, Door.Hinge.RIGHT, new QuestLockedMeta("This door appears to be locked.", null, null, null))), 1)));


        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch block entities", e);
        }

        return result;
    }

    @Nullable
    public BlockEntity getById(long id) {
        String sql = "SELECT id, block_type, block_face, world_name, x, y, z, hologram_location, json FROM blocks WHERE id = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return deserializeBlockEntity(rs);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch block entity", e);
        }

        return null;
    }

    public void deleteByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return;

        String sql = "DELETE FROM blocks WHERE id = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Long id : ids) {
                stmt.setLong(1, id);
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete block entities by IDs", e);
        }
    }

    @Nullable
    private BlockEntity deserializeBlockEntity(ResultSet rs) {
        try {
            int id = rs.getInt("id");
            String blockTypeStr = rs.getString("block_type");
            String blockFaceStr = rs.getString("block_face");
            String worldName = rs.getString("world_name");
            double x = rs.getDouble("x");
            double y = rs.getDouble("y");
            double z = rs.getDouble("z");
            String holoJson = rs.getString("hologram_location");
            String json = rs.getString("json");

            BlockType blockType = BlockType.valueOf(blockTypeStr);
            BlockFace blockFace = BlockFace.valueOf(blockFaceStr);
            World world = Bukkit.getWorld(worldName);
            Location location = new Location(world, x, y, z);

            Location hologramLocation = null;
            if (holoJson != null) {
                hologramLocation = gson.fromJson(holoJson, Location.class);
            }

            VersionedJson versionedJson = gson.fromJson(json, VersionedJson.class);

            return new BlockEntity(
                    new AbstractInteractableBlockMeta(id, blockType, blockFace, location, hologramLocation),
                    versionedJson
            );

        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to deserialize block entity! " + e);
            return null;
        }
    }
}
