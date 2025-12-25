package org.zuma.examplePlugin.domain.item.helpers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.domain.head.HeadGenerator;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;

public class ItemStackCreator {
    private final AbstractItemType itemType;

    public ItemStackCreator(AbstractItemType itemType) {
        this.itemType = itemType;
    }

    public ItemStack createItemStackOfType() {
        return createItemStackOfType(1);
    }

    public ItemStack createItemStackOfType(int amount) {
        if (amount <= 0) {
            throw new Error("Cannot get ItemStack of " + itemType.id + ":" + itemType.material + " with amount less than 1");
        }

        return new ItemStack(itemType.material);
    }

    public void setItemStackMeta(ItemStack itemStack, int amount, boolean generateState) {
        itemType.getMetaGenerator().setItemStackProps(itemStack, generateState);

        if (itemType.material == Material.PLAYER_HEAD && itemType.headTexture != null) {
            HeadGenerator.setHeadMeta(itemStack, itemType.headTexture);
        }

        itemStack.setAmount(amount);
    }
}
