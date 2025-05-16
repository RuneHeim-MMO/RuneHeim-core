package org.zuma.examplePlugin.item.impl.ore;

import org.bukkit.Material;
import org.zuma.examplePlugin.item.base.MaterialItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.ItemRarity;

public class MithrilOre extends MaterialItem {
    public MithrilOre() {
        super(ItemId.MITHRIL_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Ore");
    }
}
