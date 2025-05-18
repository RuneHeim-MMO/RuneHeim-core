package org.zuma.examplePlugin.domain.item.impl.bar;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.base.MaterialItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class SteelBar extends MaterialItem {
    public SteelBar() {
        super(ItemId.STEEL_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Steel Bar");
    }
}
