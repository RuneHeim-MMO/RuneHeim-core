package org.zuma.examplePlugin.domain.block.persistence;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.constants.ResourceNodeTypeId;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.InventoryOpenerMeta;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeMetaId;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;

import java.util.List;

public class InteractableBlockRepository {

    public List<AbstractInteractableBlockMeta> getAll() {
        return List.of(
                new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 93, -101.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 94.5, -101.5),
                        new Gson().toJson(new ResourceNodeMetaId(ResourceNodeTypeId.IRON_ORE.asInt()))),


                new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -98.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -98.5),
                        new Gson().toJson(new ResourceNodeMetaId(ResourceNodeTypeId.HERRING_POOL.asInt()))),


                new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -96.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -96.5),
                        new Gson().toJson(new ResourceNodeMetaId(ResourceNodeTypeId.MINNOW_POOL.asInt()))),

                new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -95.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -95.5),
                        new Gson().toJson(new ResourceNodeMetaId(ResourceNodeTypeId.TROUT_STREAM.asInt()))),

                new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -92.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -92.5),
                        new Gson().toJson(new ResourceNodeMetaId(ResourceNodeTypeId.SALMON_STREAM.asInt()))),

                new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -90.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -90.5),
                        new Gson().toJson(new ResourceNodeMetaId(ResourceNodeTypeId.TUNA_SPOT.asInt()))),

                new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -88.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -88.5),
                        new Gson().toJson(new ResourceNodeMetaId(ResourceNodeTypeId.CRAWLER_REEF.asInt()))),

                new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -86.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -86.5),
                        new Gson().toJson(new ResourceNodeMetaId(ResourceNodeTypeId.PIKE_LAKE.asInt()))),

                new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -84.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -84.5),
                        new Gson().toJson(new ResourceNodeMetaId(ResourceNodeTypeId.EEL_SWAMP.asInt()))),

                new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -82.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -82.5),
                        new Gson().toJson(new ResourceNodeMetaId(ResourceNodeTypeId.SHARK_DEPTHS.asInt()))),


                new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -80.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -80.5),
                        new Gson().toJson(new ResourceNodeMetaId(ResourceNodeTypeId.TURTLE_BAY.asInt()))),


                new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 94, -78.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 95.5, -78.5),
                        new Gson().toJson(new ResourceNodeMetaId(ResourceNodeTypeId.RAY_SANDBANK.asInt()))),


                new AbstractInteractableBlockMeta(BlockType.InventoryOpener,
                        new Location(Bukkit.getWorld("world"), -14, 94, -101),
                        new Location(Bukkit.getWorld("world"), -13.5, 95.5, -101),
                        new Gson().toJson(new InventoryOpenerMeta(InventoryId.BASIC_FURNACE, Material.BARRIER, null, "Furnace"))),

                new AbstractInteractableBlockMeta(BlockType.InventoryOpener,
                        new Location(Bukkit.getWorld("world"), -14, 94, -103),
                        new Location(Bukkit.getWorld("world"), -13.5, 94, -103),
                        new Gson().toJson(new InventoryOpenerMeta(InventoryId.ANVIL, Material.ANVIL, null, "Anvil"))),


                new AbstractInteractableBlockMeta(BlockType.InventoryOpener,
                        new Location(Bukkit.getWorld("world"), -11, 94, -101),
                        new Location(Bukkit.getWorld("world"), -10.5, 94, -101.5),
                        new Gson().toJson(new InventoryOpenerMeta(InventoryId.STOVE, Material.FURNACE, null, "Stove"))
                )

        );


    }
}
