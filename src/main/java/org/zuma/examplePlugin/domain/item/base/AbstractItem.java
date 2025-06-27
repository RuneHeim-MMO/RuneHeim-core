package org.zuma.examplePlugin.domain.item.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.heads.HeadGenerator;
import org.zuma.examplePlugin.domain.item.constants.CustomModelId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.helpers.MetaGenerator;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.item.PlayerArmorEquipEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractItem {
    private final MetaGenerator metaGenerator;
    public final int id;
    public final ItemType type;
    public final ItemRarity rarity;
    public final Material material;
    public final String displayName;
    public final List<Component> lore;
    public final List<Component> requirementsLore;
    public final List<Component> bonusesLore;
    public final List<Component> itemTypeLore;
    public List<Component> gemsLore;
    public int customModelData;
    @Nullable
    public StateType stateType = null;
    @Nullable
    public final String headTexture;
    @Nullable
    public final org.bukkit.Color leatherColor;


    public AbstractItem(AbstractItemMeta meta) {
        this.metaGenerator = new MetaGenerator(this);
        this.type = meta.type();
        this.id = meta.id();
        this.material = meta.material();
        this.displayName = meta.displayName();
        this.customModelData = meta.id();
        this.rarity = meta.rarity();
        this.headTexture = meta.headTexture();
        this.leatherColor = meta.leatherColor();

        lore = new ArrayList<>();
        requirementsLore = new ArrayList<>();
        bonusesLore = new ArrayList<>();
        itemTypeLore = new ArrayList<>();
        gemsLore = new ArrayList<>();

        if (meta.lore() != null) {
            this.lore.addAll(meta.lore().stream().map(lore -> Component.text(lore).color(Color.ITEM_DESCRIPTION.asColor())).toList());
        }

    }

    public void appendToLore(Component component) {
        this.lore.add(component);
    }

    public void appendToRequirementsLore(Component component) {
        this.requirementsLore.add(component);
    }

    public void appendToBonusesLore(Component component) {
        this.bonusesLore.add(component);
    }

    public void appendToItemTypeLore(String type, @Nullable String icon) {
        itemTypeLore.add(Component.text(icon != null ? icon + " " : "", NamedTextColor.WHITE).append(Component.text(type, Color.ITEM_TYPE.asColor())));
    }

    public void setGemsLore(List<Component> gemsLore) {
        this.gemsLore = gemsLore;
    }

    public void clearGemsLore() {
        this.gemsLore.clear();
    }

    public ItemStack asItemStack() {
        return asItemStack(1);
    }

    public ItemStack asItemStack(int amount) {

        if (amount <= 0) {
            throw new Error("Cannot get ItemStack of " + this.id + ":" + this.material + " with amount less than 1");
        }

        ItemStack itemStack = new ItemStack(material);
        itemStack.setItemMeta(metaGenerator.generateMeta(itemStack));

        if (material == Material.PLAYER_HEAD && headTexture != null) {
            HeadGenerator.setHeadMeta(itemStack, headTexture);
        }

        if (material.name().contains("LEATHER_") && leatherColor != null) {
            LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
            meta.setColor(leatherColor);
            itemStack.setItemMeta(meta);
        }

        this.setDisplayName(itemStack, null, null);
        itemStack.setAmount(amount);

        return itemStack;
    }

    public ItemStack setDisplayName(ItemStack itemStack, @Nullable Component prefix, @Nullable Component suffix) {
        return metaGenerator.setDisplayName(itemStack, prefix, suffix);
    }

    public void updateLore(ItemStack itemStack) {
        itemStack.setItemMeta(metaGenerator.generateMeta(itemStack));
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

}
