package org.zuma.examplePlugin.domain.mob.constants;

import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.impl.GenericShopkeeper;

public enum MobType {
    CHESTPLATE_SHOP(new GenericShopkeeper("Chestplate Shop 0", EquipmentSet.SHOPKEEPER_1, MobBehaviors.VILLAGER_MAN_1.getBehavior(), "shopkeeper", 0, false));

    private final AbstractMob mob;

    MobType(AbstractMob mob) {
        this.mob = mob;
    }


    public AbstractMob getNewInstance() {
        return mob.copy();
    }
}
