package org.zuma.examplePlugin.domain.mob.mechanics.attacks.boss;

import org.bukkit.entity.LivingEntity;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;

public class VariableSpeedMechanic implements MobMechanic {
    private final double activeSpeed;
    private final int durationSeconds;
    private CombatantMob mob;
    private Double baseSpeed;
    private int secondsRemaining = 0;

    public VariableSpeedMechanic(int durationSeconds, double activeSpeed) {
        this.durationSeconds = durationSeconds;
        this.activeSpeed = activeSpeed;
    }

    @Override
    public void setParent(AbstractMob mob) {
        if (!(mob instanceof CombatantMob combatantMob)) {
            throw new IllegalArgumentException("VariableSpeedMechanic can only be applied to CombatantMob");
        }

        this.mob = combatantMob;
    }

    @Override
    public void execute(LivingEntity target) {
        if (mob == null || mob.getEntity() == null) return;
        secondsRemaining = durationSeconds;
        mob.setLivingEntitySpeedAttr(activeSpeed);
    }

    @Override
    public void tick() {
        if (mob == null || mob.getEntity() == null) return;

        if (baseSpeed == null) {
            baseSpeed = mob.getLivingEntitySpeedAttr();
        }


        if (secondsRemaining > 0) {
            secondsRemaining--;
            if (secondsRemaining == 0) {
                mob.setLivingEntitySpeedAttr(baseSpeed);
            }
        }
    }

    @Override
    public void cleanup() {
        this.mob = null;
    }
}
