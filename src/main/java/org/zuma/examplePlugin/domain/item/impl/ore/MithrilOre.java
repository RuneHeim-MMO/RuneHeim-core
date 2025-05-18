package org.zuma.examplePlugin.domain.item.impl.ore;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.base.MaterialItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class MithrilOre extends MaterialItem {
    public MithrilOre() {
        super(ItemId.MITHRIL_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Ore");
    }
}
