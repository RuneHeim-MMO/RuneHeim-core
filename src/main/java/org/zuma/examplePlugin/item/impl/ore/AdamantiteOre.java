package org.zuma.examplePlugin.item.impl.ore;

import org.bukkit.Material;
import org.zuma.examplePlugin.item.base.MaterialItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.ItemRarity;

public class AdamantiteOre extends MaterialItem {
    public AdamantiteOre() {
        super(ItemId.ADAMANTITE_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Ore");
    }
}
