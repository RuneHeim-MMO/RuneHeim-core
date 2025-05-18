package org.zuma.examplePlugin.domain.item.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.ItemType;

import java.util.List;

public record AbstractItemMeta(ItemType type, int id, Material material, ItemRarity rarity, String displayName,
                               List<Component> lore,
                               String jsonMeta) {
}
