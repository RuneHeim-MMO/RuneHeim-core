package org.zuma.examplePlugin.domain.player.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.cooldown.CooldownHandler;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class AdditionalGearSlotListener implements Listener {
    private final AdditionalGearSlotHandler slotHandler;
    private final CooldownHandler cooldownHandler = new CooldownHandler();

    public AdditionalGearSlotListener(AdditionalGearSlotHandler slotHandler) {
        this.slotHandler = slotHandler;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true) // Handle arrow usage only from reserved slot
    public void onBowShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            event.setCancelled(true);
            return;
        }

        if (!runePlayer.getQuiverHandler().hasArrows()) {
            HudService.showFailTitle(runePlayer, "You do not have arrows in your quiver!");
            event.setCancelled(true);
            return;
        }

        runePlayer.getQuiverHandler().deductArrow();

        int arrowsLeft = runePlayer.getQuiverHandler().getArrowCount();

        if (arrowsLeft <= 5) {
            HudService.sendInfoMessage(runePlayer, Component.text("You have " + (arrowsLeft == 0 ? "no" : arrowsLeft) + " arrows left in your quiver!", Color.WARNING.asColor()));
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

        if (cooldownHandler.isCooldownInProgress(player)) return;
        cooldownHandler.startCooldown(player, 200L);

        int rawSlot = event.getRawSlot();
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            return;
        }

        if (event.getView().getTopInventory() instanceof CraftingInventory) {
            if (rawSlot >= 1 && rawSlot <= 4) {
                slotHandler.handleCraftingGridClick(runePlayer, event);
                return;
            }

            if (rawSlot == 45) {
                slotHandler.handleShieldSlotClick(runePlayer, event);
                return;
            }
        }


        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null || !clickedInventory.equals(player.getInventory())) {
            return;
        }


        slotHandler.handleOwnInventoryClick(runePlayer, event);

    }

}
