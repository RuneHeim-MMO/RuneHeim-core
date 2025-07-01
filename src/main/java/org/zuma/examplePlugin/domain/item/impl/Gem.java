package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class Gem extends EquipableItemType {
    public Gem(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Gem", Icon.GREEN_GEM.get());
    }

    @Override
    public EquipableType getEquipableType() {
        return EquipableType.NON_WEARABLE;
    }
}
