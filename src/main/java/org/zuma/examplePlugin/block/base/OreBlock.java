package org.zuma.examplePlugin.block.base;

import org.bukkit.Location;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.block.constants.BlockType;
import org.zuma.examplePlugin.player.RunePlayer;

public class OreBlock extends InteractableBlock {
    public OreBlock(BlockType type, Location location) {
        super(type, location);
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event) {
        player.getPlayer().sendMessage("Stop that!");
    }

    public void onPlayerBlockBreakEvent(RunePlayer player, BlockBreakEvent event) {
        player.getPlayer().sendMessage("Nuh uh!");
        event.setCancelled(true);
    }

    public void onPlayerBlockBreakProgressUpdateEvent(RunePlayer player, BlockBreakEvent event) {
        player.getPlayer().sendMessage("Nuh uh!");
        event.setCancelled(true);
    }
}
