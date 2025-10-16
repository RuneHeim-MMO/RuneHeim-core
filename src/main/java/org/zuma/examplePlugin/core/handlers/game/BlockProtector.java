package org.zuma.examplePlugin.core.handlers.game;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class BlockProtector implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(event.getPlayer());

        if (player != null && player.getRole().hasPermission(Permission.WORLD_MANIPULATION) && event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(event.getPlayer());

        if (player != null && player.getRole().hasPermission(Permission.WORLD_MANIPULATION) && event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        event.setCancelled(true);
    }

}
