package org.zuma.examplePlugin.core.handlers.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class DefaultCraftingDisabler implements Listener {

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        // Detect 2x2 crafting grid (player inventory)
        if (event.getInventory().getMatrix().length == 4) {
            event.getInventory().setResult(null);
        }
    }

    // Prevent placing items in the 2x2 grid via clicks
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getType() == InventoryType.CRAFTING) {
            // Slots 1–4 are the crafting grid in the player inventory
            if (event.getRawSlot() >= 1 && event.getRawSlot() <= 4) {
                event.setCancelled(true);
            }
        }
    }

    // Prevent dragging items into the 2x2 grid
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().getType() == InventoryType.CRAFTING) {
            for (int slot : event.getRawSlots()) {
                if (slot >= 1 && slot <= 4) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
