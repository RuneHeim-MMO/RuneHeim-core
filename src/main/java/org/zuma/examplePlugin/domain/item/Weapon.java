package org.zuma.examplePlugin.domain.item;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.constants.WeaponSpeed;
import org.zuma.examplePlugin.domain.item.constants.WeaponType;

public interface Weapon {
    Material getMaterial();

    WeaponSpeed getSpeed();

    WeaponType getType();
}
