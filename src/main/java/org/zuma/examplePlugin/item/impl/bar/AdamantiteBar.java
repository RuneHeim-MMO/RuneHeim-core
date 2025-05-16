package org.zuma.examplePlugin.item.impl.bar;

import org.bukkit.Material;
import org.zuma.examplePlugin.item.base.MaterialItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.ItemRarity;

public class AdamantiteBar extends MaterialItem {
    public AdamantiteBar() {
        super(ItemId.ADAMANTITE_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Bar");
    }
}
