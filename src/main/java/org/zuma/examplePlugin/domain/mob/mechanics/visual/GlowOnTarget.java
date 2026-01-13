package org.zuma.examplePlugin.domain.mob.mechanics.visual;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class GlowOnTarget implements MobMechanic {
    private CombatantMob mob;
    private BukkitTask glowTask;

    public GlowOnTarget() {

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
        if (glowTask != null) {
            glowTask.cancel();
            glowTask = null;
        }


        glowTask = Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), () -> {
            if (mob == null || mob.getEntity() == null || mob.getEntity().isDead() || !mob.getEntity().isValid()) {
                if (glowTask != null) {
                    glowTask.cancel();
                    glowTask = null;
                }
                return;
            }
            Location loc = mob.getEntity().getLocation();
            loc.getWorld().spawnParticle(Particle.END_ROD, loc.add(0, 1, 0), 10, 0.5, 0.5, 0.5, 0.01);
        }, 0L, 5L);

    }

    @Override
    public void onEntityTargetPlayer(EntityTargetEvent event, RunePlayer player) {
        if (mob == null || mob.getEntity() == null) return;
        execute(player.asBukkitPlayer());
    }

    @Override
    public void cleanup() {
        this.mob = null;

        if (glowTask != null) {
            glowTask.cancel();
            glowTask = null;
        }
    }

}
