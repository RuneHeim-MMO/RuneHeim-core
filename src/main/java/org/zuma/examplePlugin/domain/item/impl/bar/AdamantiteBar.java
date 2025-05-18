package org.zuma.examplePlugin.domain.item.impl.bar;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.base.MaterialItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class AdamantiteBar extends MaterialItem {
    public AdamantiteBar() {
        super(ItemId.ADAMANTITE_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Bar");
    }
}
