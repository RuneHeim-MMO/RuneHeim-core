package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableWithSockets;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class GearWithSocketsItem extends EquipableWithSockets {

    public GearWithSocketsItem(AbstractItemMeta meta) {
        super(meta);
        super.appendToItemTypeLore("Gear", Icon.SHIELD_3.get());
    }


}
