package org.zuma.examplePlugin.domain.mob;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.impl.CityGuardMob;

public class CityGuardHandler implements Listener {


    public CityGuardHandler() {
        startVariableSpeedLoop();
    }

    private void startVariableSpeedLoop() {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::variableSpeedTick, 20L, 60L);
    }

    private void variableSpeedTick() {
        RuneQuestPlugin.getMobRegistry().getAll().stream().filter(mob -> mob instanceof CityGuardMob).forEach(cityGuard -> {
            Entity cityGuardEntity = cityGuard.getEntity();

            if (!(cityGuardEntity instanceof LivingEntity livingCityGuard)) {
                return;
            }

            if (!(livingCityGuard instanceof Mob mob)) {
                return;
            }

            LivingEntity target = mob.getTarget();

            if (target == null || target.isDead()) {
                ((CityGuardMob) cityGuard).disengage();
            }
        });
    }


    @EventHandler
    public void onMobAttackNearCityGuard(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        AbstractMob attacker = null;

        // Handle direct mob attacks
        if (damager instanceof Mob damagerMob) {
            attacker = RuneQuestPlugin.getMobRegistry().getMob(damagerMob);
        }
        // Handle projectiles (arrows, snowballs, etc.)
        else if (damager instanceof Projectile projectile) {
            ProjectileSource shooter = projectile.getShooter();
            if (shooter instanceof Mob shooterMob) {
                attacker = RuneQuestPlugin.getMobRegistry().getMob(shooterMob);
            }
        }

        if (attacker == null) return;

        // Only interested if the target is a player or a horse with passengers
        Entity target = event.getEntity();
        if (!(target instanceof Player) && !(target instanceof AbstractHorse h && !h.getPassengers().isEmpty())) {
            return;
        }

        if (!(attacker.getEntity() instanceof LivingEntity livingAttacker)) return;

        // Notify nearby city guards
        for (Entity entity : attacker.getEntity().getNearbyEntities(20, 10, 20)) {
            AbstractMob witness = RuneQuestPlugin.getMobRegistry().getMob(entity);
            if (witness instanceof CityGuardMob cityGuardMob) {
                cityGuardMob.onWitnessAttack(livingAttacker);
            }
        }
    }
}
