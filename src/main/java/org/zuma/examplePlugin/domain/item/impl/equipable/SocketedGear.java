package org.zuma.examplePlugin.domain.item.impl.equipable;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.SocketedEquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class SocketedGear extends SocketedEquipableItemType {

    public SocketedGear(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Gear", Icon.SHIELD_3.get());
    }

    @Override
    public EquipableType getEquipableType() {
        return EquipableType.GENERIC_ARMOR;
    }

}
