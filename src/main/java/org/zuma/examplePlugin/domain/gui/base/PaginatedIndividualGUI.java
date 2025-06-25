package org.zuma.examplePlugin.domain.gui.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.AbstractMap;
import java.util.List;

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


    public PaginatedIndividualGUI(String title, int id, int rows) {
        super(title, id, rows);

        this.inventory = super.getBaseInventory();

        if (rows < 2) {
            throw new IllegalStateException("PaginatedIndividualGUI can have minimum of 2rows!");
        }
    }

    private void recalculateIndexes() {
        int slots = usableRows * ITEMS_IN_ROW;

        if (page > maxPages) page = maxPages;
        if (page < 1) page = 1;

        indexStart = (page - 1) * slots;

        // inclusive end index, but must be at least indexStart if no items
        indexEnd = Math.min(indexStart + slots, totalItems);

        // If there are no items, adjust so indexEnd is not negative
        if (totalItems == 0) {
            indexStart = 0;
            indexEnd = 0;
        }

    }

    public void setContent(List<ItemStack> items) {
        this.totalItems = items.size();
        this.items = List.copyOf(items);

        int slotsPerPage = usableRows * ITEMS_IN_ROW;

        maxPages = (totalItems + slotsPerPage - 1) / slotsPerPage;
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

        page += 1;
        recalculateIndexes();
        getBaseInventory();
    }

    public void previousPage() {
        if (page <= 1) {
            return;
        }

        page -= 1;
        recalculateIndexes();
        getBaseInventory();
    }

    public ItemStack nextPageIcon() {
        if (page >= maxPages) {
            return new ItemStack(Material.AIR);
        }

        ItemStack icon = new ItemStack(Material.GREEN_BANNER);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(Component.text("Next", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        icon.setItemMeta(meta);

        super.assignMenuValue(icon, 10000);
        return icon;
    }

    public ItemStack previousPageIcon() {
        if (page <= 1) {
            return new ItemStack(Material.AIR);
        }

        ItemStack icon = new ItemStack(Material.RED_BANNER);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(Component.text("Previous", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        icon.setItemMeta(meta);

        super.assignMenuValue(icon, -10000);
        return icon;
    }

    @Override
    public Inventory getBaseInventory() {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.clear(i);
        }

        List<ItemStack> pageItems = items.subList(indexStart, indexEnd);
        for (int i = 0; i < pageItems.size(); i++) {
            inventory.setItem(i, pageItems.get(i));
        }

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

        if (menuValue == null) {
            return;
        }

        if (menuValue == 10000) {
            nextPage();
            event.setCancelled(true);
            return;
        }

        if (menuValue == -10000) {
            previousPage();
            event.setCancelled(true);
            return;
        }

        handleInventoryClick(event, slot, player);
    }


    public abstract void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player);
}
