package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Goblin {

    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.GOBLIN_LOW_LVL_1.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(2),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 6, "goblin_1", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_LOW_LVL_2.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(2),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 6, "goblin_2", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_LOW_LVL_3.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(2),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 6, "goblin_3", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_LOW_LVL_4.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(2),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 6, "goblin_4", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_LOW_LVL_5.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(2),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 6, "goblin_5", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));


    }
}
