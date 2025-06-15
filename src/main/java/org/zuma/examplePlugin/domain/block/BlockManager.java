package org.zuma.examplePlugin.domain.block;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;

import java.util.Collection;
import java.util.HashMap;

public class BlockManager {
    private final HashMap<Location, AbstractInteractableBlock> blocks = new HashMap<>();


    public void replaceBlockLocation(Location oldLocation, Location newLocation) {
        AbstractInteractableBlock block = blocks.remove(oldLocation);

        if (block != null) {
            blocks.put(newLocation, block);
        }
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

    public void unregisterBlock(AbstractInteractableBlock block) {
        Location blockLocation = block.getLocation().toBlockLocation();

        if (!blocks.containsKey(blockLocation)) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot unregister " + block.getType() + " at " + block.getLocation() + ". Block does not exist.");
            return;
        }

        blocks.get(blockLocation).onDisable();
        blocks.remove(blockLocation, block);
    }

    public void onDisable() {
        synchronized (this.blocks) {
            this.blocks.values().forEach(AbstractInteractableBlock::onDisable);
            this.blocks.clear();
        }
    }

}
