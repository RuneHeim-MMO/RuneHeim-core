package org.zuma.examplePlugin.domain.mob;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSlot;

public record EquipmentSetRecord(EquipmentSlot slot, ItemStack stack, int chance) {
}
