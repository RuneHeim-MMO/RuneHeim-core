package org.zuma.examplePlugin.domain.block.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.adapters.LocationAdapter;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;

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
