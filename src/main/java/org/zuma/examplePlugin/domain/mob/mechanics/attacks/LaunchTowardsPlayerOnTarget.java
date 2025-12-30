package org.zuma.examplePlugin.domain.mob.mechanics.attacks;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import org.zuma.examplePlugin.core.utils.MobUtils;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class LaunchTowardsPlayerOnTarget implements MobMechanic {
    private CombatantMob mob;
    private final double strength;

    public LaunchTowardsPlayerOnTarget(double strength) {
        this.strength = strength;
    }

    @Override
    public void setParent(AbstractMob mob) {
        if (!(mob instanceof CombatantMob combatantMob)) {
            throw new IllegalArgumentException("SpecialAttackMechanic can only be applied to CombatantMob");
        }

        this.mob = combatantMob;
    }

    @Override
    public void execute(LivingEntity target) {
        if (mob == null || mob.getEntity() == null || target == null || target.isDead() || !(mob.getEntity() instanceof LivingEntity living))
            return;


        MobUtils.pullEntityTowardMob(living, target, strength, strength / 3);
        System.out.println("Launched " + target.getName() + " towards " + living.getName());
    }

    @Override
    public void cleanup() {
        this.mob = null;
    }

    @Override
    public void onEntityTargetPlayer(EntityTargetEvent event, RunePlayer target) {
        if (mob == null || mob.getEntity() == null || target == null || target.isDead())
            return;

        execute(target.asBukkitPlayer());

    }

}
