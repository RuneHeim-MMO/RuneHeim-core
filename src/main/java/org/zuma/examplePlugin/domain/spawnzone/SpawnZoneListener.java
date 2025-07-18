package org.zuma.examplePlugin.domain.spawnzone;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class SpawnZoneListener implements Listener {
    private final SpawnZoneHandler handler;

    public SpawnZoneListener(SpawnZoneHandler handler) {
        this.handler = handler;
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        handler.onChunkUnload(event);

    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        handler.onEntityDeath(event);
    }

    @EventHandler

    public void onEntityRemove(EntityRemoveFromWorldEvent event) {
        handler.onEntityRemove(event);
    }

}
