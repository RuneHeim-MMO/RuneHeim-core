package org.zuma.examplePlugin.domain.gui.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.interfaces.InventoryOpenerBlock;
import org.zuma.examplePlugin.domain.gui.IDHolder;
import org.zuma.examplePlugin.domain.mob.base.AbstractInventoryOpener;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractGUI implements Listener {
    private final static int ITEMS_IN_ROW = 9;

    private final UUID uuid;
    protected String title;
    protected final int id, rows;
    protected final boolean shared;

    @Nullable
    protected AbstractInventoryOpener holder;
    @Nullable
    protected InventoryOpenerBlock blockHolder;

    public AbstractGUI(String title, int id, int rows, boolean shared) {
        if (rows < 1 || rows > 6) {
            throw new IllegalStateException("GUI can have minimum of 1 and maximum of 6 rows!");
        }

        this.uuid = UUID.randomUUID();
        this.title = title;
        this.id = id;
        this.rows = rows;
        this.shared = shared;
    }

    public void setBlockHolder(@Nullable InventoryOpenerBlock holder) {
        this.blockHolder = holder;
    }

    @Nullable
    public InventoryOpenerBlock getBlockHolder() {
        return this.blockHolder;
    }

    public void setMobHolder(@Nullable AbstractInventoryOpener holder) {
        this.holder = holder;
    }

    @Nullable
    public AbstractInventoryOpener getMobHolder() {
        return this.holder;
    }

    public Component getTitle() {
        return Component.text(this.title);
    }

    public void setTitle(String title) {
        this.title = title;
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

        appendLore.add(Component.empty());
        appendLore.add(Component.text(leftClickAction + " - Left Click", Color.ACCENT.asColor()).decoration(TextDecoration.ITALIC, false));

        if (shiftClickAction != null) {
            appendLore.add(Component.text(shiftClickAction + " - Shift Click", Color.ACCENT.asColor()).decoration(TextDecoration.ITALIC, false));
        }

        if (lore != null) {
            lore.addAll(appendLore);
            meta.lore(lore);
        } else {
            meta.lore(appendLore);
        }

        item.setItemMeta(meta);
    }


    public Inventory getBaseInventory() {
        return Bukkit.createInventory(new IDHolder(id, uuid), getSlots(), getTitle());
    }

    // Returns true if inventory opening succeeded. (It might fail if trying to open anvil without hammer in hands for example)
    public abstract boolean attemptOpenInventory(RunePlayer player, Runnable onFail);

    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {

    }

    public void onInventoryClose(@Nullable InventoryCloseEvent event, @Nullable RunePlayer player) {

    }


    public UUID getUuid() {
        return uuid;
    }

    public boolean isShared() {
        return shared;
    }
}
