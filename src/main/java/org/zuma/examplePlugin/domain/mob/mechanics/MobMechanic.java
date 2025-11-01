package org.zuma.examplePlugin.domain.mob.mechanics;

import org.bukkit.entity.Player;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public interface MobMechanic {

    default void onDamagedByPlayer(RunePlayer attacker, Hit hit) {
    }


    default void onMobDamagePlayer(RunePlayer target) {
    }


    default void onSpawn() {
    }


    default void onDeath(RunePlayer attacker, Hit lastHit) {
    }

    default void tick() {

    }

}
