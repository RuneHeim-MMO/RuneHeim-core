package org.zuma.examplePlugin.domain.item;

import org.zuma.examplePlugin.domain.item.constants.WeaponSpeed;
import org.zuma.examplePlugin.domain.item.constants.WeaponType;

public interface Weapon {
    WeaponSpeed getSpeed();
    WeaponType getType();
}
