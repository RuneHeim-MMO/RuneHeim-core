package org.zuma.examplePlugin.domain.mob;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
        if (!(event.getDamager() instanceof Mob damagerMob)) return;
        if (!(event.getEntity() instanceof Player)) return;

        AbstractMob attacker = RuneQuestPlugin.getMobRegistry().getMob(damagerMob);

        if (attacker == null) {
            return;
        }

        for (Entity entity : damagerMob.getNearbyEntities(20, 10, 20)) {
            AbstractMob witness = RuneQuestPlugin.getMobRegistry().getMob(entity);
            if (!(witness instanceof CityGuardMob cityGuardMob)) {
                continue;
            }

            cityGuardMob.onWitnessAttack(damagerMob);
        }
    }
}
