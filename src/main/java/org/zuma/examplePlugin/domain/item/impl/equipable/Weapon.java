package org.zuma.examplePlugin.domain.item.impl.equipable;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.WeaponType;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.constants.WeaponSpeed;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class Weapon extends EquipableItemType implements WeaponType {
    private final WeaponSpeed speed;

    public Weapon(AbstractItemMeta meta, WeaponSpeed speed) {
        super(meta);
        super.setTypeLore("Weapon", Icon.SWORD_2.get());
        this.speed = speed;
    }

    @Override
    public EquipableType getEquipableType() {
        return EquipableType.HANDHELD;
    }

    public WeaponSpeed getSpeed() {
        return speed;
    }
}
