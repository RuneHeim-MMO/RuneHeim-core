package org.zuma.examplePlugin.domain.mob.types;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.combat.constants.ProjectileType;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;
import org.zuma.examplePlugin.domain.mob.mechanics.RangedMobMechanic;
import org.zuma.examplePlugin.domain.mob.projectiles.RuneProjectile;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class DungeonMobs {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.LAVA_DUNGEON_BLAZE_BASE_35.getId(), () -> new GenericCombatant(
                "Dungeon Turret", null, getBaseSkills(35),
                null, MobBehaviors.ARMADILLO.getBehavior(),
                0.3, true, false, 35, null, null, DisguiseType.BLAZE,
                List.of(DropTableId.BONES.asInt()),
                false).addMobMechanic(new RangedMobMechanic(new RuneProjectile(ProjectileType.RANGED, EntityType.CHICKEN, 2), true)));

        registry.register(MobType.LAVA_DUNGEON_BLAZE_BASE_37.getId(), () -> new GenericCombatant(
                "Dungeon Turret", null, getBaseSkills(37),
                null, MobBehaviors.ARMADILLO.getBehavior(),
                0.3, true, false, 40, null, null, DisguiseType.BLAZE,
                List.of(DropTableId.BONES.asInt()),
                false).overrideBaseToSkeleton());


        registry.register(MobType.LAVA_DUNGEON_BLAZE_BASE_39.getId(), () -> new GenericCombatant(
                "Dungeon Turret", null, getBaseSkills(39),
                null, MobBehaviors.ARMADILLO.getBehavior(),
                0.3, true, false, 43, null, null, DisguiseType.BLAZE,
                List.of(DropTableId.BONES.asInt()),
                false).overrideBaseToSkeleton());


    }
}

