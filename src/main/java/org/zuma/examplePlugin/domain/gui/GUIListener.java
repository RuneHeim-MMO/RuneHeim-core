package org.zuma.examplePlugin.domain.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {
    private final GUIHandler handler;

    public GUIListener(GUIHandler handler) {
        this.handler = handler;
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        handler.onInventoryClick(event);
    }
}
