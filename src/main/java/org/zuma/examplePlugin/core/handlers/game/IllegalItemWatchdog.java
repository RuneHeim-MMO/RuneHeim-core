package org.zuma.examplePlugin.core.handlers.game;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotHandler;
import org.zuma.examplePlugin.domain.player.inventory.ReservedSlot;

import java.util.Set;

public class IllegalItemWatchdog implements Listener {
    public static ItemMeta tagIllegalItem(ItemMeta meta) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(PersistenceDataKey.ILLEGAL_ITEM.key(RuneQuestPlugin.getInstance()), PersistentDataType.BYTE, (byte) 1);
        return meta;
    }

    public static void tagIllegalItem(ItemStack item) {
        ItemMeta meta = tagIllegalItem(item.getItemMeta());
        item.setItemMeta(meta);
    }

    public static LegalityType getItemStackStatus(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        boolean isOverridden = container.get(PersistenceDataKey.OVERRIDE_SAVING_AND_ILLEGALITY.key(RuneQuestPlugin.getInstance()), PersistentDataType.BYTE) != null;
        if (isOverridden) return LegalityType.LEGAL;

        boolean isIllegalItem = container.get(PersistenceDataKey.ILLEGAL_ITEM.key(RuneQuestPlugin.getInstance()), PersistentDataType.BYTE) != null;
        if (isIllegalItem) return LegalityType.ILLEGAL;

        boolean isVanillaItem = container.get(PersistenceDataKey.ITEM_ID.key(RuneQuestPlugin.getInstance()), PersistentDataType.INTEGER) == null;
        if (isVanillaItem) return LegalityType.ILLEGAL_VANILLA;


        return LegalityType.LEGAL;
    }

    public static void setItemLegal(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = PersistenceDataKey.ILLEGAL_ITEM.key(RuneQuestPlugin.getInstance());
        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (container.has(key, PersistentDataType.INTEGER)) {
            container.remove(key);
            item.setItemMeta(meta);
        }
    }

    private void handleIllegalItem(Player player, String itemName, LegalityType type) {
        player.sendMessage(Component.text("You are not supposed to have this item (" + itemName + " - " + type + ")!", Color.ERROR.asColor()));
        player.sendMessage(Component.text("Please submit a bug report. This event has been logged.", Color.ERROR.asColor()));
        RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, player.getName() + " has acquired item which is was tagged as illegal! Item name: " + itemName + ", type: " + type);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);


        if (runePlayer != null && runePlayer.getRole().hasPermission(Permission.ITEM_WATCHDOG_BYPASS)) {
            return;
        }

        ItemStack item = event.getItem().getItemStack();
        LegalityType status = getItemStackStatus(item);

        if (status != LegalityType.LEGAL) {
            handleIllegalItem(player, TextUtils.extractPlainText(item.displayName()), status);
            event.setCancelled(true);
            event.getItem().remove();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer != null && runePlayer.getRole().hasPermission(Permission.ITEM_WATCHDOG_BYPASS)) {
            return;
        }

        PlayerInventory inventory = player.getInventory();
        Set<Integer> reservedSlots = ReservedSlot.getSlotMap().keySet();

        for (int slot = 0; slot < inventory.getSize(); slot++) {

            if (reservedSlots.contains(slot) || slot == AdditionalGearSlotHandler.INFO_SLOT || slot == AdditionalGearSlotHandler.SETTINGS_SLOT) {
                continue; // ignore reserved slots
            }

            ItemStack item = inventory.getItem(slot);
            if (item != null) {
                LegalityType status = getItemStackStatus(item);

                if (status != LegalityType.LEGAL) {
                    handleIllegalItem(player, TextUtils.extractPlainText(item.displayName()), status);
                    inventory.clear(slot);
                }
            }
        }
    }

    public enum LegalityType {
        LEGAL, ILLEGAL_VANILLA, ILLEGAL
    }


}
