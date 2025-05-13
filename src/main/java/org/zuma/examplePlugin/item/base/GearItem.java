package org.zuma.examplePlugin.item.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.zuma.examplePlugin.item.constants.ItemRarity;
import org.zuma.examplePlugin.modifier.Modifier;

import java.util.ArrayList;
import java.util.List;

public abstract class GearItem extends CustomItem {
    public final List<Modifier> modifiers = new ArrayList<>();

    public GearItem(int id, Material material, ItemRarity rarity, String displayName) {
        this(id, material, rarity, displayName, null);
    }

    public GearItem(int id, Material material, ItemRarity rarity, String displayName, Component[] lore) {
        super(id, material, rarity, displayName, lore);
    }

    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
    }

}
