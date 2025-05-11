package org.zuma.examplePlugin.item;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.zuma.examplePlugin.item.items.RuneItem;
import org.zuma.examplePlugin.player.RunePlayer;

public class ItemHandler {
    private final ItemRegistry registry;

    public ItemHandler(ItemRegistry registry) {
        this.registry = registry;

        registry.registerItem(new RuneItem());
    }

    public ItemRegistry getRegistry() {
        return registry;
    }


    public void onPlayerInteract(PlayerInteractEvent event, ScriptedItem itemInHand, RunePlayer player) {
        itemInHand.onPlayerInteract(event, player);
    }

    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, ScriptedItem itemInHand, RunePlayer player) {
        itemInHand.onPlayerInteractEntity(event, player);
    }

    public void onPlayerItemDrop(PlayerDropItemEvent event, ScriptedItem droppedItem, RunePlayer player) {
        droppedItem.onPlayerItemDrop(event, player);
    }

    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event, ScriptedItem itemInHand, RunePlayer player) {
        itemInHand.onPlayerTakeItemInHand(event, player);
    }

    public void onPlayerDamageEntity(EntityDamageByEntityEvent event, RunePlayer damager, ScriptedItem damagedWith) {
        damagedWith.onPlayerDamageEntity(event, damager);
    }
}
