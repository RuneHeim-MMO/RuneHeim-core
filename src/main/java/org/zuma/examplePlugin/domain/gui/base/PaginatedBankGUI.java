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
import org.zuma.examplePlugin.core.interfaces.SerializableItemContainer;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.*;

public abstract class PaginatedBankGUI extends IndividualGUI implements SerializableItemContainer {
    private static final ItemStack placeholder = GUIControl.TRANSPARENT.asItemstack(Component.text("Bank"));
    private static final int ITEMS_IN_ROW = 9;   // Always 9
    private final int usableRows = rows - 1;
    private final Inventory inventory;

    private int page = 1;
    private int indexStart = 0;
    private int indexEnd = 0;
    private int maxPages = 3;
    private final HashMap<Integer, ItemStackSlotPair> items;
    private final Set<Integer> ignoredSlots;

    public PaginatedBankGUI(String title, int id, int rows, @Nullable Set<Integer> ignoredSlots) {
        super(title, id, rows);

        this.inventory = super.getBaseInventory();
        this.items = new HashMap<>();
        this.ignoredSlots = ignoredSlots == null ? new HashSet<>() : ignoredSlots;

        if (rows < 2) {
            throw new IllegalStateException("PaginatedBankGUI can have minimum of 2 rows!");
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

        if (page > maxPages) page = maxPages;
        if (page < 1) page = 1;

        int usableSlots = usableRows * ITEMS_IN_ROW;

        indexStart = (page - 1) * usableSlots;
        indexEnd = indexStart + usableSlots;

    }


    public void setPairsContent(List<ItemStackSlotPair> items, boolean tagIllegal) {
        items.forEach(item -> this.items.put(item.slot(), item));

        if (tagIllegal) {
            this.items.values().forEach(i -> IllegalItemWatchdog.tagIllegalItem(i.itemStack()));
        }

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

        updateMemoryMap();
        page++;
        recalculateIndexes();
        getBaseInventory();
    }

    public void previousPage() {
        if (page <= 1) {
            return;
        }

        updateMemoryMap();
        page--;
        recalculateIndexes();
        getBaseInventory();
    }

    protected void updateMemoryMap() {
        int maxSlotIndex = usableRows * ITEMS_IN_ROW - 1;

        for (int i = 0; i <= maxSlotIndex; i++) {
            ItemStack item = inventory.getItem(i);

            if (item == null || item.getType() == Material.AIR) {
                this.items.remove(indexStart + i);
                continue;
            }

            this.items.put(indexStart + i, new ItemStackSlotPair(indexStart + i, item));
        }
    }

    public ItemStack nextPageIcon() {
        boolean enabled = page < maxPages;
        ItemStack icon = (enabled ? GUIControl.CHEVRON_RIGHT : GUIControl.CHEVRON_RIGHT_DISABLED).asItemstack(Component.text("Next page", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        if (enabled) super.assignMenuValue(icon, 10000);

        return icon;
    }

    public ItemStack previousPageIcon() {
        boolean enabled = page > 1;
        ItemStack icon = (enabled ? GUIControl.CHEVRON_LEFT : GUIControl.CHEVRON_LEFT_DISABLED).asItemstack(Component.text("Previous page", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        if (enabled) super.assignMenuValue(icon, -10000);

        return icon;
    }

    @Override
    public List<ItemStackSlotPair> getContent() {
        return this.items.values().stream().toList();
    }

    @Override
    public Inventory getBaseInventory() {
        inventory.clear();

        List<ItemStackSlotPair> filteredItems = items.entrySet().stream()
                .filter(entry -> entry.getKey() >= indexStart && entry.getKey() < indexEnd)
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .toList();


        filteredItems.forEach(item -> {
            inventory.setItem(item.slot() - indexStart, item.itemStack());
        });

        int lastRowStart = (rows - 1) * ITEMS_IN_ROW;

        if (ignoredSlots != null) {
            ignoredSlots.forEach(slot -> inventory.setItem(slot, placeholder.clone()));
        }

        for (int i = lastRowStart; i < lastRowStart + ITEMS_IN_ROW; i++) {
            inventory.setItem(i, placeholder.clone());
        }

        inventory.setItem(getPaginationControlsOffset() + lastRowStart + ITEMS_IN_ROW - 6, previousPageIcon());
        inventory.setItem(getPaginationControlsOffset() + lastRowStart + ITEMS_IN_ROW - 5, GUIControl.fromNumber(page).asItemstack(Component.text("Page: " + page)));
        inventory.setItem(getPaginationControlsOffset() + lastRowStart + ITEMS_IN_ROW - 4, nextPageIcon());

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
            SoundType.UI_CLICK.play(player, false);
            event.setCancelled(true);
            return;
        } else if (menuValue != null && menuValue == -10000) {
            previousPage();
            SoundType.UI_CLICK.play(player, false);
            event.setCancelled(true);
            return;
        }

        if (GUIControl.isGuiItem(currentItem)) {
            event.setCancelled(true);
            return;
        }

        handleInventoryClick(event, slot, player);

    }

    public abstract void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player);

    public abstract int getPaginationControlsOffset();
}