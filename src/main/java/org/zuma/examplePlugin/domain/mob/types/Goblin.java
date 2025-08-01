package org.zuma.examplePlugin.domain.mob.types;

import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Goblin {

    public static void registerMobs(MobTypeRegistry registry) {

        registry.register(MobType.GOBLIN_1_BASE_1.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(1),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 5, "goblin_1", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_2_BASE_1.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(1),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 5, "goblin_2", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_3_BASE_1.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(1),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 5, "goblin_3", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_4_BASE_1.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(1),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 5, "goblin_4", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_5_BASE_1.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(1),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 5, "goblin_5", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_1_BASE_4.getId(), () -> new GenericCombatant("Strong Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(4),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 7, "goblin_1", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_2_BASE_4.getId(), () -> new GenericCombatant("Strong Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(5),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 7, "goblin_2", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_3_BASE_4.getId(), () -> new GenericCombatant("Strong Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(4),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 7, "goblin_3", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_4_BASE_4.getId(), () -> new GenericCombatant("Strong Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(5),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 7, "goblin_4", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.GOBLIN_5_BASE_4.getId(), () -> new GenericCombatant("Strong Goblin", EquipmentSet.GOBLIN,
                getBaseSkills(4),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 7, "goblin_5", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));



    }
}
