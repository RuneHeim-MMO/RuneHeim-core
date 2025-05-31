package org.zuma.examplePlugin.core.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.zuma.examplePlugin.domain.item.ItemRecord;

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
}
