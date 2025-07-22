package org.zuma.examplePlugin.domain.player.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.HashMap;

public class AdditionalGearSlotListener implements Listener {
    private final AdditionalGearSlotHandler slotHandler;

    public AdditionalGearSlotListener(AdditionalGearSlotHandler slotHandler) {
        this.slotHandler = slotHandler;
    }

    @EventHandler // Handle arrow usage only from reserved slot
    public void onBowShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            event.setCancelled(true);
            return;
        }

        ItemStack consumedArrow = event.getConsumable();
        PlayerInventory inv = player.getInventory();
        int reservedSlot = ReservedSlot.ARROW_SLOT.getSlot();
        ItemStack reservedArrow = inv.getItem(reservedSlot);

        // If engine took arrow not in reserved slot
        if (reservedArrow == null || !reservedArrow.isSimilar(consumedArrow)) {

            if (consumedArrow == null) {
                // Something weird happened
                event.setCancelled(true);
                return;
            }

            // Refund the consumed arrow
            ItemStack refund = consumedArrow.clone();
            refund.setAmount(1);
            runePlayer.giveItemOrDrop(refund);

            if (reservedArrow == null || GUIControl.isGuiItem(reservedArrow)) {
                HudService.showFailTitle(runePlayer, "You do not have arrows in your arrows slot!");
                event.setCancelled(true);
                return;
            }

            // Deduct 1 arrow from arrow slot
            int newAmount = reservedArrow.getAmount() - 1;
            if (newAmount <= 0) {
                inv.setItem(reservedSlot, null);
            } else {
                reservedArrow.setAmount(newAmount);
                inv.setItem(reservedSlot, reservedArrow);
            }
        }
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
