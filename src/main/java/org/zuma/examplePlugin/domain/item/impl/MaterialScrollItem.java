package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class MaterialScrollItem extends AbstractItem {
    public MaterialScrollItem(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Scroll", Icon.SCROLL_LIGHT_GRAY.get());
    }
}
