package org.zuma.examplePlugin.domain.item.impl.ore;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.base.MaterialItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class SteelOre extends MaterialItem {
    public SteelOre() {
        super(ItemId.STEEL_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Steel Ore");
    }
}
