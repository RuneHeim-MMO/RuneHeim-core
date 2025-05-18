package org.zuma.examplePlugin.domain.item.impl.bar;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.base.MaterialItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class RuniteBar extends MaterialItem {
    public RuniteBar() {
        super(ItemId.RUNITE_BAR.asInt(), Material.COAL, ItemRarity.RARE, "Runite Bar");
    }
}
