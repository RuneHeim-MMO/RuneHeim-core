package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class WeaponItem extends EquipableItem {

    public WeaponItem(AbstractItemMeta meta) {
        super(meta);
        this.applyModifiersWhenInHand(true);
        super.appendItemType("Weapon", Icon.SWORD_2.get());
    }


}
