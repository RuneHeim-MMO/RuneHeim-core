package org.zuma.examplePlugin.domain.block.persistence;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeMetaId;

import java.util.List;

public class InteractableBlockRepository {

    public List<AbstractInteractableBlockMeta> getAll() {
        return List.of(new AbstractInteractableBlockMeta(BlockType.ResourceNode, new Location(Bukkit.getWorld("world"), -22.5, 93, -101.5), new Location(Bukkit.getWorld("world"), -22.5, 94.5, -101.5), new Gson().toJson(new ResourceNodeMetaId(0))));
    }
}
