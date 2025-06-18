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




/*

              /*  new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -92, 93, -145),
                        new Location(Bukkit.getWorld("world"), -40.5, 94.5, -98.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.TREE.asInt(), "tree", "tree_depleted")), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -71, 93, -145),
                        new Location(Bukkit.getWorld("world"), -40.5, 94.5, -98.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.BIRCH_TREE.asInt(), "birch_tree", "birch_tree_depleted")), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -45, 93, -146),
                        new Location(Bukkit.getWorld("world"), -40.5, 94.5, -98.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.MAPLE_TREE.asInt(), "maple_tree", "maple_tree_depleted")), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -1, 93, -153),
                        new Location(Bukkit.getWorld("world"), -40.5, 94.5, -98.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.YEW_TREE.asInt(), "yew_tree", "yew_tree_depleted")), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -21, 93, -140),
                        new Location(Bukkit.getWorld("world"), -40.5, 94.5, -98.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.MAHOGANY_TREE.asInt(), "mahogany_tree", "mahogany_tree_depleted")), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -35, 93, -126),
                        new Location(Bukkit.getWorld("world"), -40.5, 94.5, -98.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.MAGIC_TREE.asInt(), "magic_tree", "magic_tree_depleted")), 1)),


                // Ores


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -26.5, 93, -98.5),
                        new Location(Bukkit.getWorld("world"), -26.5, 94.5, -98.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.TIN_ORE.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -26.5, 93, -96.5),
                        new Location(Bukkit.getWorld("world"), -26.5, 94.5, -96.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.COPPER_ORE.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -26.5, 93, -94.5),
                        new Location(Bukkit.getWorld("world"), -26.5, 94.5, -94.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.IRON_ORE.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -26.5, 93, -92.5),
                        new Location(Bukkit.getWorld("world"), -26.5, 94.5, -92.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.COAL_ORE.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -26.5, 93, -90.5),
                        new Location(Bukkit.getWorld("world"), -26.5, 94.5, -90.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.MITHRIL_ORE.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -26.5, 93, -88.5),
                        new Location(Bukkit.getWorld("world"), -26.5, 94.5, -88.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.ADAMANTITE_ORE.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -26.5, 93, -86.5),
                        new Location(Bukkit.getWorld("world"), -26.5, 94.5, -86.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.RUNITE_ORE.asInt(), null, null)), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -98.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -98.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.HERRING_POOL.asInt(), null, null)), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -96.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -96.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.MINNOW_POOL.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -94.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -94.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.TROUT_STREAM.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -92.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -92.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.SALMON_STREAM.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -90.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -90.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.TUNA_SPOT.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -88.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -88.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.CRAWLER_REEF.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -86.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -86.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.PIKE_LAKE.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -84.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -84.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.EEL_SWAMP.asInt(), null, null)), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -82.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -82.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.SHARK_DEPTHS.asInt(), null, null)), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -80.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -80.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.TURTLE_BAY.asInt(), null, null)), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -78.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -78.5)),
                        new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.RAY_SANDBANK.asInt(), null, null)), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.InventoryOpener,
                        new Location(Bukkit.getWorld("world"), -14, 94, -101),
                        new Location(Bukkit.getWorld("world"), -13.5, 95.5, -101)),
                        new VersionedJson(new Gson().toJson(new InventoryOpenerMeta(InventoryId.BASIC_FURNACE.asInt(), Material.BARRIER, null, "Furnace")), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.InventoryOpener,
                        new Location(Bukkit.getWorld("world"), -14, 94, -103),
                        new Location(Bukkit.getWorld("world"), -13.5, 94, -103)),
                        new VersionedJson(new Gson().toJson(new InventoryOpenerMeta(InventoryId.ANVIL.asInt(), Material.ANVIL, null, "Anvil")), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.InventoryOpener,
                        new Location(Bukkit.getWorld("world"), -11, 94, -101),
                        new Location(Bukkit.getWorld("world"), -10.5, 94, -101.5)),
                        new VersionedJson(new Gson().toJson(new InventoryOpenerMeta(InventoryId.STOVE.asInt(), Material.FURNACE, null, "Stove")), 1))*/
