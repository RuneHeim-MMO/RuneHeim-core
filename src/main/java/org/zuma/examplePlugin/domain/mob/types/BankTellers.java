package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.InventoryOpenedMob;

public class BankTellers {

    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.BANK_TELLER_1.getId(), () -> new InventoryOpenedMob(Icon.COIN_HANDFUL.get() + " Bank Teller", null,
                MobBehaviors.VILLAGER_MAN_4.getBehavior(), "bank_teller_1", null, InventoryId.SPINNING_WHEEL.asInt(), 0));

        registry.register(MobType.BANK_TELLER_2.getId(), () -> new InventoryOpenedMob(Icon.COIN_HANDFUL.get() + " Bank Teller", null,
                MobBehaviors.VILLAGER_MAN_3.getBehavior(), "bank_teller_2", null, InventoryId.SPINNING_WHEEL.asInt(), 0));

        registry.register(MobType.BANK_TELLER_3.getId(), () -> new InventoryOpenedMob(Icon.COIN_HANDFUL.get() + " Bank Teller", null,
                MobBehaviors.VILLAGER_WOMAN_3.getBehavior(), "bank_teller_3", null, InventoryId.SPINNING_WHEEL.asInt(), 0));


    }
}
