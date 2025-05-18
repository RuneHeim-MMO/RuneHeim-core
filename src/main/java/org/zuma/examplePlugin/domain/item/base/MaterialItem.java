package org.zuma.examplePlugin.domain.item.base;

public class MaterialItem extends CustomItem {
    public MaterialItem(CustomItemMeta meta) {
        super(meta);
        this.setRarityPrefixSlot("⚒");

    }
}
