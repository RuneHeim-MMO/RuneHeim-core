package org.zuma.examplePlugin.domain.mob.mechanics.attacks;

import org.bukkit.entity.LivingEntity;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class SpecialAttackMechanic implements MobMechanic {
    private CombatantMob mob;

    public SpecialAttackMechanic() {
    }

    @Override
    public void setParent(AbstractMob mob) {
        if (!(mob instanceof CombatantMob combatantMob)) {
            throw new IllegalArgumentException("SpecialAttackMechanic can only be applied to CombatantMob");
        }

        this.mob = combatantMob;
    }

    @Override
    public void onMobDamagePlayer(RunePlayer target, Hit hit) {
        if (mob == null) return;
        mob.setSpecialAttacking(false);
    }

    @Override
    public void execute(LivingEntity target) {
        if (mob == null) return;
        mob.setSpecialAttacking(true);
    }

    @Override
    public void cleanup() {
        this.mob = null;
    }
}
