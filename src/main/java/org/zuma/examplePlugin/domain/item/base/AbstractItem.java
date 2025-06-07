package org.zuma.examplePlugin.domain.item.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.domain.heads.HeadGenerator;
import org.zuma.examplePlugin.domain.item.constants.CustomModelId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.constants.PersistanceDataKey;
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
    public final int id;
    public final ItemType type;
    public final ItemRarity rarity;
    public final Material material;
    public final String displayName;
    public final List<Component> lore = new ArrayList<>();
    public final List<Component> requirementsLore = new ArrayList<>();
    public final List<Component> bonusesLore = new ArrayList<>();
    public String rarityPrefixSlot;
    public int customModelData;

    @Nullable
    public StateType stateType = null;

    @Nullable
    private final String headTexture;
    @Nullable
    private final Color leatherColor;


    public AbstractItem(AbstractItemMeta meta) {
        this.type = meta.type();
        this.id = meta.id();
        this.material = meta.material();
        this.displayName = meta.displayName();
        this.customModelData = meta.id();
        this.rarity = meta.rarity();
        this.rarityPrefixSlot = null;
        this.headTexture = meta.headTexture();
        this.leatherColor = meta.leatherColor();

        if (meta.lore() != null) {
            this.lore.addAll(meta.lore().stream().map(lore -> Component.text(lore).color(Colors.ITEM_DESCRIPTION.asColor())).toList());
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

    public void appendToBonusesLore(Component component) {
        this.bonusesLore.add(component);
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

    public void setDisplayName(ItemStack itemStack, @Nullable Component prefix, @Nullable Component suffix) {
        TextColor rarityColor = TextColor.fromHexString(rarity.getHexColor());
        ItemMeta meta = itemStack.getItemMeta();
        Component originalName = meta.displayName();

        if (displayName != null) {
            Component base = Component.text(displayName)
                    .color(rarityColor)
                    .decoration(TextDecoration.ITALIC, false);

            if (prefix != null) {
                base = base.append(prefix.color(rarityColor).decoration(TextDecoration.ITALIC, false));
            }
            if (suffix != null) {
                base = base.append(suffix.color(rarityColor).decoration(TextDecoration.ITALIC, false));
            }

            meta.displayName(base);

        } else if (originalName != null) {
            Component base = originalName
                    .color(rarityColor)
                    .decoration(TextDecoration.ITALIC, false);

            if (prefix != null) {
                base = base.append(prefix.color(rarityColor).decoration(TextDecoration.ITALIC, false));
            }
            if (suffix != null) {
                base = base.append(suffix.color(rarityColor).decoration(TextDecoration.ITALIC, false));
            }

            meta.displayName(base);
        }

        itemStack.setItemMeta(meta);
    }

    private ItemMeta generateItemMeta(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        List<Component> itemLore = new ArrayList<>(lore);

        if (!requirementsLore.isEmpty()) {
            itemLore.add(Component.text(""));
            itemLore.add(Component.text("Requirements: ", Colors.ITEM_LABEL.asColor()));
            Component combined = requirementsLore.stream()
                    .reduce((comp1, comp2) -> comp1.append(Component.text(", ")).append(comp2))
                    .orElse(Component.empty());

            itemLore.add(combined);
        }

        if (!bonusesLore.isEmpty()) {
            itemLore.add(Component.text(""));
            itemLore.add(Component.text("Modifiers: ", Colors.ITEM_LABEL.asColor()));
            itemLore.addAll(bonusesLore);
        }

        itemLore.add(Component.text(""));
        itemLore.add(rarity.getComponent(rarityPrefixSlot == null ? "" : rarityPrefixSlot));
        meta.lore(itemLore.stream().map(lore -> lore.decoration(TextDecoration.ITALIC, false)).toList());

        if (customModelData != -1) {
            meta.setCustomModelData(customModelData);
        }

        NamespacedKey IdKey = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistanceDataKey.ITEM_ID.asString());
        meta.getPersistentDataContainer().set(IdKey, PersistentDataType.INTEGER, id);

        if (stateType != null) {
            NamespacedKey stateUuid = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistanceDataKey.STATE_UUID.asString());
            meta.getPersistentDataContainer().set(stateUuid, PersistentDataType.STRING, UUID.randomUUID().toString());
        }

        return meta;

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
