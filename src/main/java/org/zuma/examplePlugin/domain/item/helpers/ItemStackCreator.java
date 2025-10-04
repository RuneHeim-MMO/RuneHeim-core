package org.zuma.examplePlugin.domain.item.helpers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.domain.head.HeadGenerator;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;

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

    public void setItemStackMeta(ItemStack itemStack, int amount) {
        itemType.getMetaGenerator().setItemStackProps(itemStack);

        if (itemType.material == Material.PLAYER_HEAD && itemType.headTexture != null) {
            HeadGenerator.setHeadMeta(itemStack, itemType.headTexture);
        }

        itemStack.setAmount(amount);
    }
}
