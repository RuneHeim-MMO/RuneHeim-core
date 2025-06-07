package org.zuma.examplePlugin.domain.block.persistence;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.zuma.examplePlugin.core.meta.JsonMeta;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.constants.ResourceNodeTypeId;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.InventoryOpenerMeta;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeMeta;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;

import java.util.List;

public class InteractableBlockRepository {

    public List<BlockEntity> getAll() {
        return List.of(
                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 93, -101.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 94.5, -101.5)),
                        new JsonMeta(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.IRON_ORE.asInt())), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -98.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -98.5)),
                        new JsonMeta(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.HERRING_POOL.asInt())), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -96.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -96.5)),
                        new JsonMeta(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.MINNOW_POOL.asInt())), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -95.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -95.5)),
                        new JsonMeta(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.TROUT_STREAM.asInt())), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -92.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -92.5)),
                        new JsonMeta(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.SALMON_STREAM.asInt())), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -90.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -90.5)),
                        new JsonMeta(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.TUNA_SPOT.asInt())), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -88.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -88.5)),
                        new JsonMeta(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.CRAWLER_REEF.asInt())), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -86.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -86.5)),
                        new JsonMeta(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.PIKE_LAKE.asInt())), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -84.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -84.5)),
                        new JsonMeta(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.EEL_SWAMP.asInt())), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -82.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -82.5)),
                        new JsonMeta(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.SHARK_DEPTHS.asInt())), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -80.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -80.5)),
                        new JsonMeta(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.TURTLE_BAY.asInt())), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -78.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -78.5)),
                        new JsonMeta(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.RAY_SANDBANK.asInt())), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.InventoryOpener,
                        new Location(Bukkit.getWorld("world"), -14, 94, -101),
                        new Location(Bukkit.getWorld("world"), -13.5, 95.5, -101)),
                        new JsonMeta(new Gson().toJson(new InventoryOpenerMeta(InventoryId.BASIC_FURNACE.asInt(), Material.BARRIER, null, "Furnace")), 1)),

                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.InventoryOpener,
                        new Location(Bukkit.getWorld("world"), -14, 94, -103),
                        new Location(Bukkit.getWorld("world"), -13.5, 94, -103)),
                        new JsonMeta(new Gson().toJson(new InventoryOpenerMeta(InventoryId.ANVIL.asInt(), Material.ANVIL, null, "Anvil")), 1)),


                new BlockEntity(new AbstractInteractableBlockMeta(BlockType.InventoryOpener,
                        new Location(Bukkit.getWorld("world"), -11, 94, -101),
                        new Location(Bukkit.getWorld("world"), -10.5, 94, -101.5)),
                        new JsonMeta(new Gson().toJson(new InventoryOpenerMeta(InventoryId.STOVE.asInt(), Material.FURNACE, null, "Stove")), 1))

        );


    }
}
