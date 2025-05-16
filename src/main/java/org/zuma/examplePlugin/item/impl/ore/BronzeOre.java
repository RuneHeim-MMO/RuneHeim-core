package org.zuma.examplePlugin.item.impl.ore;

import org.bukkit.Material;
import org.zuma.examplePlugin.item.base.MaterialItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.ItemRarity;

public class BronzeOre extends MaterialItem {
    public BronzeOre() {
        super(ItemId.BRONZE_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Ore");
    }
}
