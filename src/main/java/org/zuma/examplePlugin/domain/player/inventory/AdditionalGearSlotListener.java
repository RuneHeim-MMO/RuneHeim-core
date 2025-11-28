package org.zuma.examplePlugin.domain.player.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.cooldown.CooldownHandler;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;

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

        ItemStack consumedArrow = event.getConsumable();
        PlayerInventory inv = player.getInventory();
        int reservedSlot = ReservedSlot.ARROW_SLOT.getSlot();
        ItemStack reservedArrow = inv.getItem(reservedSlot);
        ItemStack deductedArrow = consumedArrow;

        if (consumedArrow == null) {
            // Something weird happened
            event.setCancelled(true);
            return;
        }

        // If engine took arrow not in reserved slot
        if (reservedArrow == null || !reservedArrow.isSimilar(consumedArrow)) {
            // Refund the consumed arrow
            ItemStack refund = consumedArrow.clone();
            refund.setAmount(1);
            runePlayer.giveItemOrDrop(refund);

            if (reservedArrow == null || GUIControl.isGuiItem(reservedArrow)) {
                HudService.showFailTitle(runePlayer, "You do not have arrows in your arrows slot!");
                event.setCancelled(true);
                return;
            }


            deductedArrow = reservedArrow.clone();

            // Deduct 1 arrow from arrow slot
            int newAmount = reservedArrow.getAmount() - 1;
            if (newAmount <= 0) {
                inv.setItem(reservedSlot, null);
                slotHandler.fillReservedSlots(runePlayer);
            } else {
                reservedArrow.setAmount(newAmount);
                inv.setItem(reservedSlot, reservedArrow);
            }

        }

        boolean isArrowConsumed = true;

        Modifier arrowRecoveryModifier = runePlayer.getModifiers().get(ModifierType.ARROW_RECOVERY_PERCENT);
        if (arrowRecoveryModifier != null) {
            double randomRoll = PlayerUtils.getRandom().nextDouble() * 100.0;
            if (randomRoll < arrowRecoveryModifier.amount()) {
                isArrowConsumed = false;
            }

        }

        if (!isArrowConsumed) {
            SoundType.ARROW_RECOVERED.play(runePlayer, false);
            ItemStack refund = deductedArrow.clone();
            refund.setAmount(1);
            runePlayer.giveItemOrDrop(refund);
        } else {
            AnalyticsUtils.incrementServerAndPlayer(runePlayer, AnalyticsKey.ARROWS_CONSUMED, 1);
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
