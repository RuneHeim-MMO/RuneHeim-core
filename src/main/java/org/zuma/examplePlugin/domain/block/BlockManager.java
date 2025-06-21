package org.zuma.examplePlugin.domain.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.DespawnableBlock;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;

import java.util.Collection;
import java.util.HashMap;

public class BlockManager {
    private static final int BLOCK_DESPAWN_SECONDS = 10;
    private final HashMap<Location, AbstractInteractableBlock> blocks = new HashMap<>();
    private final HashMap<DespawnableBlock, Long> despawnableQueue = new HashMap<>();

    public BlockManager() {
        startDespawnLoop();
    }

    public void startDespawnLoop() {
        new BukkitRunnable() {

            @Override
            public void run() {

                blocks.values().forEach((block) -> {
                    if (!(block instanceof DespawnableBlock despawnable)) {
                        return;
                    }

                    if (!despawnable.shouldBeDespawned()) {
                        return;
                    }

                    if (despawnableQueue.containsKey(despawnable)) {
                        return;
                    }

                    despawnableQueue.put(despawnable, System.currentTimeMillis() + BlockManager.BLOCK_DESPAWN_SECONDS * 1000L);
                });

                despawnableQueue.forEach((block, despawnMillis) -> {
                    if (System.currentTimeMillis() >= despawnMillis) {

                        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                            despawnBlock(block);
                        });
                    }
                });

            }

        }.runTaskTimer(RuneQuestPlugin.getInstance(), 20, 20);
    }

    public void despawnBlock(DespawnableBlock block) {
        ItemRecord despawnDrop = block.dropOnDespawn();

        despawnableQueue.remove(block);
        unregisterBlock((AbstractInteractableBlock) block);

        if (despawnDrop != null) {
            AbstractInteractableBlock abstractInteractableBlock = (AbstractInteractableBlock) block;
            Location blockLocation = abstractInteractableBlock.getLocation();


            AbstractItem drop = RuneQuestPlugin.getItemRegistry().getItem(despawnDrop.getItemId());

            if (drop == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Could not produce a drop on despawn for " + ((AbstractInteractableBlock) block).getType() + ". It has an invalid item id in item record..");
                return;
            }

            blockLocation.getWorld().dropItem(blockLocation, drop.asItemStack(despawnDrop.getAmount()));
        }

    }

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
        blockLocation.setYaw(0f);
        blockLocation.setPitch(0f);

        if (blocks.containsKey(blockLocation)) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot initialize " + block.getType() + " at " + block.getLocation() + ". Block already exists at this location.");
            return;
        }

        blocks.put(blockLocation, block);
    }

    public void unregisterBlock(AbstractInteractableBlock block) {
        Location blockLocation = block.getLocation().toBlockLocation();
        blockLocation.setYaw(0f);
        blockLocation.setPitch(0f);

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
