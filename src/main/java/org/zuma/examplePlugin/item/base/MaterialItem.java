package org.zuma.examplePlugin.item.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.zuma.examplePlugin.item.constants.ItemRarity;
import org.zuma.examplePlugin.item.constants.ItemType;

import java.util.List;

public abstract class MaterialItem extends CustomItem {
    public MaterialItem(int id, Material material, ItemRarity rarity, String displayName) {
        this(id, material, rarity, displayName, null);
        this.setCustomModelData(id);
    }

    public MaterialItem(int id, Material material, ItemRarity rarity, String displayName, List<Component> lore) {
        super(ItemType.MATERIAL, id, material, rarity, displayName, lore);
        this.setRarityPrefixSlot("⚒");

    }
}
