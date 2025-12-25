package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class Arrow extends EquipableItemType {
    public Arrow(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Arrow", Icon.ARROW.get());
    }

    @Override
    public EquipableType getEquipableType() {
        return EquipableType.ARROW;
    }
}
