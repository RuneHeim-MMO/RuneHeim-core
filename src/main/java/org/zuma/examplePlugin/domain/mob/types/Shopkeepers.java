package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;
import org.zuma.examplePlugin.domain.mob.impl.GenericShopkeeper;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Shopkeepers {

    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.GENERIC_SHOPKEEPER.getId(), () -> new GenericShopkeeper(Icon.CHEST.get() + " General Store", EquipmentSet.SHOPKEEPER_1,
                MobBehaviors.VILLAGER_MAN_1.getBehavior(), "shopkeeper", null, InventoryId.GENERAL_STORE.asInt(), 20));


    }
}
