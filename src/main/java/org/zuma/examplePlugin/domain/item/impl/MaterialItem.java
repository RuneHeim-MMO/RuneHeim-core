package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class MaterialItem extends AbstractItem {
    public MaterialItem(AbstractItemMeta meta) {
        super(meta);
        super.appendItemType("Material", Icon.MATERIAL.get());
    }
}
