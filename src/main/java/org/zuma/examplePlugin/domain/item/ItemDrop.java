package org.zuma.examplePlugin.domain.item;

import org.zuma.examplePlugin.domain.item.constants.ItemId;

public class ItemDrop {
    private final int itemId;
    private final int amount;

    public ItemDrop(ItemId itemId, int amount) {
        this.itemId = itemId.asInt();
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getItemId() {
        return itemId;
    }
}
