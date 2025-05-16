package org.zuma.examplePlugin.item.impl.bar;

import org.bukkit.Material;
import org.zuma.examplePlugin.item.base.MaterialItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.ItemRarity;

public class SteelBar extends MaterialItem {
    public SteelBar() {
        super(ItemId.STEEL_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Steel Bar");
    }
}
