package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.AbstractType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class MaterialType extends AbstractType {
    public MaterialType(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Material", Icon.MATERIAL.get());
    }
}
