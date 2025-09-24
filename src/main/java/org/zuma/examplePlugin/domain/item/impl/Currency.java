package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class Currency extends AbstractItemType {
    public Currency(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Currency", Icon.COIN_PILE.get());
    }
}
