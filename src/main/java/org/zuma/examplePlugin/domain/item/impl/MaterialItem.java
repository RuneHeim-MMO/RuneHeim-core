package org.zuma.examplePlugin.domain.item.impl;

import net.kyori.adventure.text.Component;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class MaterialItem extends AbstractItem {
    public MaterialItem(AbstractItemMeta meta) {
        super(meta);
        this.setRarityPrefixSlot(Icon.MATERIAL.get());
        super.appendToLore(Component.text(""));
        super.appendToLore(Component.text("Material", Colors.ITEM_TYPE.asColor()));
    }
}
