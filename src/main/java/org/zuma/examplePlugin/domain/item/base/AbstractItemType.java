package org.zuma.examplePlugin.domain.item.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.item.constants.CustomModelId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.item.helpers.ItemStackCreator;
import org.zuma.examplePlugin.domain.item.helpers.MetaGenerator;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.item.PlayerArmorEquipEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractItemType {
    public final int id;
    public final ItemType type;
    public final ItemRarity rarity;
    public final Material material;
    public final String displayName;
    public final List<Component> typeLore;
    public final List<String> descriptionLore;
    public final boolean isTradeable;
    private boolean isSellableToShop;
    @Nullable
    public final String headTexture;
    public final NamespacedKey untradeableOwnerUUIDKey = PersistenceDataKey.UNTRADEABLE_OWNER_UUID.key(RuneQuestPlugin.getInstance());
    public final PersistentDataType<?, String> untradeableOwnerUUIDType = (PersistentDataType<?, String>) PersistenceDataKey.UNTRADEABLE_OWNER_UUID.getType();
    private final MetaGenerator metaGenerator;
    private final ItemStackCreator creator;
    private final double craftPriceMultiplier;
    public int customModelData;
    @Nullable
    public StateType stateType;

    private Integer basePrice = null;
    private Double pastIngredientsPriceMultiplier = null;

    public AbstractItemType(AbstractItemMeta meta) {
        this.type = meta.type();
        this.id = meta.id();
        this.craftPriceMultiplier = meta.craftPriceMultiplier();
        this.material = meta.material();
        this.displayName = meta.displayName();
        this.customModelData = meta.id();
        this.rarity = meta.rarity();
        this.headTexture = meta.headTexture();
        this.isTradeable = meta.isTradeable();
        this.stateType = null;
        this.typeLore = new ArrayList<>();
        this.descriptionLore = new ArrayList<>();
        this.isSellableToShop = true;

        if (meta.lore() != null) {
            this.descriptionLore.addAll(meta.lore());
        }

        this.metaGenerator = new MetaGenerator(this);
        this.creator = new ItemStackCreator(this);

    }

    @Nullable
    public Integer getBasePrice() {
        if (basePrice == null) return null;
        if (pastIngredientsPriceMultiplier == null) return basePrice;

        return (int) (basePrice * pastIngredientsPriceMultiplier);
    }

    public void setBasePrice(Integer basePrice) {
        this.basePrice = basePrice;
    }

    @Nullable // If null, then base price is used as is - usually for first tier materials
    public Double getPastIngredientsPriceMultiplier() {
        return pastIngredientsPriceMultiplier;
    }

    public void setPastIngredientsPriceMultiplier(Double pastIngredientsPriceMultiplier) {
        this.pastIngredientsPriceMultiplier = pastIngredientsPriceMultiplier;
    }

    public void setTypeLore(String type, @Nullable String icon) {
        typeLore.clear();
        typeLore.add(Component.text(icon != null ? icon + " " : "", NamedTextColor.WHITE).append(Component.text(type, Color.ITEM_TYPE_LABEL.asColor())));
    }

    public ItemStack createItemStackOfType() {
        return this.createItemStackOfType(1, false);
    }

    public ItemStack createItemStackOfType(int amount) {
        return this.createItemStackOfType(amount, false);
    }

    public ItemStack createItemStackOfType(boolean deferMeta) {
        return this.createItemStackOfType(1, deferMeta);
    }

    public ItemStack createItemStackOfType(int amount, boolean deferMeta) {
        ItemStack item = creator.createItemStackOfType(amount);

        if (!deferMeta) {
            creator.setItemStackMeta(item, amount, true);
        }

        return item;
    }

    // skip state generation for mob equipment
    public ItemStack createItemStackOfTypeIgnoreState(int amount) {
        ItemStack item = creator.createItemStackOfType(amount);
        creator.setItemStackMeta(item, amount, false);
        return item;
    }

    public void applyItemStackMeta(ItemStack stack, int amount) {
        this.creator.setItemStackMeta(stack, amount, true);
    }

    @Nullable
    public StateType getStateType() {
        return stateType;
    }

    public void setStateType(@Nullable StateType type) {
        stateType = type;
    }

    public void setCustomModelData(CustomModelId id) {
        customModelData = id.asInt();
    }

    public boolean isTradeable() {
        return this.isTradeable;
    }

    @Nullable
    public UUID getUntradeableOwnerUUID(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        String ownerUUID = pdc.get(untradeableOwnerUUIDKey, untradeableOwnerUUIDType);

        if (ownerUUID == null) {
            return null;
        }

        return UUID.fromString(ownerUUID);
    }

    public void setUntradeableOwnerUUID(ItemStack itemStack, UUID ownerUUID) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(untradeableOwnerUUIDKey, untradeableOwnerUUIDType, ownerUUID.toString());
        itemStack.setItemMeta(meta);
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

    public void onPlayerConsumeItem(PlayerItemConsumeEvent event, RunePlayer player, StateManager itemStateManager) {

    }

    public void onPlayerPickupItem(PlayerAttemptPickupItemEvent event, RunePlayer player, StateManager itemStateManager) {

    }

    public MetaGenerator getMetaGenerator() {
        return metaGenerator;
    }

    public Material getMaterial() {
        return material;
    }

    public double getCraftPriceMultiplier() {
        return craftPriceMultiplier;
    }

    public boolean isSellableToShop() {
        return isSellableToShop;
    }

    public void setSellableToShop(boolean sellableToShop) {
        isSellableToShop = sellableToShop;
    }
}
