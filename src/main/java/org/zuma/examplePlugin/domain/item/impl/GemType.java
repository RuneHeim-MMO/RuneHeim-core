package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class GemType extends EquipableType {
    public GemType(AbstractItemMeta meta) {
        super(meta);
        super.applyModifiersWhenInHand(false);
        super.setTypeLore("Gem", Icon.GREEN_GEM.get());
    }
}
