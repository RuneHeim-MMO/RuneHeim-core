package org.zuma.examplePlugin.domain.block.persistence;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.constants.ResourceNodeTypeId;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.v1.InventoryOpenerMeta;
import org.zuma.examplePlugin.domain.block.meta.v1.ResourceNodeMeta;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;

import java.util.List;

public class InteractableBlockRepository {

    public List<BlockEntity> getAll() {
        return List.of(

                // Trees

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
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
                        new VersionedJson(new Gson().toJson(new InventoryOpenerMeta(InventoryId.STOVE.asInt(), Material.FURNACE, null, "Stove")), 1))

        );


    }
}
