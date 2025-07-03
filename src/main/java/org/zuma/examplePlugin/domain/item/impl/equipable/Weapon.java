package org.zuma.examplePlugin.domain.item.impl.equipable;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class Weapon extends EquipableItemType {

    public Weapon(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Weapon", Icon.SWORD_2.get());
    }

    @Override
    public EquipableType getEquipableType() {
        return EquipableType.HANDHELD;
    }
}
