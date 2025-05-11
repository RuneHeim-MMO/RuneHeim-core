package org.zuma.examplePlugin.item;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.zuma.examplePlugin.utils.PlayerUtils;

public class ItemListener implements Listener {
    private final ItemHandler itemHandler;

    public ItemListener(ItemHandler handler) {
        this.itemHandler = handler;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        RuneItem item = itemHandler.getRuneItem(PlayerUtils.getItemStackInHand(event.getPlayer()));

        if (item == null) {
            return;
        }

        itemHandler.onPlayerInteract(event, item);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        RuneItem item = itemHandler.getRuneItem(PlayerUtils.getItemStackInHand(event.getPlayer()));

        if (item == null) {
            return;
        }

        itemHandler.onPlayerInteractEntity(event, item);

    }

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent event) {
        RuneItem item = itemHandler.getRuneItem(event.getItemDrop().getItemStack());

        if (item == null) {
            return;
        }

        itemHandler.onPlayerItemDrop(event, item);

    }

    @EventHandler
    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event) {
        RuneItem item = itemHandler.getRuneItem(PlayerUtils.getItemStackInHand(event.getPlayer()));

        if (item == null) {
            return;
        }

        itemHandler.onPlayerTakeItemInHand(event, item);

    }

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        RuneItem item = itemHandler.getRuneItem(PlayerUtils.getItemStackInHand(player));

        if (item == null) {
            return;
        }

        itemHandler.onPlayerDamageEntity(event, player, item);
    }

}
