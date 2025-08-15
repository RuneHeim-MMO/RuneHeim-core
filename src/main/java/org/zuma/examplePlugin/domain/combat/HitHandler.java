package org.zuma.examplePlugin.domain.combat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.events.combat.RuneEntityDamageEntityEvent;

import javax.annotation.Nullable;

public class HitHandler {


    private boolean getIsCriticalHit(CombatEntity entity, Hit hit) {
        int percentMaxHpHitCritical = 10;
        return hit.damage() > (entity.getHealth() * percentMaxHpHitCritical / 100.0);
    }

    private void handleHitSound(CombatEntity entity, AttackType type, Hit hit) {
        SoundType sound = null;

        switch (hit.hitType()) {
            case CRIT -> {
                switch (type) {
                    case MELEE -> sound = SoundType.HIT_MELEE_CRITICAL;
                    case MAGIC -> sound = SoundType.HIT_MAGIC_CRITICAL;
                    case RANGED -> sound = SoundType.HIT_RANGED_CRITICAL;
                }
            }
            case REGULAR -> {
                switch (type) {
                    case MELEE -> sound = SoundType.HIT_MELEE_REGULAR;
                    case MAGIC -> sound = SoundType.HIT_MAGIC_REGULAR;
                    case RANGED -> sound = SoundType.HIT_RANGED_REGULAR;
                }
            }
            case MISS -> {
                switch (type) {
                    case MELEE -> sound = SoundType.HIT_MELEE_MISS;
                    case MAGIC -> sound = SoundType.HIT_MAGIC_MISS;
                    case RANGED -> sound = SoundType.HIT_RANGED_MISS;
                }
            }
        }

        if (sound != null) {
            sound.play(entity.getEntity().getLocation());
        }
    }

    @Nullable
    public Hit handleMeleeHit(CombatEntity attacker, CombatEntity defender) {
        AttackType type = AttackType.MELEE;
        Hit hit = HitCalculator.getNewHit(attacker, defender, type);

        RuneEntityDamageEntityEvent event = new RuneEntityDamageEntityEvent(attacker, defender, hit, type);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return null;
        }

        defender.takeHit(hit);
        handleHitSound(defender, type, hit);
        HudService.showDamageBubble(defender, hit, type);
        return hit;
    }

    @Nullable
    public Hit handleRangedHit(CombatEntity attacker, CombatEntity defender) {
        AttackType type = AttackType.RANGED;
        Hit hit = HitCalculator.getNewHit(attacker, defender, type);

        RuneEntityDamageEntityEvent newEvent = new RuneEntityDamageEntityEvent(attacker, defender, hit, type);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            return null;
        }

        defender.takeHit(hit);
        handleHitSound(defender, type, hit);
        HudService.showDamageBubble(defender, hit, type);
        return hit;
    }

    @Nullable
    public Hit handleMagicHit(CombatEntity attacker, CombatEntity defender) {
        AttackType type = AttackType.MAGIC;
        Hit hit = HitCalculator.getNewHit(attacker, defender, type);

        RuneEntityDamageEntityEvent newEvent = new RuneEntityDamageEntityEvent(attacker, defender, hit, type);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            return null;
        }

        defender.takeHit(hit);
        handleHitSound(defender, type, hit);
        HudService.showDamageBubble(defender, hit, type);
        return hit;
    }
}
