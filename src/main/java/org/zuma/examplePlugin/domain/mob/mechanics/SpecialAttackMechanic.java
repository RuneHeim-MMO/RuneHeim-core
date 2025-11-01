package org.zuma.examplePlugin.domain.mob.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.projectiles.RuneProjectile;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class SpecialAttackMechanic implements MobMechanic {
    private CombatantMob mob;

    public SpecialAttackMechanic() {
    }

    public void setParent(AbstractMob mob) {
        if (!(mob instanceof CombatantMob combatantMob)) {
            throw new IllegalArgumentException("SpecialAttackMechanic can only be applied to CombatantMob");
        }

        this.mob = combatantMob;
    }

    // TODO: energy adjustments
    @Override
    public void onMobDamagePlayer(RunePlayer target, Hit hit) {
        mob.setSpecialAttacking(true);

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (mob != null) mob.setSpecialAttacking(false);
        }, 1L); // 2 seconds later
    }
}
