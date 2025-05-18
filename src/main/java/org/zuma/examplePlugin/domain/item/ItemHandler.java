package org.zuma.examplePlugin.domain.item;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.item.PlayerArmorEquipEvent;

public class ItemHandler {
    private final ItemRegistry registry;

    public ItemHandler(ItemRegistry registry) {
        this.registry = registry;
    }

    public ItemRegistry getRegistry() {
        return registry;
    }

    public void onPlayerInteract(PlayerInteractEvent event, AbstractItem itemInHand, RunePlayer player) {
        itemInHand.onPlayerInteract(event, player);
    }

    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, AbstractItem itemInHand, RunePlayer player) {
        itemInHand.onPlayerInteractEntity(event, player);
    }

    public void onPlayerItemDrop(PlayerDropItemEvent event, AbstractItem droppedItem, RunePlayer player) {
        droppedItem.onPlayerItemDrop(event, player);
    }

    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event, AbstractItem itemInHand, RunePlayer player) {
        itemInHand.onPlayerTakeItemInHand(event, player);
    }

    public void onPlayerDamageEntity(EntityDamageByEntityEvent event, RunePlayer damager, AbstractItem damagedWith) {
        damagedWith.onPlayerDamageEntity(event, damager);
    }

    public void onPlayerWearItem(PlayerArmorEquipEvent event, RunePlayer player) {
        event.getItem().onPlayerWearItem(event, player);
    }
}
