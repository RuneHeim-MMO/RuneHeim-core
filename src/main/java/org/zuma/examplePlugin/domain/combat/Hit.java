package org.zuma.examplePlugin.domain.combat;

import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.combat.constants.HitType;

public class Hit {
    private final CombatEntity attacker;
    private final CombatEntity defender;
    private final AttackType type;
    private int damage;
    private final HitType hitType;
    private final int maxDamage;
    private final double chance;
    private final boolean isSpecialAttack;

    // Canonical constructor
    public Hit(CombatEntity attacker, CombatEntity defender, AttackType type, int damage,
               HitType hitType, int maxDamage, double chance, boolean isSpecialAttack) {
        this.attacker = attacker;
        this.defender = defender;
        this.type = type;
        this.damage = damage;
        this.hitType = hitType;
        this.maxDamage = maxDamage;
        this.chance = chance;
        this.isSpecialAttack = isSpecialAttack;
    }

    // Getters (same names as record fields)
    public CombatEntity attacker() {
        return attacker;
    }

    public CombatEntity defender() {
        return defender;
    }

    public AttackType type() {
        return type;
    }

    public int damage() {
        return damage;
    }

    public HitType hitType() {
        return hitType;
    }

    public int maxDamage() {
        return maxDamage;
    }

    public double chance() {
        return chance;
    }

    public void overrideDamage(int damage) {
        this.damage = damage;
    }

    public boolean isSpecialAttack() {
        return isSpecialAttack;
    }
}
