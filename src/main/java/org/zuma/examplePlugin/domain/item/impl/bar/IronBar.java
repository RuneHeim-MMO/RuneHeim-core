package org.zuma.examplePlugin.domain.item.impl.bar;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.base.MaterialItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class IronBar extends MaterialItem {
    public IronBar() {
        super(ItemId.IRON_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Bar");
    }
}
