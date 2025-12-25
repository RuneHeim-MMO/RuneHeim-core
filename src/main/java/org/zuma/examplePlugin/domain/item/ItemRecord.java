package org.zuma.examplePlugin.domain.item;

import org.zuma.examplePlugin.domain.item.constants.ItemId;

public class ItemRecord {
    private final int itemId;
    private final int amount;

    public ItemRecord(ItemId itemId, int amount) {
        this(itemId.asInt(), amount);
    }

    public ItemRecord(int itemId, int amount) {
        if (amount <= 0) {
            throw new Error("Item record (item id " + itemId + ") cannot have negative or 0 amount!");
        }
        this.itemId = itemId;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getItemId() {
        return itemId;
    }
}
