package org.zuma.examplePlugin.item.impl.bar;

import org.bukkit.Material;
import org.zuma.examplePlugin.item.base.MaterialItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.ItemRarity;

public class MithrilBar extends MaterialItem {
    public MithrilBar() {
        super(ItemId.MITHRIL_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Bar");
    }
}
