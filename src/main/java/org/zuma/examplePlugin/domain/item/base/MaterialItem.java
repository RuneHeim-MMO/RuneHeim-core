package org.zuma.examplePlugin.domain.item.base;

import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class MaterialItem extends AbstractItem {
    public MaterialItem(AbstractItemMeta meta) {
        super(meta);
        this.setRarityPrefixSlot("⚒");

    }
}
