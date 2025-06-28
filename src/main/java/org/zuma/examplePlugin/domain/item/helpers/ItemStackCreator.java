package org.zuma.examplePlugin.domain.item.helpers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.zuma.examplePlugin.domain.heads.HeadGenerator;
import org.zuma.examplePlugin.domain.item.base.AbstractType;

public class ItemStackCreator {
    private final AbstractType itemType;

    public ItemStackCreator(AbstractType itemType) {
        this.itemType = itemType;
    }

    public ItemStack createItemStackOfType() {
        return createItemStackOfType(1);
    }

    public ItemStack createItemStackOfType(int amount) {

        if (amount <= 0) {
            throw new Error("Cannot get ItemStack of " + itemType.id + ":" + itemType.material + " with amount less than 1");
        }

        ItemStack itemStack = new ItemStack(itemType.material);
        itemType.getMetaGenerator().setItemStackProps(itemStack);

        if (itemType.material == Material.PLAYER_HEAD && itemType.headTexture != null) {
            HeadGenerator.setHeadMeta(itemStack, itemType.headTexture);
        }

        if (itemType.material.name().contains("LEATHER_") && itemType.leatherColor != null) {
            LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
            meta.setColor(itemType.leatherColor);
            itemStack.setItemMeta(meta);
        }

        itemStack.setAmount(amount);
        return itemStack;
    }
}
