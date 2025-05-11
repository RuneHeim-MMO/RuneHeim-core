package org.zuma.examplePlugin.item;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.player.RuneQuestPlayerHandler;

import java.util.HashMap;
import java.util.Map;

public class ItemHandler {
    private final RuneQuestPlugin plugin;
    private final RuneQuestPlayerHandler playerHandler;
    private final Map<Integer, RuneItem> runeItems = new HashMap<>(); // ID:RuneItem (ID stored in persistent item stack data)

    public ItemHandler(RuneQuestPlugin pluginInstance, RuneQuestPlayerHandler playerHandler) {
        this.plugin = pluginInstance;
        this.playerHandler = playerHandler;
    }

    public RuneItem getRuneItem(ItemStack item) {
        return null; // TODO
    }


    public void onPlayerInteract(PlayerInteractEvent event, RuneItem itemInHand) {
        itemInHand.onPlayerInteract(event);
    }


    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, RuneItem itemInHand) {
        itemInHand.onPlayerInteractEntity(event);
    }


    public void onPlayerItemDrop(PlayerDropItemEvent event, RuneItem droppedItem) {
        droppedItem.onPlayerItemDrop(event);
    }


    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event, RuneItem itemInHand) {
        itemInHand.onPlayerTakeItemInHand(event);
    }


    public void onPlayerDamageEntity(EntityDamageByEntityEvent event, Player damager, RuneItem damagedWith) {
        damagedWith.onPlayerDamageEntity(event, damager);
    }
}
