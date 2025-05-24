package org.zuma.examplePlugin.domain.item.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.constants.PersistentDataKey;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.item.PlayerArmorEquipEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractItem {
    public final int id, customModelData;
    public final ItemType type;
    public final ItemRarity rarity;
    public final Material material;
    public final String displayName;
    public final List<Component> lore = new ArrayList<>();
    public final List<Component> requirementsLore = new ArrayList<>();
    public String rarityPrefixSlot, jsonMeta;

    @Nullable
    public StateType stateType = null;

    public AbstractItem(AbstractItemMeta meta) {
        this.type = meta.type();
        this.id = meta.id();
        this.material = meta.material();
        this.displayName = meta.displayName();
        this.customModelData = meta.id();
        this.rarity = meta.rarity();
        this.jsonMeta = meta.jsonMeta();
        this.rarityPrefixSlot = null;

        if (meta.lore() != null) {
            this.lore.addAll(meta.lore());
        }

    }

    public void setRarityPrefixSlot(String string) {
        this.rarityPrefixSlot = string;
    }

    public void appendToLore(Component component) {
        this.lore.add(component);
    }

    public void appendToRequirementsLore(Component component) {
        this.requirementsLore.add(component);
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

        if (!requirementsLore.isEmpty()) {
            itemLore.add(Component.text(""));
            itemLore.add(Component.text("Requirements: ", NamedTextColor.DARK_GRAY));
            Component combined = requirementsLore.stream()
                    .reduce((comp1, comp2) -> comp1.append(Component.text(", ")).append(comp2))
                    .orElse(Component.empty());

            itemLore.add(combined);
        }

        itemLore.add(Component.text(""));
        itemLore.add(rarity.getComponent(rarityPrefixSlot == null ? "" : rarityPrefixSlot));

        if (displayName != null) {
            meta.displayName(Component.text(displayName).color(rarityColor).decoration(TextDecoration.ITALIC, false));
        } else if (originalName != null) {
            meta.displayName(originalName.color(rarityColor).decoration(TextDecoration.ITALIC, false));
        }


        meta.lore(itemLore.stream().map(lore -> lore.decoration(TextDecoration.ITALIC, false)).toList());


        if (customModelData != -1) {
            meta.setCustomModelData(customModelData);
        }

        NamespacedKey IdKey = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistentDataKey.ITEM_ID.asString());
        meta.getPersistentDataContainer().set(IdKey, PersistentDataType.INTEGER, id);

        if (stateType != null) {
            NamespacedKey stateUuid = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistentDataKey.STATE_UUID.asString());
            meta.getPersistentDataContainer().set(stateUuid, PersistentDataType.STRING, UUID.randomUUID().toString());

            NamespacedKey stateType = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistentDataKey.STATE_TYPE.asString());
            meta.getPersistentDataContainer().set(stateType, PersistentDataType.STRING, stateType.asString());
        }

        return meta;

    }

    public String getJsonMeta() {
        return jsonMeta;
    }

    @Nullable
    public StateType getStateType() {
        return stateType;
    }

    public void setStateType(@Nullable StateType type) {
        stateType = type;
    }

    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player, StateManager itemStateManager) {
    }


    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, RunePlayer player, StateManager itemStateManager) {
    }


    public void onPlayerItemDrop(PlayerDropItemEvent event, RunePlayer player, StateManager itemStateManager) {
    }


    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event, RunePlayer player, StateManager itemStateManager) {
    }


    public void onPlayerDamageEntity(EntityDamageByEntityEvent event, RunePlayer player, StateManager itemStateManager) {
    }

    public void onPlayerWearItem(PlayerArmorEquipEvent event, RunePlayer player, StateManager itemStateManager) {

    }

}
