package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class UtilityItem extends AbstractItemType {
    public UtilityItem(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Utility", Icon.ROCK.get());
    }
}
