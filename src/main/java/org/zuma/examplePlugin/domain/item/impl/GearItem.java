package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class GearItem extends EquipableItem {

    public GearItem(AbstractItemMeta meta) {
        super(meta);
        super.appendToItemTypeLore("Gear", Icon.SHIELD_3.get());
    }


}
