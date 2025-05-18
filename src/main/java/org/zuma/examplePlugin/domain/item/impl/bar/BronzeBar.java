package org.zuma.examplePlugin.domain.item.impl.bar;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.base.MaterialItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class BronzeBar extends MaterialItem {
    public BronzeBar() {
        super(ItemId.BRONZE_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Bar");
    }
}
