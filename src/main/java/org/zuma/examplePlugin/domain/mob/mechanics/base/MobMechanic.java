package org.zuma.examplePlugin.domain.mob.mechanics.base;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public interface MobMechanic {

    void setParent(@Nullable AbstractMob mob);

    void execute(LivingEntity entity);

    void cleanup();

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
