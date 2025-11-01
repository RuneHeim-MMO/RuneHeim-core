package org.zuma.examplePlugin.domain.mob.mechanics;

import org.bukkit.entity.*;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.projectiles.RuneProjectile;

public class RangedMobMechanic implements MobMechanic {
    private CombatantMob mob;
    private final RuneProjectile projectileType;
    private final boolean canHybridAttack;


    public RangedMobMechanic(RuneProjectile projectileType, boolean canHybridAttack) {
        this.projectileType = projectileType;
        this.canHybridAttack = canHybridAttack;
    }

    public void setParent(AbstractMob mob) {
        if (!(mob instanceof CombatantMob combatantMob)) {
            throw new IllegalArgumentException("RangedMobMechanic can only be applied to CombatantMob");
        }

        this.mob = combatantMob;
        this.mob.setProjectileType(this.projectileType);
    }

    @Override
    public void tick() {
        if (mob == null) return;
        if (!(mob.getEntity() instanceof LivingEntity e && e instanceof Mob mobEntity)) return;
        Entity target = mobEntity.getTarget();

        if (target instanceof Player playerTarget || target instanceof AbstractHorse abstractHorse) {
            LivingEntity targetEntity = (LivingEntity) target;

            if (e.getType().equals(EntityType.SKELETON)) return; // Skeletons have their own shooting mechanic
            if (canHybridAttack && MathUtils.getLocationsDistanceXZ(e.getLocation(), targetEntity.getLocation()) < 3)
                return; // Skip ranged attack if in melee range and hybrid attack is allowed

            mob.shootProjectileAtTarget(targetEntity);
        }
    }
}
