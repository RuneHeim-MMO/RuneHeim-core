package org.zuma.examplePlugin.block;

import io.papermc.paper.event.block.BlockBreakProgressUpdateEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.block.base.InteractableBlock;
import org.zuma.examplePlugin.player.RunePlayer;

public class BlockListener implements Listener {
    private final BlockManager manager;

    public BlockListener(BlockManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR) {
            return;
        }

        Player player = event.getPlayer();
        Block dumbBlock = event.getClickedBlock() != null ? event.getClickedBlock() : player.getLocation().getBlock();
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
        InteractableBlock block = manager.getInteractableBlock(dumbBlock);

        if (runePlayer == null || block == null) {
            return;
        }

        block.onPlayerInteractEvent(runePlayer, event.getAction(), event);

    }

    @EventHandler
    public void onPlayerBlockBreakProgressUpdateEvent(BlockBreakProgressUpdateEvent event) {

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        Block dumbBlock = event.getBlock();
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
        InteractableBlock block = manager.getInteractableBlock(dumbBlock);

        if (runePlayer == null || block == null) {
            return;
        }

        block.onPlayerBlockBreakProgressUpdateEvent(runePlayer, event);

    }

    @EventHandler
    public void onPlayerBlockBreakEvent(BlockBreakEvent event) {
        Block dumbBlock = event.getBlock();
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(event.getPlayer());
        InteractableBlock block = manager.getInteractableBlock(dumbBlock);

        if (runePlayer == null || block == null) {
            return;
        }

        block.onPlayerBlockBreakEvent(runePlayer, event);

    }
}
