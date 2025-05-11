package org.zuma.examplePlugin.item;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public abstract class RuneItem {
    protected final int id;

    public RuneItem(int id) {
        this.id = id;
    }


    public void onPlayerInteract(PlayerInteractEvent event) {
    }


    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    }


    public void onPlayerItemDrop(PlayerDropItemEvent event) {
    }


    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event) {
    }


    public void onPlayerDamageEntity(EntityDamageByEntityEvent event, Player damager) {
    }


}
