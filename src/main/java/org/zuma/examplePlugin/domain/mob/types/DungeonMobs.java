package org.zuma.examplePlugin.domain.mob.types;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.combat.constants.ProjectileType;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;
import org.zuma.examplePlugin.domain.mob.mechanics.attacks.*;
import org.zuma.examplePlugin.domain.mob.mechanics.base.BossMechanic;
import org.zuma.examplePlugin.domain.mob.mechanics.base.BossPhase;
import org.zuma.examplePlugin.domain.mob.mechanics.base.WeightedMechanic;
import org.zuma.examplePlugin.domain.mob.mechanics.traits.InitialEffectMechanic;
import org.zuma.examplePlugin.domain.mob.mechanics.traits.MobEnergyMechanic;
import org.zuma.examplePlugin.domain.mob.mechanics.traits.RangedMobMechanic;
import org.zuma.examplePlugin.domain.mob.mechanics.transitions.ArrowAirBlastPhaseTransition;
import org.zuma.examplePlugin.domain.mob.projectiles.RuneProjectile;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneMobRecord;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class DungeonMobs {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.LAVA_DUNGEON_TURRET_BASE_20.getId(), () -> new GenericCombatant(
                "Dungeon Turret", null, getBaseSkills(20, 0.5),
                null, MobBehaviors.BLAZE.getBehavior(),
                0.3, true, false, 18, null, null, DisguiseType.BLAZE,
                List.of(DropTableId.DUNGEON_TURRET.asInt()),
                true).overrideBaseToSkeleton().addMobMechanic(new RangedMobMechanic(new RuneProjectile(ProjectileType.RANGED, EntityType.SHULKER_BULLET, 2), false, 50)));

        registry.register(MobType.LAVA_DUNGEON_GUARD_1_BASE_35.getId(), () -> new GenericCombatant(
                "Dungeon Keeper", EquipmentSet.STEEL_FULL_SWORD, getBaseSkills(35, 0.5),
                null, MobBehaviors.RAVAGER.getBehavior(),
                0.35, true, false, 35, "dungeon_keeper_1", null, null,
                List.of(DropTableId.LAVA_DUNGEON_KEY_A.asInt()),
                false).setMaxWalkDistance(20));

        registry.register(MobType.LAVA_DUNGEON_GUARD_2_BASE_40.getId(), () -> new GenericCombatant(
                "Dungeon Keeper", EquipmentSet.MITHRIL_FULL_SWORD, getBaseSkills(40, 0.5),
                null, MobBehaviors.RAVAGER.getBehavior(),
                0.35, true, false, 40, "dungeon_keeper_2", null, null,
                List.of(DropTableId.LAVA_DUNGEON_KEY_B.asInt()),
                false).setMaxWalkDistance(20));

        registry.register(MobType.LAVA_DUNGEON_GUARD_3_BASE_45.getId(), () -> new GenericCombatant(
                "Dungeon Keeper", EquipmentSet.ADAMANT_FULL_SWORD, getBaseSkills(45, 0.5),
                null, MobBehaviors.RAVAGER.getBehavior(),
                0.35, true, false, 45, "dungeon_keeper_3", null, null,
                List.of(DropTableId.LAVA_DUNGEON_KEY_C.asInt()),
                false).setMaxWalkDistance(20));

        registry.register(MobType.LAVA_DUNGEON_GUARD_4_BASE_50.getId(), () -> new GenericCombatant(
                "Dungeon Keeper", EquipmentSet.RUNE_FULL_SWORD, getBaseSkills(50, 0.5),
                null, MobBehaviors.RAVAGER.getBehavior(),
                0.35, true, false, 50, "dungeon_keeper_4", null, null,
                List.of(DropTableId.LAVA_DUNGEON_KEY_D.asInt()),
                false).setMaxWalkDistance(20));

        registry.register(MobType.LAVA_DUNGEON_BOSS_BASE_55.getId(), () -> new GenericCombatant(
                "Dungeon Keeper", null, getBaseSkills(50, 1),
                null, MobBehaviors.RAVAGER.getBehavior(),
                0.45, true, false, 100000, "lava_dungeon_boss", null, null, // Hp controlled by boss mechanic
                List.of(),
                false)
                .addMobMechanic(new MobEnergyMechanic(200, 10, 3))
                .addMobMechanic(new InitialEffectMechanic(20, List.of(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1), new PotionEffect(PotionEffectType.SLOWNESS, 200, 1)), "The air grows thick with smoke...", SoundType.RAVAGER_DEATH))
                .addMobMechanic(new BossMechanic(List.of(
                        new BossPhase(80, 80, List.of(new WeightedMechanic(5, 8, new RangedMobMechanic(new RuneProjectile(ProjectileType.RANGED, EntityType.SHULKER_BULLET, 5), true, 70), "Ashes to ashes, intruder!", SoundType.RANGED_ATTACK_FIRE),
                                new WeightedMechanic(1, 10, new SpecialAttackMechanic(), "Prepare to die!", SoundType.SPECIAL_ATTACK_DEATH),
                                new WeightedMechanic(4, 20, new ArrowRainMechanic(200, 10, 5, 5), "Run, little spark! Run!", SoundType.ARROW_RAIN_EXPLOSION),
                                new WeightedMechanic(3, 15, new PotionEffectMechanic(20, List.of(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1))), "Oh no, not the darkness! HAAHAHA!", SoundType.DARKNESS_BLAST),
                                new WeightedMechanic(1, 1, new VariableSpeedMechanic(4, 0.8), "Feel the heat of my wrath!", SoundType.WRATH_LAVA_STEP),
                                new WeightedMechanic(10, 25, new SummonCreaturesMechanic(List.of(
                                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_TURRET_BASE_20, 2, 100),
                                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_GUARD_1_BASE_35, 1, 50)
                                ), 10), "Minions, arise!", SoundType.SUMMON_MINIONS)), new ArrowAirBlastPhaseTransition(10),null),
                        new BossPhase(150, 150, List.of(
                                new WeightedMechanic(5, 8, new RangedMobMechanic(
                                        new RuneProjectile(ProjectileType.RANGED, EntityType.SHULKER_BULLET, 5), false, 100),
                                        "You dare defy me?! Burn!", SoundType.RANGED_ATTACK_FIRE),
                                new WeightedMechanic(1, 5, new SpecialAttackMechanic(),
                                        "I will crush you where you stand!", SoundType.SPECIAL_ATTACK_DEATH),
                                new WeightedMechanic(4, 5, new ArrowRainMechanic(400, 20, 5, 5),
                                        "No escape, feel my fury!", SoundType.ARROW_RAIN_EXPLOSION),
                                new WeightedMechanic(3, 5, new PotionEffectMechanic(20,
                                        List.of(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1))),
                                        "Stumble in the shadows, wretched fools!", SoundType.DARKNESS_BLAST),
                                new WeightedMechanic(3, 5, new PotionEffectMechanic(20,
                                        List.of(new PotionEffect(PotionEffectType.SLOWNESS, 60, 100))),
                                        "FREEZE! NOW!", SoundType.DARKNESS_BLAST),
                                new WeightedMechanic(1, 1, new VariableSpeedMechanic(10, 1),
                                        "Burn! Burn! Burn under my wrath!", SoundType.WRATH_LAVA_STEP),
                                new WeightedMechanic(10, 5, new SummonCreaturesMechanic(List.of(
                                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_TURRET_BASE_20, 2, 100),
                                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_GUARD_1_BASE_35, 1, 50)
                                ), 10), "Rise, my minions! Tear them apart!", SoundType.SUMMON_MINIONS)
                        ), new ArrowAirBlastPhaseTransition(10), Particle.DRAGON_BREATH)))


                ));

    }
}

