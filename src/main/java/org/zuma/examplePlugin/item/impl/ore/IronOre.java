package org.zuma.examplePlugin.item.impl.ore;

import org.bukkit.Material;
import org.zuma.examplePlugin.item.base.MaterialItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.ItemRarity;

public class IronOre extends MaterialItem {
    public IronOre() {
        super(ItemId.IRON_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Ore");
    }
}
