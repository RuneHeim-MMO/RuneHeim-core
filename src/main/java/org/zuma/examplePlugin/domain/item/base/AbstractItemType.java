package org.zuma.examplePlugin.domain.item.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.item.constants.CustomModelId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
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

public abstract class AbstractItemType {
    private final MetaGenerator metaGenerator;
    private final ItemStackCreator creator;

    public final int id;
    public final ItemType type;
    public final ItemRarity rarity;
    public final Material material;
    public final String displayName;
    public final List<Component> typeLore;
    public final List<String> descriptionLore;
    public int customModelData;
    @Nullable
    public StateType stateType;
    @Nullable
    public final String headTexture;
    @Nullable
    public final org.bukkit.Color leatherColor;


    public AbstractItemType(AbstractItemMeta meta) {
        this.type = meta.type();
        this.id = meta.id();
        this.material = meta.material();
        this.displayName = meta.displayName();
        this.customModelData = meta.id();
        this.rarity = meta.rarity();
        this.headTexture = meta.headTexture();
        this.leatherColor = meta.leatherColor();
        stateType = null;
        typeLore = new ArrayList<>();
        descriptionLore = new ArrayList<>();

        if (meta.lore() != null) {
            this.descriptionLore.addAll(meta.lore());
        }

        this.metaGenerator = new MetaGenerator(this);
        this.creator = new ItemStackCreator(this);

    }

    public void setTypeLore(String type, @Nullable String icon) {
        typeLore.clear();
        typeLore.add(Component.text(icon != null ? icon + " " : "", NamedTextColor.WHITE).append(Component.text(type, Color.ITEM_TYPE_LABEL.asColor())));
    }

    public ItemStack createItemStackOfType() {
        return creator.createItemStackOfType(1);
    }

    public ItemStack createItemStackOfType(int amount) {
        return creator.createItemStackOfType(amount);
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
}
