package org.zuma.examplePlugin.domain.item.impl.bar;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.base.MaterialItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class MithrilBar extends MaterialItem {
    public MithrilBar() {
        super(ItemId.MITHRIL_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Bar");
    }
}
