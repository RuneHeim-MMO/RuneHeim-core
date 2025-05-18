package org.zuma.examplePlugin.domain.item.impl.ore;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.base.MaterialItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class IronOre extends MaterialItem {
    public IronOre() {
        super(ItemId.IRON_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Ore");
    }
}
