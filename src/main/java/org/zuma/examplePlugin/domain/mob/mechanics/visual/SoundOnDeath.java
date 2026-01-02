package org.zuma.examplePlugin.domain.mob.mechanics.visual;

import org.bukkit.entity.LivingEntity;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class SoundOnDeath implements MobMechanic {
    private CombatantMob mob;
    private final SoundType sound;

    public SoundOnDeath(SoundType sound) {
        this.sound = sound;
    }

    @Override
    public void setParent(AbstractMob mob) {
        if (!(mob instanceof CombatantMob combatantMob)) {
            throw new IllegalArgumentException("SpecialAttackMechanic can only be applied to CombatantMob");
        }

        this.mob = combatantMob;
    }

    @Override
    public void execute(LivingEntity entity) {
        sound.play(entity.getLocation());
    }


    @Override
    public void onDeath(RunePlayer attacker, Hit lastHit) {
        if (mob == null || mob.getEntity() == null || !(mob.getEntity() instanceof LivingEntity living)) return;
        execute(living);
    }

    @Override
    public void cleanup() {
        this.mob = null;
    }

}
