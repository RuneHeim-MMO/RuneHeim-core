package org.zuma.examplePlugin.domain.combat;

import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.combat.constants.HitType;

public class Hit {
    private final CombatEntity attacker;
    private final CombatEntity defender;
    private final AttackType attackType;
    private int damage;
    private final HitType hitType;
    private final int maxDamage;
    private final double chance;
    private final boolean isSpecialAttack, isSplashHit;

    // Canonical constructor
    public Hit(CombatEntity attacker, CombatEntity defender, AttackType attackType, int damage,
               HitType hitType, int maxDamage, double chance, boolean isSpecialAttack, boolean isSplashHit) {
        this.attacker = attacker;
        this.defender = defender;
        this.attackType = attackType;
        this.damage = damage;
        this.hitType = hitType;
        this.maxDamage = maxDamage;
        this.chance = chance;
        this.isSpecialAttack = isSpecialAttack;
        this.isSplashHit = isSplashHit;
    }

    // Getters (same names as record fields)
    public CombatEntity getAttacker() {
        return attacker;
    }

    public CombatEntity getDefender() {
        return defender;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public int getDamage() {
        return damage;
    }

    public HitType getHitType() {
        return hitType;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public double getSuccessHitChance() {
        return chance;
    }

    public void overrideDamage(int damage) {
        this.damage = damage;
    }

    public boolean isSpecialAttack() {
        return isSpecialAttack;
    }

    public boolean isSplashHit() {
        return isSplashHit;
    }
}
