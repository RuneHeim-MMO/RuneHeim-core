package org.zuma.examplePlugin.domain.item.helpers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.PersistanceDataKey;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MetaGenerator {
    private final AbstractItem customItem;

    public MetaGenerator(AbstractItem customItem) {
        this.customItem = customItem;
    }

    public ItemMeta generateMeta(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<Component> itemLore = new ArrayList<>(customItem.lore);

        if (!customItem.requirementsLore.isEmpty()) {
            itemLore.add(Component.empty());
            itemLore.add(Component.text("Requirements: ", Color.ITEM_LABEL.asColor()));
            Component combined = customItem.requirementsLore.stream()
                    .reduce((comp1, comp2) -> comp1.append(Component.text(", ")).append(comp2))
                    .orElse(Component.empty());

            itemLore.add(combined);
        }

        if (!customItem.bonusesLore.isEmpty()) {
            itemLore.add(Component.empty());
            itemLore.add(Component.text("Modifiers: ", Color.ITEM_LABEL.asColor()));
            itemLore.addAll(customItem.bonusesLore);
        }

        if (!customItem.gemsLore.isEmpty()) {
            itemLore.addAll(customItem.gemsLore);
        }

        itemLore.add(Component.empty());

        List<Component> loreComponents = new ArrayList<>(itemLore.stream().map(lore -> lore.decoration(TextDecoration.ITALIC, false)).toList());

        if (customItem.rarity != ItemRarity.NOT_APPLICABLE) {
            loreComponents.add(customItem.rarity.getComponent().decoration(TextDecoration.ITALIC, false));
        }

        loreComponents.addAll(customItem.itemTypeLore.stream().map(lore -> lore.decoration(TextDecoration.ITALIC, false)).toList());


        meta.lore(loreComponents);

        if (customItem.customModelData != -1) {
            meta.setCustomModelData(customItem.customModelData);
        }

        NamespacedKey IdKey = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistanceDataKey.ITEM_ID.asString());
        meta.getPersistentDataContainer().set(IdKey, PersistentDataType.INTEGER, customItem.id);

        if (customItem.stateType != null) {
            NamespacedKey stateUuid = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistanceDataKey.STATE_UUID.asString());
            meta.getPersistentDataContainer().set(stateUuid, PersistentDataType.STRING, UUID.randomUUID().toString());
        }

        return meta;

    }

    public ItemStack setDisplayName(ItemStack itemStack, @Nullable Component prefix, @Nullable Component suffix) {
        TextColor rarityColor = TextColor.fromHexString(customItem.rarity.getHexColor());
        ItemMeta meta = itemStack.getItemMeta();
        Component originalName = meta.displayName();

        if (customItem.displayName != null) {
            Component base = Component.text(customItem.displayName)
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

        return itemStack;
    }
}
