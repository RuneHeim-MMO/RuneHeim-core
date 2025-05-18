package org.zuma.examplePlugin.domain.item;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.base.CustomItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;

public class ItemDrop {
    private final CustomItem item;
    private final int amount;

    public ItemDrop(ItemId itemId, int amount) {
        this.item = RuneQuestPlugin.getItemRegistry().getItem(itemId);
        this.amount = amount;
    }

    public ItemStack getItemStack() {
        return item.asItemStack(amount);
    }
}
