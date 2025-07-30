package org.zuma.examplePlugin.domain.gui.base;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.handlers.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.interfaces.SerializableItemContainer;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class PaginatedIndividualGUI extends IndividualGUI {
    private static final int ITEMS_IN_ROW = 9;   // Always 9
    private final int usableRows = rows - 1;
    private final Inventory inventory;

    private int page = 1;
    private int totalItems = 0;
    private int indexStart = 0;
    private int indexEnd = 0;
    private int maxPages = 1;
    private List<ItemStack> items;
    @Nullable
    private final Set<Integer> ignoredSlots;

    public PaginatedIndividualGUI(String title, int id, int rows, @Nullable Set<Integer> ignoredSlots) {
        super(title, id, rows);

        this.inventory = super.getBaseInventory();
        this.items = new ArrayList<>();
        this.ignoredSlots = ignoredSlots;

        if (rows < 2) {
            throw new IllegalStateException("PaginatedIndividualGUI can have minimum of 2 rows!");
        }
    }

    private int getUsableSlotsCount() {
        int totalSlots = usableRows * ITEMS_IN_ROW;
        if (ignoredSlots == null || ignoredSlots.isEmpty()) {
            return totalSlots;
        }
        // Count how many ignored slots are in the usable area (exclude last row)
        int ignoredCount = 0;
        int maxSlotIndex = usableRows * ITEMS_IN_ROW - 1;
        for (Integer slot : ignoredSlots) {
            if (slot >= 0 && slot <= maxSlotIndex) {
                ignoredCount++;
            }
        }
        return totalSlots - ignoredCount;
    }

    private void recalculateIndexes() {
        int usableSlots = getUsableSlotsCount();

        if (page > maxPages) page = maxPages;
        if (page < 1) page = 1;

        indexStart = (page - 1) * usableSlots;
        indexEnd = Math.min(indexStart + usableSlots, totalItems);

        if (totalItems == 0) {
            indexStart = 0;
            indexEnd = 0;
        }
    }


    public void setContent(List<ItemStack> items) {
        this.totalItems = items.size();

        this.items = List.copyOf(items).stream().peek(IllegalItemWatchdog::tagIllegalItem).toList();

        int usableSlots = getUsableSlotsCount();

        maxPages = (totalItems + usableSlots - 1) / usableSlots;
        if (maxPages == 0) maxPages = 1;

        recalculateIndexes();
    }

    public AbstractMap.SimpleEntry<Integer, Integer> getPaginationIndexes() {
        return new AbstractMap.SimpleEntry<>(indexStart, indexEnd);
    }

    public void nextPage() {
        if (page >= maxPages) {
            return;
        }

        page++;
        recalculateIndexes();
        getBaseInventory();
    }

    public void previousPage() {
        if (page <= 1) {
            return;
        }

        page--;
        recalculateIndexes();
        getBaseInventory();
    }

    public ItemStack nextPageIcon() {
        if (page >= maxPages) {
            return new ItemStack(Material.AIR);
        }

        ItemStack icon = GUIControl.CHEVRON_RIGHT.asItemstack(Component.text("Next", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

        super.assignMenuValue(icon, 10000);
        return icon;
    }

    public ItemStack previousPageIcon() {
        if (page <= 1) {
            return new ItemStack(Material.AIR);
        }

        ItemStack icon = GUIControl.CHEVRON_LEFT.asItemstack(Component.text("Previous", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

        super.assignMenuValue(icon, -10000);
        return icon;
    }


    @Override
    public Inventory getBaseInventory() {
        // Clear entire inventory first
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.clear(i);
        }

        List<ItemStack> pageItems = items.subList(indexStart, indexEnd);
        int itemIndex = 0;

        // Fill non-ignored slots in usable rows
        int maxSlotIndex = usableRows * ITEMS_IN_ROW - 1;

        for (int i = 0; i <= maxSlotIndex; i++) {
            if (ignoredSlots != null && ignoredSlots.contains(i)) {
                continue;
            }
            if (itemIndex >= pageItems.size()) {
                break;
            }

            ItemStack item = pageItems.get(itemIndex);
            inventory.setItem(i, item);
            itemIndex++;
        }

        // Place pagination icons in the last row (full row assumed)
        int lastRowStart = (rows - 1) * ITEMS_IN_ROW;

        inventory.setItem(lastRowStart + ITEMS_IN_ROW - 2, previousPageIcon());
        inventory.setItem(lastRowStart + ITEMS_IN_ROW - 1, nextPageIcon());

        return inventory;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(currentItem);


        if (menuValue != null && menuValue == 10000) {
            nextPage();
            event.setCancelled(true);
        } else if (menuValue != null && menuValue == -10000) {
            previousPage();
            event.setCancelled(true);
        } else {
            handleInventoryClick(event, slot, player);
        }
    }

    public abstract void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player);
}