package org.zuma.examplePlugin.domain.mob;

import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericHumanoidCombatant;
import org.zuma.examplePlugin.domain.mob.impl.GenericShopkeeper;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class MobTypesLoader {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.GENERIC_SHOPKEEPER.getId(), () -> new GenericShopkeeper("Chestplate Shop 0", EquipmentSet.SHOPKEEPER_1, MobBehaviors.VILLAGER_MAN_1.getBehavior(), "shopkeeper", 0, false));
        registry.register(MobType.AGGRESSIVE_BANDIT.getId(), () -> new GenericHumanoidCombatant("Bandit", null,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 1), new SkillLevelRecord(SkillType.STRENGTH, 1), new SkillLevelRecord(SkillType.DEFENCE, 1)),
                List.of(new Modifier(ModifierType.ATTACK_SLASH, 5)), MobBehaviors.VILLAGER_MAN_1.getBehavior(),
                0.23, false, true, 50, "bandit"));
    }

}
