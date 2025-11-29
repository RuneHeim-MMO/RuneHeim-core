package org.zuma.examplePlugin.domain.mob.types;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.combat.constants.ProjectileType;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;
import org.zuma.examplePlugin.domain.mob.impl.ShearableSheep;
import org.zuma.examplePlugin.domain.mob.mechanics.traits.RangedMobMechanic;
import org.zuma.examplePlugin.domain.mob.projectiles.RuneProjectile;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Common {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.CHICKEN_BASE_2.getId(), () -> new GenericCombatant(
                "Chicken", null, getBaseSkills(2, 0.5),
                null, MobBehaviors.CHICKEN.getBehavior(),
                0.2, false, false, 3, null, null, DisguiseType.CHICKEN,
                List.of(DropTableId.BONES.asInt(), DropTableId.FEATHERS_1_TO_15.asInt(), DropTableId.CHICKEN_MEAT.asInt()),
                true));


        registry.register(MobType.HOGLIN_BASE_5.getId(), () -> new GenericCombatant(
                "Hoglin", null, getBaseSkills(5, 0.5),
                null, MobBehaviors.HOGLIN.getBehavior(),
                0.24, false, false, 9, null, null, DisguiseType.HOGLIN,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.HOGLIN.asInt()),
                true));

        registry.register(MobType.INFERNAK_WARHOUND_BASE_80.getId(), () -> new GenericCombatant(
                "Warhound", null, getBaseSkills(80, 0.5),
                null, MobBehaviors.LLAMA.getBehavior(),
                0.35, false, true, 65, null, null, DisguiseType.RAVAGER,
                List.of(DropTableId.BIG_BONES.asInt(), DropTableId.WARHOUND.asInt()),
                false));

        registry.register(MobType.LLAMA_BASE_56.getId(), () -> new GenericCombatant(
                "Llama", null, getBaseSkills(56, 0.5),
                null, MobBehaviors.LLAMA.getBehavior(),
                0.25, false, false, 48, null, null, DisguiseType.LLAMA,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.WOOL.asInt()),
                true).overrideBaseToSkeleton().addMobMechanic(new RangedMobMechanic(new RuneProjectile(ProjectileType.RANGED, EntityType.LLAMA_SPIT, 2), false, 50)));

        registry.register(MobType.STRIDER_BASE_60.getId(), () -> new GenericCombatant(
                "Strider", null, getBaseSkills(60, 0.5),
                null, MobBehaviors.STRIDER.getBehavior(),
                0.35, false, false, 55, null, null, DisguiseType.STRIDER,
                List.of(DropTableId.BIG_BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));

        registry.register(MobType.SNIFFER_BASE_65.getId(), () -> new GenericCombatant(
                "Sniffer", null, getBaseSkills(65, 0.5),
                null, MobBehaviors.RAVAGER.getBehavior(),
                0.35, false, false, 59, null, null, DisguiseType.SNIFFER,
                List.of(DropTableId.BONES.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt()),
                true));

        registry.register(MobType.PENGUIN_BASE_40.getId(), () -> new GenericCombatant(
                "Penguin", null,
                getBaseSkills(40, 0.5),
                null,
                MobBehaviors.PENGUIN.getBehavior(),
                "nm_penguin",
                0.32, false, false, 30,
                List.of(DropTableId.FEATHERS_3_TO_8.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.PENGUIN_BABY_BASE_30.getId(), () -> new GenericCombatant(
                "Baby Penguin", null,
                getBaseSkills(30, 0.5),
                null,
                MobBehaviors.PENGUIN.getBehavior(),
                "nm_penguin_baby",
                0.3, false, false, 20,
                List.of(DropTableId.FEATHERS_3_TO_8.asInt(), DropTableId.RAW_ANIMAL_MEAT.asInt(), DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.PANDA_BASE_35.getId(), () -> new GenericCombatant(
                "Panda", null, getBaseSkills(35, 0.6),
                null, MobBehaviors.PANDA.getBehavior(),
                0.35, false, false, 30, null, null, DisguiseType.PANDA,
                List.of(DropTableId.BONES.asInt()),
                true));


        registry.register(MobType.AXOLOTL.getId(), () -> new GenericCombatant(
                "Axolotl", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.AXOLOTL.getBehavior(),
                0.25, false, false, 3, null, null, DisguiseType.AXOLOTL,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.BEE.getId(), () -> new GenericCombatant(
                "Bee", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.BEE.getBehavior(),
                0.30, false, false, 3, null, null, DisguiseType.BEE,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.CAT.getId(), () -> new GenericCombatant(
                "Cat", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.CAT.getBehavior(),
                0.32, false, false, 3, null, null, DisguiseType.CAT,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.DONKEY.getId(), () -> new GenericCombatant(
                "Donkey", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.DONKEY.getBehavior(),
                0.22, false, false, 3, null, null, DisguiseType.DONKEY,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.FOX.getId(), () -> new GenericCombatant(
                "Fox", null, getBaseSkills(40, 0.5),
                null, MobBehaviors.FOX.getBehavior(),
                0.36, false, false, 60, null, null, DisguiseType.FOX,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.FROG.getId(), () -> new GenericCombatant(
                "Frog", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.FROG.getBehavior(),
                0.26, false, false, 3, null, null, DisguiseType.FROG,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.GOAT.getId(), () -> new GenericCombatant(
                "Goat", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.GOAT.getBehavior(),
                0.28, false, false, 3, null, null, DisguiseType.GOAT,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.HUSK.getId(), () -> new GenericCombatant(
                "Husk", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.HUSK.getBehavior(),
                0.20, false, false, 3, null, null, DisguiseType.HUSK,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.LLAMA.getId(), () -> new GenericCombatant(
                "Llama", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.LLAMA.getBehavior(),
                0.23, false, false, 3, null, null, DisguiseType.LLAMA,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.PIG.getId(), () -> new GenericCombatant(
                "Pig", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.PIG.getBehavior(),
                0.25, false, false, 3, null, null, DisguiseType.PIG,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.POLAR_BEAR.getId(), () -> new GenericCombatant(
                "Polar Bear", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.POLAR_BEAR.getBehavior(),
                0.22, false, false, 3, null, null, DisguiseType.POLAR_BEAR,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.RABBIT.getId(), () -> new GenericCombatant(
                "Rabbit", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.RABBIT.getBehavior(),
                0.38, false, false, 3, null, null, DisguiseType.RABBIT,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.RAVAGER.getId(), () -> new GenericCombatant(
                "Ravager", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.RAVAGER.getBehavior(),
                0.20, false, false, 3, null, null, DisguiseType.RAVAGER,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.SNIFFER.getId(), () -> new GenericCombatant(
                "Sniffer", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.SNIFFER.getBehavior(),
                0.22, false, false, 3, null, null, DisguiseType.SNIFFER,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.SPIDER.getId(), () -> new GenericCombatant(
                "Spider", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.SPIDER.getBehavior(),
                0.30, false, false, 3, null, null, DisguiseType.SPIDER,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.STRIDER.getId(), () -> new GenericCombatant(
                "Strider", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.STRIDER.getBehavior(),
                0.27, false, false, 3, null, null, DisguiseType.STRIDER,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.TURTLE.getId(), () -> new GenericCombatant(
                "Turtle", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 5), new SkillLevelRecord(SkillType.STRENGTH, 5), new SkillLevelRecord(SkillType.DEFENCE, 10)),
                null, MobBehaviors.TURTLE.getBehavior(),
                0.12, false, false, 3, null, null, DisguiseType.TURTLE,
                List.of(DropTableId.BONES.asInt()),
                true));

        registry.register(MobType.WITCH.getId(), () -> new GenericCombatant(
                "Witch", null, List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null, MobBehaviors.WITCH.getBehavior(),
                0.22, false, false, 3, null, null, DisguiseType.WITCH,
                List.of(DropTableId.BONES.asInt()),
                true));


        registry.register(MobType.SHEEP.getId(), () -> new ShearableSheep("Sheep",
                List.of(new SkillLevelRecord(SkillType.ATTACK, 1), new SkillLevelRecord(SkillType.STRENGTH, 1), new SkillLevelRecord(SkillType.DEFENCE, 3)),
                null, MobBehaviors.SHEEP.getBehavior(),
                0.2, 5));


    }
}
