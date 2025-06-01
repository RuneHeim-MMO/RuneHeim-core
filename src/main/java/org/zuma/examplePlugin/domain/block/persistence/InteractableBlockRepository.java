package org.zuma.examplePlugin.domain.block.persistence;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.InventoryOpenerMeta;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeMetaId;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class InteractableBlockRepository {

    public List<AbstractInteractableBlockMeta> getAll() {
        return List.of(new AbstractInteractableBlockMeta(BlockType.ResourceNode,
                        new Location(Bukkit.getWorld("world"), -22.5, 93, -101.5),
                        new Location(Bukkit.getWorld("world"), -22.5, 94.5, -101.5),
                        new Gson().toJson(new ResourceNodeMetaId(0))),

                new AbstractInteractableBlockMeta(BlockType.InventoryOpener,
                        new Location(Bukkit.getWorld("world"), -14, 94, -101),
                        new Location(Bukkit.getWorld("world"), -13.5, 94.5, -101),
                        new Gson().toJson(new InventoryOpenerMeta(0, Material.BARRIER, null, "Dark furnace"))),

                new AbstractInteractableBlockMeta(BlockType.InventoryOpener,
                        new Location(Bukkit.getWorld("world"), -14, 93, -103),
                        new Location(Bukkit.getWorld("world"), -13.5, 94, -103),
                        new Gson().toJson(new InventoryOpenerMeta(0, Material.ANVIL, List.of(new SkillRequirement(SkillType.SMITHING, 50)), "Anvil"))));
    }
}
