package org.zuma.examplePlugin.item;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.item.base.CustomItem;
import org.zuma.examplePlugin.player.RunePlayer;
import org.zuma.examplePlugin.player.RunePlayerManager;
import org.zuma.examplePlugin.utils.PlayerUtils;

public class ItemListener implements Listener {
    private final RunePlayerManager playerHandler;
    private final ItemHandler itemHandler;
    private final ItemRegistry itemRegistry;

    public ItemListener(ItemHandler handler, RunePlayerManager playerHandler) {
        this.itemHandler = handler;
        this.playerHandler = playerHandler;

        itemRegistry = handler.getRegistry();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        CustomItem item = itemRegistry.getItem(PlayerUtils.getItemStackInHand(event.getPlayer()));

        if (item == null) {
            return;
        }

        RunePlayer player = playerHandler.getRunePlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        itemHandler.onPlayerInteract(event, item, player);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        CustomItem item = itemRegistry.getItem(PlayerUtils.getItemStackInHand(event.getPlayer()));

        if (item == null) {
            return;
        }

        RunePlayer player = playerHandler.getRunePlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        itemHandler.onPlayerInteractEntity(event, item, player);

    }

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent event) {
        CustomItem item = itemRegistry.getItem(event.getItemDrop().getItemStack());

        if (item == null) {
            return;
        }

        RunePlayer player = playerHandler.getRunePlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        itemHandler.onPlayerItemDrop(event, item, player);

    }

    @EventHandler
    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event) {
        ItemStack itemInHand = event.getPlayer().getInventory().getItem(event.getNewSlot());

        if (itemInHand == null) {
            return;
        }

        CustomItem item = itemRegistry.getItem(itemInHand);

        if (item == null) {
            return;
        }

        RunePlayer player = playerHandler.getRunePlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        itemHandler.onPlayerTakeItemInHand(event, item, player);

    }

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) {
            return;
        }

        CustomItem item = itemRegistry.getItem(PlayerUtils.getItemStackInHand(damager));

        if (item == null) {
            return;
        }

        RunePlayer player = playerHandler.getRunePlayer(damager);

        if (player == null) {
            return;
        }

        itemHandler.onPlayerDamageEntity(event, player, item);
    }

}
