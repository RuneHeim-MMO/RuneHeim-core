package org.zuma.examplePlugin.domain.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class GUIListener implements Listener {
    private final GUIHandler handler;

    public GUIListener(GUIHandler handler) {
        this.handler = handler;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        handler.onInventoryClick(event);
    }

    @EventHandler
    public void onQuiverClick(InventoryClickEvent event) {
        handler.onQuiverClickEvent(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        handler.onInventoryClose(event);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        handler.onItemDrop(event);
    }

}
