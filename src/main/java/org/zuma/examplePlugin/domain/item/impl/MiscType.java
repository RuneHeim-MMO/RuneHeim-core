package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.domain.item.base.AbstractType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class MiscType extends AbstractType {
    public MiscType(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Misc", null);
    }
}
