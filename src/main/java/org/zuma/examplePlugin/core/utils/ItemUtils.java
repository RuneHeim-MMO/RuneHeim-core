package org.zuma.examplePlugin.core.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.WeaponType;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.WeaponSpeed;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class ItemUtils {
    public static ItemStack createColoredLeatherArmor(Material material, Color color) {
        if (!material.name().startsWith("LEATHER_")) {
            throw new IllegalArgumentException("Material must be a leather armor piece!");
        }

        ItemStack item = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        if (meta != null) {
            meta.setColor(color);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static List<ItemRecord> multiplyItemRecords(List<ItemRecord> original, int multiplier) {
        if (multiplier <= 0) {
            throw new IllegalArgumentException("Multiplier must be positive!");
        }

        return original.stream()
                .map(record -> new ItemRecord(record.getItemId(), record.getAmount() * multiplier))
                .collect(Collectors.toList());
    }

    public static boolean isWearableGear(ItemStack item) {
        if (item == null) return false;

        String name = item.getType().name();
        return name.endsWith("_HELMET")
                || name.equals("PLAYER_HEAD")
                || name.endsWith("_CHESTPLATE")
                || name.endsWith("_LEGGINGS")
                || name.endsWith("_BOOTS");
    }

    public static void moveItemToSlot(Inventory fromInv, Inventory toInv, int fromSlot, int toSlot, int amount) {
        ItemStack sourceItem = fromInv.getItem(fromSlot);

        if (sourceItem == null || sourceItem.getType().isAir()) return;
        if (sourceItem.getAmount() < amount) return;

        ItemStack targetItem = toInv.getItem(toSlot);

        int movedAmount = 0;

        if (targetItem == null || targetItem.getType().isAir()) {
            // Target slot empty, move amount or max stack size if amount > maxStack
            int maxStack = sourceItem.getMaxStackSize();
            int move = Math.min(amount, maxStack);

            ItemStack movedItem = sourceItem.clone();
            movedItem.setAmount(move);
            toInv.setItem(toSlot, movedItem);
            movedAmount = move;
        } else {
            if (targetItem.isSimilar(sourceItem)) {
                int maxStack = targetItem.getMaxStackSize();
                int space = maxStack - targetItem.getAmount();

                if (space <= 0) return; // no space

                int move = Math.min(amount, space);
                targetItem.setAmount(targetItem.getAmount() + move);
                toInv.setItem(toSlot, targetItem);
                movedAmount = move;
            } else {
                // Different item in target slot
                return;
            }
        }

        // Remove moved amount from source
        int remaining = sourceItem.getAmount() - movedAmount;
        if (remaining > 0) {
            sourceItem.setAmount(remaining);
            fromInv.setItem(fromSlot, sourceItem);
        } else {
            fromInv.setItem(fromSlot, null);
        }
    }

    @Nullable
    public static WeaponSpeed getWeaponSpeed(ItemStack item) {
        if (item.getType() == Material.AIR) {
            return null;
        }

        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getItem(item);

        if (!(type instanceof WeaponType weapon)) {
            return null;
        }

        return weapon.getSpeed();

    }
}
