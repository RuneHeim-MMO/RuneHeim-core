package org.zuma.examplePlugin.item.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.zuma.examplePlugin.constants.ItemRarity;
import org.zuma.examplePlugin.modifier.Modifier;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemWithModifiers extends CustomItem {
    public final List<Modifier> modifiers = new ArrayList<>();

    public ItemWithModifiers(int id, Material material, ItemRarity rarity) {
        super(id, material, rarity, null, null, -1);
    }

    public ItemWithModifiers(int id, Material material, ItemRarity rarity, String displayName) {
        super(id, material, rarity, displayName, null, -1);
    }

    public ItemWithModifiers(int id, Material material, ItemRarity rarity, String displayName, Component[] lore) {
        super(id, material, rarity, displayName, lore, -1);
    }

}
