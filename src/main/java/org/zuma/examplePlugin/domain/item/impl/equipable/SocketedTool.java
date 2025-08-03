package org.zuma.examplePlugin.domain.item.impl.equipable;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.Weapon;
import org.zuma.examplePlugin.domain.item.base.SocketedEquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.constants.WeaponSpeed;
import org.zuma.examplePlugin.domain.item.constants.WeaponType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class SocketedTool extends SocketedEquipableItemType {
    public SocketedTool(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Tool", Icon.TOOL.get());
    }

    @Override
    public EquipableType getEquipableType() {
        return EquipableType.HANDHELD;
    }
}
