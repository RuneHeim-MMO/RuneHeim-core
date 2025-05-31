package org.zuma.examplePlugin.domain.block;

import com.google.gson.Gson;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.base.ResourceNode;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeType;

import java.util.Collection;
import java.util.HashMap;

public class BlockManager {
    private final HashMap<Location, AbstractInteractableBlock> blocks = new HashMap<>();

    public void spawnResourceNode(ResourceNodeType type, Location location, Location hologramLocation) {
        if (blocks.get(location) != null) {
            return;
        }

        ResourceNode block = new ResourceNode(new AbstractInteractableBlockMeta(BlockType.ResourceNode, location, hologramLocation, new Gson().toJson(type)));
        block.deserializeAndApplyMeta();
        blocks.put(location, block);
    }

    public AbstractInteractableBlock getAbstractInteractableBlock(Block block) {
        return blocks.get(block.getLocation().toBlockLocation());
    }
    
    public Collection<AbstractInteractableBlock> getAllBlocks() {
        return blocks.values();
    }

    public void registerBlock(AbstractInteractableBlock block) {
        Location blockLocation = block.getLocation().toBlockLocation();

        if (blocks.containsKey(blockLocation)) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot initialize " + block.getType() + " at " + block.getLocation() + ". Block already exists at this location.");
            return;
        }

        blocks.put(blockLocation, block);
    }

}
