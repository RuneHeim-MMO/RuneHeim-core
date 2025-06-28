package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class Gear extends EquipableItemType {

    public Gear(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Gear", Icon.SHIELD_3.get());
    }


}
