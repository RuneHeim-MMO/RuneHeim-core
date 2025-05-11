package org.zuma.examplePlugin.modifier;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.zuma.examplePlugin.player.RunePlayer;
import org.zuma.examplePlugin.player.RunePlayerHandler;

public class GearModifierListener implements Listener {
    private final GearModifierHandler gearModifierHandler;
    private final RunePlayerHandler playerHandler;

    public GearModifierListener(GearModifierHandler gearModifierHandler, RunePlayerHandler playerHandler) {
        this.gearModifierHandler = gearModifierHandler;
        this.playerHandler = playerHandler;
    }

    @EventHandler
    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event) {
        RunePlayer player = playerHandler.getRunePlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        gearModifierHandler.recalculatePlayerModifiers(player);

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
            gearModifierHandler.recalculatePlayerModifiers(player);
        }

    }

}
