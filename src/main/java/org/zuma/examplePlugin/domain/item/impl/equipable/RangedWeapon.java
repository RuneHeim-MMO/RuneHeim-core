package org.zuma.examplePlugin.domain.item.impl.equipable;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.Weapon;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.constants.WeaponSpeed;
import org.zuma.examplePlugin.domain.item.constants.WeaponType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class RangedWeapon extends EquipableItemType implements Weapon {
    private final WeaponSpeed speed;

    public RangedWeapon(AbstractItemMeta meta, WeaponSpeed speed) {
        super(meta);
        super.setTypeLore("Ranged Weapon", Icon.BOW.get());
        this.speed = speed;
    }

    @Override
    public EquipableType getEquipableType() {
        return EquipableType.HANDHELD;
    }

    public WeaponSpeed getSpeed() {
        return speed;
    }

    public WeaponType getType() {
        return WeaponType.RANGED;
    }
}
