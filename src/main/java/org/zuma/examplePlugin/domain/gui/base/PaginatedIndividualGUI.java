package org.zuma.examplePlugin.domain.gui.base;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.*;

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
    private HashMap<Integer, ItemStack> overrideItems = new HashMap<>();
    private final Set<Integer> ignoredSlots;

    public PaginatedIndividualGUI(String title, int id, int rows, @Nullable Set<Integer> ignoredSlots) {
        super(title, id, rows);

        this.inventory = super.getBaseInventory();
        this.items = new ArrayList<>();
        this.ignoredSlots = ignoredSlots == null ? new HashSet<>() : ignoredSlots;
        this.overrideItems = new HashMap<>();

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

    public void setOverrideItems(HashMap<Integer, ItemStack> overrideItems, boolean tagIllegal) {
        if (tagIllegal) {
            overrideItems.values().forEach(IllegalItemWatchdog::tagIllegalItem);
        }
        this.overrideItems = overrideItems;
    }

    public void setContent(List<ItemStack> items, boolean tagIllegal) {
        this.totalItems = items.size();

        if (tagIllegal) {
            this.items = List.copyOf(items).stream().peek(IllegalItemWatchdog::tagIllegalItem).toList();
        } else {
            this.items = List.copyOf(items);
        }

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

    public void resetPage() {
        page = 1;
        recalculateIndexes();
        getBaseInventory();
    }

    public ItemStack nextPageIcon() {
        boolean enabled = page < maxPages;
        ItemStack icon = (enabled ? GUIControl.CHEVRON_RIGHT : GUIControl.CHEVRON_RIGHT_DISABLED).asItemstack(Component.text("Next page", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        super.assignMenuValue(icon, 10000);

        return icon;
    }

    public ItemStack previousPageIcon() {
        boolean enabled = page > 1;
        ItemStack icon = (enabled ? GUIControl.CHEVRON_LEFT : GUIControl.CHEVRON_LEFT_DISABLED).asItemstack(Component.text("Previous page", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
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

        if (items.size() > getUsableSlotsCount()) {
            ItemStack pageCountIcon = GUIControl.fromNumber(page).asItemstack(Component.text("Page: " + page));
            assignMenuValue(pageCountIcon, -9000); // Dummy value to identify as a menu item

            inventory.setItem(getPaginationControlsOffset() + lastRowStart + ITEMS_IN_ROW - 6, previousPageIcon());
            inventory.setItem(getPaginationControlsOffset() + lastRowStart + ITEMS_IN_ROW - 5, pageCountIcon);
            inventory.setItem(getPaginationControlsOffset() + lastRowStart + ITEMS_IN_ROW - 4, nextPageIcon());
        } else {
            // If there's only one page, fill the last row with placeholders
            for (int i = lastRowStart; i < lastRowStart + ITEMS_IN_ROW; i++) {
                inventory.setItem(i, new ItemStack(Material.AIR));
            }
        }

        for (Map.Entry<Integer, ItemStack> entry : overrideItems.entrySet()) {
            int slot = entry.getKey();
            ItemStack item = entry.getValue();

            if (slot >= 0 && slot < inventory.getSize()) {
                inventory.setItem(slot, item);
            }
        }

        return inventory;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValueInt(currentItem);

        if (menuValue != null && menuValue == -9000) {
            // Clicked on page count icon, do nothing
            event.setCancelled(true);
            return;
        }

        if (menuValue != null && menuValue == 10000) {
            nextPage();
            SoundType.UI_CLICK.play(player, false);
            event.setCancelled(true);
        } else if (menuValue != null && menuValue == -10000) {
            previousPage();
            SoundType.UI_CLICK.play(player, false);
            event.setCancelled(true);
        } else {
            handleInventoryClick(event, slot, player);
        }
    }

    public abstract void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player);

    public abstract int getPaginationControlsOffset();
}