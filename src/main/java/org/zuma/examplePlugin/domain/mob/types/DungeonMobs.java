package org.zuma.examplePlugin.domain.mob.types;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.combat.constants.ProjectileType;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;
import org.zuma.examplePlugin.domain.mob.mechanics.RangedMobMechanic;
import org.zuma.examplePlugin.domain.mob.projectiles.RuneProjectile;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class DungeonMobs {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.LAVA_DUNGEON_TURRET_BASE_20.getId(), () -> new GenericCombatant(
                "Dungeon Turret", null, getBaseSkills(20),
                null, MobBehaviors.BLAZE.getBehavior(),
                0.3, true, false, 18, null, null, DisguiseType.BLAZE,
                List.of(DropTableId.DUNGEON_TURRET.asInt()),
                true).overrideBaseToSkeleton().addMobMechanic(new RangedMobMechanic(new RuneProjectile(ProjectileType.RANGED, EntityType.SHULKER_BULLET, 2), false, 50)));

        registry.register(MobType.LAVA_DUNGEON_GUARD_1_BASE_35.getId(), () -> new GenericCombatant(
                "Dungeon Keeper", EquipmentSet.STEEL_FULL_SWORD, getBaseSkills(35),
                null, MobBehaviors.RAVAGER.getBehavior(),
                0.35, true, false, 35, "dungeon_keeper_1", null, null,
                List.of(DropTableId.LAVA_DUNGEON_KEY_A.asInt()),
                false).setMaxWalkDistance(20));

        registry.register(MobType.LAVA_DUNGEON_GUARD_2_BASE_40.getId(), () -> new GenericCombatant(
                "Dungeon Keeper", EquipmentSet.MITHRIL_FULL_SWORD, getBaseSkills(40),
                null, MobBehaviors.RAVAGER.getBehavior(),
                0.35, true, false, 40, "dungeon_keeper_2", null, null,
                List.of(DropTableId.LAVA_DUNGEON_KEY_B.asInt()),
                false).setMaxWalkDistance(20));

        registry.register(MobType.LAVA_DUNGEON_GUARD_3_BASE_45.getId(), () -> new GenericCombatant(
                "Dungeon Keeper", EquipmentSet.ADAMANT_FULL_SWORD, getBaseSkills(45),
                null, MobBehaviors.RAVAGER.getBehavior(),
                0.35, true, false, 45, "dungeon_keeper_3", null, null,
                List.of(DropTableId.LAVA_DUNGEON_KEY_C.asInt()),
                false).setMaxWalkDistance(20));

        registry.register(MobType.LAVA_DUNGEON_GUARD_4_BASE_50.getId(), () -> new GenericCombatant(
                "Dungeon Keeper", EquipmentSet.RUNE_FULL_SWORD, getBaseSkills(50),
                null, MobBehaviors.RAVAGER.getBehavior(),
                0.35, true, false, 50, "dungeon_keeper_4", null, null,
                List.of(DropTableId.LAVA_DUNGEON_KEY_D.asInt()),
                false).setMaxWalkDistance(20));


    }
}

