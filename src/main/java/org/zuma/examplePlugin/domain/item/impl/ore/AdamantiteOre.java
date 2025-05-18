package org.zuma.examplePlugin.domain.item.impl.ore;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.base.MaterialItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class AdamantiteOre extends MaterialItem {
    public AdamantiteOre() {
        super(ItemId.ADAMANTITE_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Ore");
    }
}
