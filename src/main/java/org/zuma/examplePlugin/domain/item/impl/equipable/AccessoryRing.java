package org.zuma.examplePlugin.domain.item.impl.equipable;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

// Equipment
public class AccessoryRing extends EquipableItemType {

    public AccessoryRing(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Ring", Icon.SHIELD_3.get());
    }

    @Override
    public EquipableType getEquipableType() {
        return EquipableType.ACCESSORY;
    }
}
