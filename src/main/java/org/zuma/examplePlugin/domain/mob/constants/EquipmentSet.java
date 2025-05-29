package org.zuma.examplePlugin.domain.mob.constants;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.utils.ItemUtils;
import org.zuma.examplePlugin.domain.heads.HeadTexture;
import org.zuma.examplePlugin.domain.mob.meta.MobEquipmentMeta;

public enum EquipmentSet {
    SHOPKEEPER_1(new MobEquipmentMeta(HeadTexture.SHOPKEEPER_1.asItemStack(),
            ItemUtils.createColoredLeatherArmor(Material.LEATHER_CHESTPLATE, Color.fromRGB(74, 39, 0)),
            ItemUtils.createColoredLeatherArmor(Material.LEATHER_LEGGINGS, Color.fromRGB(74, 39, 0)),
            ItemUtils.createColoredLeatherArmor(Material.LEATHER_BOOTS, Color.fromRGB(74, 39, 0)),
            new ItemStack(Material.PAPER)));

    private final MobEquipmentMeta meta;

    EquipmentSet(MobEquipmentMeta meta) {
        this.meta = meta;
    }

    public MobEquipmentMeta asMobEquipmentMeta() {
        return this.meta;
    }
}
