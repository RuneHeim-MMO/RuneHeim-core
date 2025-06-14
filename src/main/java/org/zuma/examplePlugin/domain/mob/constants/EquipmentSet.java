package org.zuma.examplePlugin.domain.mob.constants;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.domain.mob.meta.MobEquipmentMeta;

public enum EquipmentSet {
    SHOPKEEPER_1(new MobEquipmentMeta(null, null, null, null, new ItemStack(Material.PAPER)));

    private final MobEquipmentMeta meta;

    EquipmentSet(MobEquipmentMeta meta) {
        this.meta = meta;
    }

    public MobEquipmentMeta asMobEquipmentMeta() {
        return this.meta;
    }
}
