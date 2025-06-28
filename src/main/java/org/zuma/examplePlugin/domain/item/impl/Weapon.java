package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class Weapon extends EquipableItemType {

    public Weapon(AbstractItemMeta meta) {
        super(meta);
        this.applyModifiersWhenInHand(true);
        super.setTypeLore("Weapon", Icon.SWORD_2.get());
    }


}
