package org.zuma.examplePlugin.domain.item.base;

public class MaterialItem extends AbstractItem {
    public MaterialItem(AbstractItemMeta meta) {
        super(meta);
        this.setRarityPrefixSlot("⚒");

    }
}
