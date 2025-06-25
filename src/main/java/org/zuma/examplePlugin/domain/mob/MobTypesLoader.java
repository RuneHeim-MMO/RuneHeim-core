package org.zuma.examplePlugin.domain.mob;

import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;
import org.zuma.examplePlugin.domain.mob.impl.GenericShopkeeper;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class MobTypesLoader {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.GENERIC_SHOPKEEPER.getId(), () -> new GenericShopkeeper("Chestplate Shop 0", EquipmentSet.SHOPKEEPER_1, MobBehaviors.VILLAGER_MAN_1.getBehavior(), "shopkeeper", 0, false));

        registry.register(MobType.AGGRESSIVE_BANDIT_1.getId(), () -> new GenericCombatant("Bandit", EquipmentSet.BANDIT_1,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 1), new SkillLevelRecord(SkillType.STRENGTH, 1), new SkillLevelRecord(SkillType.DEFENCE, 1)),
                List.of(new Modifier(ModifierType.ATTACK_SLASH, 5)), MobBehaviors.VILLAGER_MAN_1.getBehavior(),
                0.23, false, true, 50, "bandit"));

        registry.register(MobType.AGGRESSIVE_BANDIT_2.getId(), () -> new GenericCombatant("Bandit", EquipmentSet.BANDIT_2,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 1), new SkillLevelRecord(SkillType.STRENGTH, 1), new SkillLevelRecord(SkillType.DEFENCE, 1)),
                List.of(new Modifier(ModifierType.ATTACK_SLASH, 5)), MobBehaviors.VILLAGER_MAN_1.getBehavior(),
                0.23, false, true, 50, "bandit"));


        registry.register(MobType.GOBLIN_LOW_LVL_1.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN_1,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 5), new SkillLevelRecord(SkillType.STRENGTH, 5), new SkillLevelRecord(SkillType.DEFENCE, 5)),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 8, "goblin_1"));


        registry.register(MobType.GOBLIN_LOW_LVL_2.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN_2,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 40), new SkillLevelRecord(SkillType.STRENGTH, 40), new SkillLevelRecord(SkillType.DEFENCE, 40)),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.33, false, false, 8, "goblin_1"));


    }

}
