package org.zuma.examplePlugin.domain.item.impl.ore;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.base.MaterialItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class BronzeOre extends MaterialItem {
    public BronzeOre() {
        super(ItemId.BRONZE_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Ore");
    }
}
