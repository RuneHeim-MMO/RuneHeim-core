package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class Material extends AbstractItemType {
    public Material(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Material", Icon.MATERIAL.get());
    }
}
