package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.SocketedEquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class SocketedGearType extends SocketedEquipableType {

    public SocketedGearType(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Gear", Icon.SHIELD_3.get());
    }


}
