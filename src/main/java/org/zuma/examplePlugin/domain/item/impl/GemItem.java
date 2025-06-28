package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class GemItem extends EquipableItem {
    public GemItem(AbstractItemMeta meta) {
        super(meta);
        super.applyModifiersWhenInHand(false);
        super.appendToItemTypeLore("Gem", Icon.GREEN_GEM.get());
    }
}
