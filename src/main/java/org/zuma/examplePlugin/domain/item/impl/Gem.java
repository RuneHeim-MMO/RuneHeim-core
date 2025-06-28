package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class Gem extends EquipableItemType {
    public Gem(AbstractItemMeta meta) {
        super(meta);
        super.applyModifiersWhenInHand(false);
        super.setTypeLore("Gem", Icon.GREEN_GEM.get());
    }
}
