package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class WeaponType extends EquipableType {

    public WeaponType(AbstractItemMeta meta) {
        super(meta);
        this.applyModifiersWhenInHand(true);
        super.setTypeLore("Weapon", Icon.SWORD_2.get());
    }


}
