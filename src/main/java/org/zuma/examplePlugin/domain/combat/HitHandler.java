package org.zuma.examplePlugin.domain.combat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.events.combat.RuneEntityDamageEntityEvent;

public class HitHandler {

    private boolean getIsCriticalHit(CombatEntity entity, Hit hit) {
        int percentMaxHpHitCritical = 10;
        return hit.damage() > (entity.getHealth() * percentMaxHpHitCritical / 100.0);
    }

    private void handleCriticalSound(CombatEntity entity, AttackType type, boolean isCriticalHit) {
        if (!isCriticalHit) {
            return;
        }

        switch (type) {
            case MELEE:
                SoundType.HIT_MELEE_CRITICAL.play(entity.getEntity().getLocation());
                break;
            case MAGIC:
                SoundType.HIT_MAGIC_CRITICAL.play(entity.getEntity().getLocation());
                break;
            case RANGED:
                SoundType.HIT_RANGED_CRITICAL.play(entity.getEntity().getLocation());
                break;
            default:
                break;
        }
    }

    public void handleMeleeHit(CombatEntity attacker, CombatEntity defender) {
        AttackType type = AttackType.MELEE;
        Hit hit = HitCalculator.getHitDamage(attacker, defender, type);
        boolean isCriticalHit = getIsCriticalHit(defender, hit);

        RuneEntityDamageEntityEvent event = new RuneEntityDamageEntityEvent(attacker, defender, hit, type);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }


        defender.takeHit(hit);

        if (attacker.getEntity() instanceof Player p) {
            p.sendMessage("Your max hit: " + HitCalculator.getMaxHit(attacker, AttackType.MELEE));
        }

        if (defender.getEntity() instanceof Player p) {
            p.sendMessage("Attackers max hit: " + HitCalculator.getMaxHit(attacker, AttackType.MELEE));
        }

        handleCriticalSound(defender, type, isCriticalHit);
        HudService.showDamageBubble(defender, hit, type, isCriticalHit);
    }
}
