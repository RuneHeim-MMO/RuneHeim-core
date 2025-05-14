package org.zuma.examplePlugin.block;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.zuma.examplePlugin.block.base.InteractableBlock;
import org.zuma.examplePlugin.block.base.OreBlock;
import org.zuma.examplePlugin.block.constants.BlockType;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BlockManager {
    private final HashMap<Location, InteractableBlock> blocks = new HashMap<>();

    public void spawnOreBlock(BlockType type, Location location, Location hologramLocation) {
        if (blocks.get(location) != null) {
            return;
        }

        InteractableBlock block = new OreBlock(type, location);
        block.setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), hologramLocation, List.of(type.toString())));
        block.spawn(type.getMaterial());

        blocks.put(location, block);
    }

    public InteractableBlock getInteractableBlock(Block block) {
        return blocks.get(block.getLocation());
    }

}
