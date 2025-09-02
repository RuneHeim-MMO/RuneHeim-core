package org.zuma.examplePlugin.domain.player.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.*;

public class AdditionalGearSlotHandler {
    public static final int INFO_SLOT = 16;
    public static final int SETTINGS_SLOT = 17;

    private final Map<Integer, ReservedSlot> slotsDef = ReservedSlot.getSlotMap();
    private final List<Integer> slots = slotsDef.keySet().stream().toList();

    private ItemStack getSlotPlaceholder(int slot) {
        String slotLabel = slotsDef.get(slot).getPlaceHolderLabel();
        if (slotLabel == null) slotLabel = "AccessoryRing Slot";
        return GUIControl.TRANSPARENT.asItemstack(Component.text(slotLabel, NamedTextColor.GRAY), true);
    }

    public void fillReservedSlots(RunePlayer player) {
        PlayerInventory inventory = player.asBukkitPlayer().getInventory();

        inventory.setItem(INFO_SLOT, GUIControl.TRANSPARENT.asItemstack(Component.text("Info"), true));
        inventory.setItem(SETTINGS_SLOT, GUIControl.TRANSPARENT.asItemstack(Component.text("Settings"), true));

        for (int slot : slots) {
            ItemStack inSlot = inventory.getItem(slot);

            if (inSlot == null || inSlot.getType() == Material.AIR) {
                inventory.setItem(slot, getSlotPlaceholder(slot));
            }
        }

    }

    public void checkReservedSlotsValidity(RunePlayer player) {
        PlayerInventory inventory = player.asBukkitPlayer().getInventory();

        slotsDef.forEach((slot, def) -> {
            ItemStack atSlot = inventory.getItem(slot);

            if (GUIControl.isGuiItem(atSlot)) {
                atSlot.setAmount(1);
                return;
            }

            if (atSlot == null) return;

            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(atSlot);

            if (type == null || !def.isValidItem(type)) {
                ItemStack cloned = atSlot.clone();

                inventory.setItem(slot, getSlotPlaceholder(slot));
                player.giveItemOrDrop(cloned);
            }
        });

        fillReservedSlots(player);

    }

    public void handleOwnInventoryClick(RunePlayer player, InventoryClickEvent event) {
        PlayerInventory inventory = player.asBukkitPlayer().getInventory();
        int slot = event.getSlot();

        if (slot == INFO_SLOT || slot == SETTINGS_SLOT) {
            if (slot == INFO_SLOT) {
                player.asBukkitPlayer().sendMessage("Info");
            }

            if (slot == SETTINGS_SLOT) {
                player.asBukkitPlayer().sendMessage("Settings");
            }

            event.setCancelled(true);
            return;
        }


        ItemStack clickedItemStack = inventory.getItem(event.getSlot());
        ItemStack cursor = event.getCursor();
        boolean hasItemInCursor = event.getCursor().getType() != Material.AIR;

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> fillReservedSlots(player), 2L);
        ReservedSlot slotDef = slotsDef.get(slot);
        
        if (!slots.contains(event.getSlot()) || slotDef == null) {
            return;
        }

        if (GUIControl.isGuiItem(clickedItemStack) && !hasItemInCursor) {
            event.setCancelled(true);
            return;
        }

        if (!GUIControl.isGuiItem(clickedItemStack) && clickedItemStack != null && !hasItemInCursor) {
            slotDef.getUnequipSound().play(player, false);
            return;
        }

        AbstractItemType cursorItemType = RuneQuestPlugin.getItemRegistry().getType(cursor);

        if (cursorItemType instanceof SkillRestrictable restrictable && !player.meetsRequirements(restrictable)) {
            HudService.sendFailMessage(player, Component.text("You do not meet requirements to use " + cursorItemType.displayName + "."));
            event.setCancelled(true);
            return;
        }


        if (!ReservedSlot.isItemInCorrectSlot(slot, cursorItemType)) {
            event.setCancelled(true);
            return;
        }


        if (GUIControl.isGuiItem(clickedItemStack)) {
            ItemStack clonedCursor = cursor.clone();
            event.getWhoClicked().setItemOnCursor(null);
            inventory.setItem(slot, clonedCursor);
            event.setCancelled(true);
        }


        slotDef.getEquipSound().play(player, false);

    }


    public List<ItemStack> getSlotItems(RunePlayer player) {
        List<ItemStack> items = new ArrayList<>();

        for (int slot : slots) {
            items.add(player.asBukkitPlayer().getInventory().getItem(slot));
        }

        return items;
    }

    @Nullable
    public ItemStack getSlotItem(RunePlayer player, ReservedSlot slot) {
        Player bukkitPlayer = player.asBukkitPlayer();

        if (bukkitPlayer == null || !bukkitPlayer.isOnline()) {
            return null;
        }

        return bukkitPlayer.getInventory().getItem(slot.getSlot());
    }
}
