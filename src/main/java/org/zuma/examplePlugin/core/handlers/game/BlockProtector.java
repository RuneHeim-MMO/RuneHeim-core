package org.zuma.examplePlugin.core.handlers.game;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.zuma.examplePlugin.core.utils.PlayerUtils;

public class BlockProtector implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        boolean isAllowed = PlayerUtils.canPlayerManipulateWorld(event.getPlayer());

        if (isAllowed) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        boolean isAllowed = PlayerUtils.canPlayerManipulateWorld(event.getPlayer());

        if (isAllowed) {
            return;
        }

        event.setCancelled(true);
    }

}
