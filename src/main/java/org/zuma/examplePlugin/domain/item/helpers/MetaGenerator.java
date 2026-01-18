package org.zuma.examplePlugin.domain.item.helpers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.domain.item.Weapon;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.base.SocketedEquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.item.helpers.lore.*;
import org.zuma.examplePlugin.domain.item.impl.BulkItem;
import org.zuma.examplePlugin.domain.item.impl.EmptyContainer;
import org.zuma.examplePlugin.domain.item.impl.Mount;
import org.zuma.examplePlugin.domain.item.impl.StorageItem;
import org.zuma.examplePlugin.domain.item.impl.consumable.Food;
import org.zuma.examplePlugin.domain.item.impl.consumable.Potion;
import org.zuma.examplePlugin.domain.item.impl.fairy.Fairy;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFood;
import org.zuma.examplePlugin.domain.item.impl.utility.StoneOfPray;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;
import org.zuma.examplePlugin.domain.item.state.states.MultiConsumableState;
import org.zuma.examplePlugin.events.state.StateCreatedDefaultEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MetaGenerator {
    private final AbstractItemType itemType;

    public MetaGenerator(AbstractItemType itemType) {
        this.itemType = itemType;
    }

    public void updatePresentationLayer(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.lore(generateLore(item));
        meta.displayName(getDisplayName(item));

        if (itemType.customModelData != -1) {
            meta.setCustomModelData(itemType.customModelData);
        }

        if (List.of(ItemType.RANGED_WEAPON, ItemType.RANGED_WEAPON_WITH_SOCKETS).contains(itemType.type)) {
            meta.addEnchant(Enchantment.INFINITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (isItemGlowing(item)) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }


        Multimap<Attribute, AttributeModifier> emptyModifiers = ArrayListMultimap.create();
        meta.setAttributeModifiers(emptyModifiers);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DYE);

        // Equipable component model handling
        if (itemType instanceof EquipableItemType equipableItemType && equipableItemType.getEquipmentSlot() != null) {
            EquippableComponent eq = meta.getEquippable(); // snapshot
            eq.setSlot(equipableItemType.getEquipmentSlot());

            if (equipableItemType.getEquipableModel() != null) {
                eq.setModel(NamespacedKey.minecraft(equipableItemType.getEquipableModel()));
            }

            meta.setEquippable(eq);
        }

        item.setItemMeta(meta);
    }

    public boolean isItemGlowing(ItemStack item) {
        if (itemType == null) {
            return false;
        }

        if (itemType instanceof SocketedEquipableItemType) {
            AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

            if (state instanceof GearGemSocketsState socketsState) {
                return !socketsState.getInsertedGemIds().isEmpty();
            }
        }

        return false;
    }

    public List<Component> generateLore(ItemStack item) {
        List<Component> lore = new ArrayList<>();

        if (itemType == null) {
            return lore;
        }

        lore.addAll(itemType.descriptionLore.stream().map(str -> Component.text(str).color(Color.ITEM_LORE_DESCRIPTION.asColor())).toList());

        if (itemType instanceof Weapon weapon) {
            lore.addAll(new WeaponLore(weapon).getLore());
        }

        if (itemType instanceof Food food) {
            lore.addAll(new FoodLore(food).getLore());
        }

        if (itemType instanceof BulkItem bulkItem) {
            lore.addAll(new BulkItemLore(bulkItem).getLore());
        }

        if (itemType instanceof Potion potion) {
            lore.addAll(new PotionLore(potion).getLore());
        }

        if (itemType instanceof EquipableItemType equipable) {
            lore.addAll(new EquipableLore(equipable).getLore());
        }

        if (itemType instanceof SocketedEquipableItemType) {
            lore.addAll(new SocketedEquipableLore(item).getLore());
        }

        if (itemType instanceof EmptyContainer emptyContainer) {
            lore.addAll(new EmptyContainerLore(emptyContainer).getLore());
        }

        if (itemType instanceof FairyFood fairyFood) {
            lore.addAll(new FairyFoodLore(fairyFood).getLore());
        }

        if (itemType instanceof Fairy fairy) {
            lore.addAll(new FairyLore(fairy, item).getLore());
        }

        if (itemType instanceof Mount mount) {
            lore.addAll(new MountLore(mount, item).getLore());
        }

        if (itemType instanceof StorageItem) {
            lore.addAll(new StorageItemLore((StorageItem) itemType, item).getLore());
        }

        if (itemType instanceof StoneOfPray) {
            lore.addAll(new StoneOfPrayLore(item).getLore());
        }

        lore.add(Component.empty());
        lore.addAll(itemType.typeLore);

        if (itemType.rarity != ItemRarity.NOT_APPLICABLE) {
            lore.add(itemType.rarity.getComponent().decoration(TextDecoration.ITALIC, false));
        }

        if (!itemType.isTradeable()) {
            lore.add(Icon.ERROR.asComponent().append(Component.text("Untradeable", Color.ERROR.asColor())));
        }

        return lore.stream().map(component -> component.decoration(TextDecoration.ITALIC, false)).toList();
    }


    public Component getDisplayNameSuffix(ItemStack item) {
        if (!(itemType instanceof Stateful)) {
            return Component.empty();
        }
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state instanceof MultiConsumableState consumableState) {
            return Component.text(" (" + consumableState.getUsageLeft() + "/" + consumableState.getMaxUsages() + ")", Color.ITEM_TYPE_LABEL.asColor());
        }

        return Component.empty();
    }

    public Component getDisplayName(ItemStack item) {
        TextColor rarityColor = TextColor.fromHexString(itemType.rarity.getHexColor());
        ItemMeta meta = item.getItemMeta();
        Component originalName = meta.displayName();
        Component prefix = Component.empty();
        Component suffix = getDisplayNameSuffix(item);

        if (itemType.displayName != null) {
            Component base = Component.text(itemType.displayName)
                    .color(rarityColor)
                    .decoration(TextDecoration.ITALIC, false);

            base = base.append(prefix.color(rarityColor).decoration(TextDecoration.ITALIC, false));
            base = base.append(suffix.color(rarityColor).decoration(TextDecoration.ITALIC, false));

            return base;

        } else if (originalName != null) {
            Component base = originalName
                    .color(rarityColor)
                    .decoration(TextDecoration.ITALIC, false);

            base = base.append(prefix.color(rarityColor).decoration(TextDecoration.ITALIC, false));
            base = base.append(suffix.color(rarityColor).decoration(TextDecoration.ITALIC, false));

            return base;
        }

        return item.displayName();
    }

    public void setItemStackProps(ItemStack item, boolean generateState) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(PersistenceDataKey.ITEM_ID.key(RuneQuestPlugin.getInstance()), PersistentDataType.INTEGER, itemType.id);

        if (generateState && itemType.stateType != null && itemType instanceof Stateful stateful) {
            NamespacedKey stateUuid = PersistenceDataKey.STATE_UUID.key(RuneQuestPlugin.getInstance());

            if (!meta.getPersistentDataContainer().has(stateUuid)) {
                UUID uuid = UUID.randomUUID();
                meta.getPersistentDataContainer().set(stateUuid, PersistentDataType.STRING, uuid.toString());

                AbstractState newState = stateful.cloneDefaultStateTemplate();
                RuneQuestPlugin.getItemStateManager().insertDefaultItemState(uuid, newState);

                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    Bukkit.getServer().getPluginManager().callEvent(new StateCreatedDefaultEvent(uuid, newState));
                });
            }
        }

        // To set it's state uuid so lore generation fn can access it's state.
        item.setItemMeta(meta);
        updatePresentationLayer(item);
    }
}
