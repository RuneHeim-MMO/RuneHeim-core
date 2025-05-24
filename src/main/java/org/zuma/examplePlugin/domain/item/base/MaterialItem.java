package org.zuma.examplePlugin.domain.item.base;

import org.zuma.examplePlugin.domain.item.meta.AbstractMeta;

public class MaterialItem extends AbstractItem {
    public MaterialItem(AbstractMeta meta) {
        super(meta);
        this.setRarityPrefixSlot("⚒");

    }
}
