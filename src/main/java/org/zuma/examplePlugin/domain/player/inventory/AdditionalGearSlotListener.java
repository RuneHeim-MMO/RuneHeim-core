package org.zuma.examplePlugin.domain.player.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class AdditionalGearSlotListener implements Listener {
    private final AdditionalGearSlotHandler slotHandler;

    public AdditionalGearSlotListener(AdditionalGearSlotHandler slotHandler) {
        this.slotHandler = slotHandler;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            return;
        }

        slotHandler.fillReservedSlots(runePlayer);
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            return;
        }

        slotHandler.checkReservedSlotsValidity(runePlayer);
    }

    @EventHandler
    public void onOwnInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null || !clickedInventory.equals(player.getInventory())) {
            return;
        }

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            return;
        }

        slotHandler.handleOwnInventoryClick(runePlayer, event);

    }

}
