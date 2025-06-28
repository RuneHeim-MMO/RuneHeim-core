package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.SocketedEquipableItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class SocketedWeapon extends SocketedEquipableItemType {

    public SocketedWeapon(AbstractItemMeta meta) {
        super(meta);
        this.applyModifiersWhenInHand(true);
        super.setTypeLore("Weapon", Icon.SWORD_2.get());
    }


}
