package org.zuma.examplePlugin.item;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.zuma.examplePlugin.player.RuneQuestPlayer;
import org.zuma.examplePlugin.player.RuneQuestPlayerHandler;
import org.zuma.examplePlugin.utils.PlayerUtils;

public class ItemListener implements Listener {
    private final RuneQuestPlayerHandler playerHandler;
    private final ItemHandler itemHandler;

    public ItemListener(ItemHandler handler, RuneQuestPlayerHandler playerHandler) {
        this.itemHandler = handler;
        this.playerHandler = playerHandler;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ScriptedItem item = itemHandler.getRuneItem(PlayerUtils.getItemStackInHand(event.getPlayer()));

        if (item == null) {
            return;
        }

        RuneQuestPlayer player = playerHandler.getRunePlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        itemHandler.onPlayerInteract(event, item, player);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        ScriptedItem item = itemHandler.getRuneItem(PlayerUtils.getItemStackInHand(event.getPlayer()));

        if (item == null) {
            return;
        }

        RuneQuestPlayer player = playerHandler.getRunePlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        itemHandler.onPlayerInteractEntity(event, item, player);

    }

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent event) {
        ScriptedItem item = itemHandler.getRuneItem(event.getItemDrop().getItemStack());

        if (item == null) {
            return;
        }

        RuneQuestPlayer player = playerHandler.getRunePlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        itemHandler.onPlayerItemDrop(event, item, player);

    }

    @EventHandler
    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event) {
        ScriptedItem item = itemHandler.getRuneItem(PlayerUtils.getItemStackInHand(event.getPlayer()));

        if (item == null) {
            return;
        }

        RuneQuestPlayer player = playerHandler.getRunePlayer(event.getPlayer());

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

        ScriptedItem item = itemHandler.getRuneItem(PlayerUtils.getItemStackInHand(damager));

        if (item == null) {
            return;
        }

        RuneQuestPlayer player = playerHandler.getRunePlayer(damager);

        if (player == null) {
            return;
        }

        itemHandler.onPlayerDamageEntity(event, player, item);
    }

}
