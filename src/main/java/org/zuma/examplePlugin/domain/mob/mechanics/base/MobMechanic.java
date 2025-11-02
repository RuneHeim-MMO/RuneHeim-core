package org.zuma.examplePlugin.domain.mob.mechanics.base;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public interface MobMechanic {

    public void setParent(AbstractMob mob);

    public void execute(LivingEntity entity);

    default void onDamagedByPlayer(RunePlayer attacker, Hit hit) {
    }


    default void onMobDamagePlayer(RunePlayer target, Hit hit) {
    }

    default void onEntityTargetPlayer(EntityTargetEvent event, RunePlayer player) {
    }

    default void onSpawn() {
    }


    default void onDeath(RunePlayer attacker, Hit lastHit) {
    }

    default void tick() {

    }

}
