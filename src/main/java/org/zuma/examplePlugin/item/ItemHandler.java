package org.zuma.examplePlugin.item;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.zuma.examplePlugin.item.base.CustomItem;
import org.zuma.examplePlugin.item.impl.bar.*;
import org.zuma.examplePlugin.item.impl.ore.*;
import org.zuma.examplePlugin.player.RunePlayer;

public class ItemHandler {
    private final ItemRegistry registry;

    public ItemHandler(ItemRegistry registry) {
        this.registry = registry;

        registry.registerItem(new BronzeOre());
        registry.registerItem(new IronOre());
        registry.registerItem(new SteelOre());
        registry.registerItem(new MithrilOre());
        registry.registerItem(new AdamantiteOre());
        registry.registerItem(new RuniteOre());

        registry.registerItem(new BronzeBar());
        registry.registerItem(new IronBar());
        registry.registerItem(new SteelBar());
        registry.registerItem(new MithrilBar());
        registry.registerItem(new AdamantiteBar());
        registry.registerItem(new RuniteBar());

    }

    public ItemRegistry getRegistry() {
        return registry;
    }

    public void onPlayerInteract(PlayerInteractEvent event, CustomItem itemInHand, RunePlayer player) {
        itemInHand.onPlayerInteract(event, player);
    }

    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, CustomItem itemInHand, RunePlayer player) {
        itemInHand.onPlayerInteractEntity(event, player);
    }

    public void onPlayerItemDrop(PlayerDropItemEvent event, CustomItem droppedItem, RunePlayer player) {
        droppedItem.onPlayerItemDrop(event, player);
    }

    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event, CustomItem itemInHand, RunePlayer player) {
        itemInHand.onPlayerTakeItemInHand(event, player);
    }

    public void onPlayerDamageEntity(EntityDamageByEntityEvent event, RunePlayer damager, CustomItem damagedWith) {
        damagedWith.onPlayerDamageEntity(event, damager);
    }
}
