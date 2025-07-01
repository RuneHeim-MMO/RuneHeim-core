package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

// Equipment
public class Gear extends EquipableItemType {

    public Gear(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Gear", Icon.SHIELD_3.get());
    }

    @Override
    public EquipableType getEquipableType() {
        return EquipableType.GENERIC_ARMOR;
    }
}
