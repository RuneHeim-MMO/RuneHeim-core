package org.zuma.examplePlugin.domain.gui.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGUI {
    private final static int ITEMS_IN_ROW = 9;

    protected final String title;
    protected final int id, rows;

    public AbstractGUI(String title, int id, int rows) {
        this.title = "[" + id + "] " + title;
        this.id = id;
        this.rows = rows;
    }

    public Component getTitle() {
        return Component.text(this.title);
    }

    public int getId() {
        return id;
    }

    public int getSlots() {
        return rows * ITEMS_IN_ROW;
    }

    public void assignMenuValue(ItemStack item, int value) {
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(RuneQuestPlugin.getInstance(), "menu_button");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    @Nullable
    public Integer getMenuValue(ItemStack item) {
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.get(new NamespacedKey(RuneQuestPlugin.getInstance(), "menu_button"), PersistentDataType.INTEGER);
    }

    public void setActionsLore(ItemStack item, String leftClickAction, @Nullable String shiftClickAction) {
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = meta.lore();
        List<Component> appendLore = new ArrayList<>();

        appendLore.add(Component.text(""));
        appendLore.add(Component.text(leftClickAction + " - Left Click", Colors.ACCENT.asColor()));
        if (shiftClickAction != null)
            appendLore.add(Component.text(shiftClickAction + " - Shift Click", Colors.ACCENT.asColor()));

        if (lore != null) {
            lore.addAll(appendLore);
            meta.lore(lore);
        } else {
            meta.lore(appendLore);
        }

        item.setItemMeta(meta);
    }


    public Inventory getBaseInventory() {
        return Bukkit.createInventory(null, getSlots(), getTitle());
    }

    public abstract void openInventory(RunePlayer player);

    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {

    }

}
