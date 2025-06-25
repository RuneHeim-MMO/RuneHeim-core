package org.zuma.examplePlugin.domain.block;

import io.papermc.paper.event.block.BlockBreakProgressUpdateEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.gui.IDHolder;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class BlockListener implements Listener {
    private final BlockManager manager;
    private final ItemRegistry itemRegistry;

    public BlockListener(BlockManager manager, ItemRegistry itemRegistry) {
        this.manager = manager;
        this.itemRegistry = itemRegistry;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.useInteractedBlock() == Event.Result.DENY ||
                event.useItemInHand() == Event.Result.DENY) {

            return;
        }


        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR) {
            return;
        }

        Player player = event.getPlayer();
        Block dumbBlock = event.getClickedBlock() != null ? event.getClickedBlock() : player.getLocation().getBlock();
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
        AbstractInteractableBlock block = manager.getAbstractInteractableBlock(dumbBlock);


        if (runePlayer == null || block == null) {
            return;
        }


        AbstractItem itemInHand = itemRegistry.getItem(PlayerUtils.getItemStackInHand(player));
        block.onPlayerInteractEvent(runePlayer, event.getAction(), event, itemInHand);

    }

    @EventHandler
    public void onPlayerBlockBreakProgressUpdateEvent(BlockBreakProgressUpdateEvent event) {


        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        Block dumbBlock = event.getBlock();
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
        AbstractInteractableBlock block = manager.getAbstractInteractableBlock(dumbBlock);

        if (runePlayer == null || block == null) {
            return;
        }

        block.onPlayerBlockBreakProgressUpdateEvent(runePlayer, event);

    }

    @EventHandler
    public void onPlayerBlockBreakEvent(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Block dumbBlock = event.getBlock();
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(event.getPlayer());
        AbstractInteractableBlock block = manager.getAbstractInteractableBlock(dumbBlock);

        if (runePlayer == null || block == null) {
            return;
        }

        block.onPlayerBlockBreakEvent(runePlayer, event);

    }

    // On inventory unmount, clear all references to inventory uuids in inv opener blocks
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (!(event.getPlayer() instanceof Player)) {
            return;
        }

        if (!(holder instanceof IDHolder idHolder)) {
            return;
        }

        manager.removeStoredInventoryUUIDForBlock(idHolder);
    }
}
