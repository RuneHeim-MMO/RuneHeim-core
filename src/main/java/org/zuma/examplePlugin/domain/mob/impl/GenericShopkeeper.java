package org.zuma.examplePlugin.domain.mob.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.mob.base.AbstractShopkeeper;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobTypeId;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyStaticImmortalMobMeta;

public class GenericShopkeeper extends AbstractShopkeeper {

    public GenericShopkeeper(int id, String displayName, EquipmentSet equipmentSet, MobBehavior behavior, int shopId) {
        super(new FriendlyStaticImmortalMobMeta(id, MobTypeId.IMMORTAL_STATIC_SHOPKEEPER.asInt(), Component.text(displayName), EntityType.ZOMBIE, true), shopId);
        super.setMobEquipment(equipmentSet.asMobEquipmentMeta());
        super.setBehavior(behavior);
    }


}
