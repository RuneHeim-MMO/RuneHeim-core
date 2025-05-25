package org.zuma.examplePlugin.domain.mob.meta;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public record MobEquipmentMeta(@Nullable ItemStack helmet, @Nullable ItemStack chestplate, @Nullable ItemStack leggings,
                               @Nullable ItemStack boots, @Nullable ItemStack hand) {
}
