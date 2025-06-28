package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class GearType extends EquipableType {

    public GearType(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Gear", Icon.SHIELD_3.get());
    }


}
