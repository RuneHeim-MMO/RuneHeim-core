package org.zuma.examplePlugin.domain.item.impl;

import net.kyori.adventure.text.Component;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class MiscItem extends AbstractItem {
    public MiscItem(AbstractItemMeta meta) {
        super(meta);
        super.appendToLore(Component.text(""));
        super.appendToLore(Component.text("Misc Item", Color.ITEM_TYPE.asColor()));
    }
}
