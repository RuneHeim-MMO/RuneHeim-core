package org.zuma.examplePlugin.domain.item.meta;

import org.bukkit.Color;
import org.bukkit.Material;
import org.zuma.examplePlugin.core.meta.JsonMeta;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.ItemType;

import javax.annotation.Nullable;
import java.util.List;

public record AbstractMeta(ItemType type, int id, Material material, ItemRarity rarity, String displayName,
                           List<String> lore,
                           @Nullable String headTexture,
                           @Nullable Color leatherColor,
                           JsonMeta jsonMeta) {
}
