package org.zuma.examplePlugin.domain.mob.constants;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.mob.meta.MobEquipmentMeta;

public enum EquipmentSet {
    SHOPKEEPER_1(new MobEquipmentMeta(null, null, null, null, new ItemStack(Material.PAPER))),
    BANDIT_1(new MobEquipmentMeta(null, new ItemStack(Material.DIAMOND_CHESTPLATE), null, null, RuneQuestPlugin.getItemRegistry().getItem(ItemId.ADAMANTITE_DAGGER).asItemStack())),
    BANDIT_2(new MobEquipmentMeta(RuneQuestPlugin.getItemRegistry().getItem(ItemId.RUNITE_HELMET).asItemStack(), null, null, null, RuneQuestPlugin.getItemRegistry().getItem(ItemId.ADAMANTITE_SWORD).asItemStack()));


    private final MobEquipmentMeta meta;

    EquipmentSet(MobEquipmentMeta meta) {
        this.meta = meta;
    }

    public MobEquipmentMeta asMobEquipmentMeta() {
        return this.meta;
    }
}
