package org.zuma.examplePlugin.item.impl.bar;

import org.bukkit.Material;
import org.zuma.examplePlugin.item.base.MaterialItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.ItemRarity;

public class IronBar extends MaterialItem {
    public IronBar() {
        super(ItemId.IRON_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Bar");
    }
}
