package org.zuma.examplePlugin.domain.combat;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.mob.MobRegistry;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

import javax.annotation.Nullable;

public class HitListener implements Listener {
    private final RunePlayerManager playerManager;
    private final MobRegistry mobRegistry;
    private final HitHandler hitHandler;

    public HitListener(HitHandler hitHandler, RunePlayerManager playerManager, MobRegistry mobRegistry) {
        this.playerManager = playerManager;
        this.mobRegistry = mobRegistry;
        this.hitHandler = hitHandler;
    }

    @Nullable
    private CombatEntity getCombatEntity(Entity entity) {
        if (entity instanceof Player player) {
            return playerManager.getRunePlayer(player);
        }

        AbstractMob matchingMob = mobRegistry.getMob(entity);
        if (matchingMob instanceof CombatEntity combatMob) {
            return combatMob;
        }

        return null;
    }

    @EventHandler
    public void onEntityAttackEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;

        if (!(event.getEntity() instanceof LivingEntity defenderEntity)) return;

        // Respect vanilla hurt cooldown (red tint period)
        if (defenderEntity.getNoDamageTicks() > 0) {
            event.setCancelled(true);
            return;
        }


        CombatEntity attacker = getCombatEntity(event.getDamager());
        CombatEntity defender = getCombatEntity(defenderEntity);

        if (attacker != null && defender != null) {
            hitHandler.handleMeleeHit(attacker, defender);

            // Optionally reset no damage ticks to mimic vanilla hit cooldown
            defenderEntity.setNoDamageTicks(defenderEntity.getMaximumNoDamageTicks());
        }

        event.setDamage(0); // Cancel real damage
    }
}
