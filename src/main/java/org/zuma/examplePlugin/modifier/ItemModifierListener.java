package org.zuma.examplePlugin.modifier;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.zuma.examplePlugin.player.RunePlayer;
import org.zuma.examplePlugin.player.RunePlayerHandler;

public class ItemModifierListener implements Listener {
    private final ItemModifierHandler handler;
    private final RunePlayerHandler playerHandler;

    public ItemModifierListener(ItemModifierHandler handler, RunePlayerHandler playerHandler) {
        this.handler = handler;
        this.playerHandler = playerHandler;
    }

    @EventHandler
    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event) {
        RunePlayer player = playerHandler.getRunePlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        handler.recalculatePlayerModifiers(player);

    }

    @EventHandler
    public void onPlayerInventoryItemClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || event.getClickedInventory().equals(event.getWhoClicked().getInventory())) {
            return;
        }

        RunePlayer player = playerHandler.getRunePlayer(event.getWhoClicked().getUniqueId());
        int slot = event.getSlot();

        if (player == null) {
            return;
        }

        // Armor slots
        if (slot >= 36 && slot <= 39) {
            handler.recalculatePlayerModifiers(player);
        }

    }

}
