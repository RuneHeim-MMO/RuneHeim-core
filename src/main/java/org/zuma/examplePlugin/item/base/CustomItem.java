package org.zuma.examplePlugin.item.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.item.constants.ItemRarity;
import org.zuma.examplePlugin.item.constants.PersistentDataKey;
import org.zuma.examplePlugin.player.RunePlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomItem {
    public final int id;
    public final Material material;
    public final String displayName;
    public final List<Component> lore = new ArrayList<>();
    public final ItemRarity rarity;
    public String raritySuffixSlot;
    public int customModelData;

    public CustomItem(int id, Material material, ItemRarity rarity, String displayName) {
        this(id, material, rarity, displayName, null);
    }

    public CustomItem(int id, Material material, ItemRarity rarity, String displayName, List<Component> lore) {
        this.id = id;
        this.material = material;
        this.displayName = displayName;
        this.customModelData = -1;
        this.rarity = rarity;
        this.raritySuffixSlot = null;

        if (lore != null) {
            this.lore.addAll(lore);
        }

    }

    public void setRaritySuffixSlot(String string) {
        this.raritySuffixSlot = string;
    }

    public void appendToLore(Component component) {
        this.lore.add(component);
    }

    public void setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
    }

    public ItemStack asItemStack() {
        return asItemStack(1);
    }

    public ItemStack asItemStack(int amount) {

        if (amount <= 0) {
            throw new Error("Cannot get ItemStack of " + this.id + ":" + this.material + " with amount less than 1");
        }

        ItemStack itemStack = new ItemStack(material);
        itemStack.setItemMeta(generateItemMeta(itemStack));
        itemStack.setAmount(amount);

        return itemStack;
    }

    private ItemMeta generateItemMeta(ItemStack itemStack) {
        TextColor rarityColor = TextColor.fromHexString(rarity.getHexColor());
        ItemMeta meta = itemStack.getItemMeta();
        Component originalName = meta.displayName();

        List<Component> itemLore = new ArrayList<>(lore);
        itemLore.add(rarity.getComponent(raritySuffixSlot == null ? "" : raritySuffixSlot));

        if (displayName != null) {
            meta.displayName(Component.text(displayName).color(rarityColor).decoration(TextDecoration.ITALIC, false));
        } else if (originalName != null) {
            meta.displayName(originalName.color(rarityColor).decoration(TextDecoration.ITALIC, false));
        }

        if (!itemLore.isEmpty()) {
            meta.lore(itemLore.stream().map(lore -> lore.decoration(TextDecoration.ITALIC, false)).toList());
        }

        if (customModelData != -1) {
            meta.setCustomModelData(customModelData);
        }

        NamespacedKey IdKey = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistentDataKey.ITEM_ID.asString());
        meta.getPersistentDataContainer().set(IdKey, PersistentDataType.INTEGER, id);

        return meta;

    }


    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player) {
    }


    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, RunePlayer player) {
    }


    public void onPlayerItemDrop(PlayerDropItemEvent event, RunePlayer player) {
    }


    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event, RunePlayer player) {
    }


    public void onPlayerDamageEntity(EntityDamageByEntityEvent event, RunePlayer player) {
    }


}
