package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class MiscItem extends AbstractItem {
    public MiscItem(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Misc", null);
    }
}
