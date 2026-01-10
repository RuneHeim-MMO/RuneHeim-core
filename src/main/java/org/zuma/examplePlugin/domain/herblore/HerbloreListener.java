package org.zuma.examplePlugin.domain.herblore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class HerbloreListener implements Listener {
    private HerbloreManager herbloreManager;

    public HerbloreListener(HerbloreManager herbloreManager) {
        this.herbloreManager = herbloreManager;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        herbloreManager.onChunkLoad(event);
    }
}
