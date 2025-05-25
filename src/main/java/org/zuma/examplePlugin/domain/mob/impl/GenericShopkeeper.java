package org.zuma.examplePlugin.domain.mob.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.domain.heads.HeadTexture;
import org.zuma.examplePlugin.domain.mob.base.AbstractShopkeeper;
import org.zuma.examplePlugin.domain.mob.constants.MobTypeId;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyStaticImmortalMobMeta;
import org.zuma.examplePlugin.domain.mob.meta.MobEquipmentMeta;

public class GenericShopkeeper extends AbstractShopkeeper {

    public GenericShopkeeper(int id, String displayName, int shopId) {
        super(new FriendlyStaticImmortalMobMeta(id, MobTypeId.IMMORTAL_STATIC_SHOPKEEPER.asInt(), Component.text(displayName), EntityType.ZOMBIE), shopId);
        super.setMobEquipment(new MobEquipmentMeta(HeadTexture.IRON_HELM.asItemStack(), null, null, null, new ItemStack(Material.PAPER)));
    }


}
