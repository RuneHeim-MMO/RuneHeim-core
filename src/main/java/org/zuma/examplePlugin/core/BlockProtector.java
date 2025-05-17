package org.zuma.examplePlugin.core;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockProtector implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {

        if (event.getPlayer().isOp() && event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        event.setCancelled(true);
    }
}
