package org.zuma.examplePlugin.domain.combat;

import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.combat.constants.HitType;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.Map;

public class HitCalculator {
    private static final double BASE_BONUS = 128.0;
    private static final double MAX_HIT_DIVISOR = 640.0;
    private static final double MAX_HIT_FLOOR_OFFSET = 0.5;
    private static final double HIT_CHANCE_OFFSET = 2.0;

    public static double getEntitySkillLevel(CombatEntity entity, SkillType type) {
        Skill matchingSkill = entity.getSkills().get(type);

        if (matchingSkill == null) {
            return 0;
        }

        double level = matchingSkill.getLevelWithBonuses();

        switch (type) {
            case ATTACK -> {
                level *= 1 + (getEntityModifierAmount(entity, ModifierType.ATTACK_BOOST_PERCENT) / 100.0);
            }
            case STRENGTH -> {
                level *= 1 + (getEntityModifierAmount(entity, ModifierType.STRENGTH_BOOST_PERCENT) / 100.0);
            }
            case DEFENCE -> {
                level *= 1 + (getEntityModifierAmount(entity, ModifierType.DEFENCE_BOOST_PERCENT) / 100.0);
            }
            case RANGED -> {
                level *= 1 + (getEntityModifierAmount(entity, ModifierType.RANGED_BOOST_PERCENT) / 100.0);
            }
        }

        return level;
    }

    public static double getEntityModifierAmount(CombatEntity entity, ModifierType type) {
        Modifier matchingModifier = entity.getModifiers().get(type);

        if (matchingModifier == null) {
            return 0;
        }

        return matchingModifier.amount();
    }

    public static boolean getIsCriticalHit(CombatEntity entity, int damage) {
        int percentMaxHpHitCritical = 10;
        return damage > (entity.getHealth() * percentMaxHpHitCritical / 100.0);
    }

    public static HitType getHitType(CombatEntity entity, int damage) {
        HitType hitType = HitType.REGULAR;

        if (damage == 0) {
            hitType = HitType.MISS;
        }

        if (getIsCriticalHit(entity, damage)) {
            hitType = HitType.CRIT;
        }

        return hitType;
    }

    public static Hit getNewHit(CombatEntity attacker, CombatEntity defender, AttackType type, boolean isSpecialAttack, boolean isSplashHit) {
        Map.Entry<Boolean, Double> hitRollResult = isHitSuccess(attacker, defender, type, isSpecialAttack);
        boolean hitSuccess = hitRollResult.getKey();
        double hitChance = hitRollResult.getValue();
        double maxHit = getMaxHit(attacker, type);

        if (isSpecialAttack) {
            maxHit *= 1.15;
        }

        if (isSplashHit) {
            maxHit *= 0.5;
        }

        if (hitSuccess) {
            int baseDamage = (int) (Math.random() * (maxHit + 1));
            int damage = Math.min(defender.getHealth(), baseDamage);

            return new Hit(attacker, defender, type, damage, getHitType(defender, damage), (int) maxHit, hitChance, isSpecialAttack, isSplashHit);
        } else if (Math.random() < 0.25) {
            // Glancing blow
            int baseDamage = (int) (Math.random() * (maxHit / 3.0));
            int damage = Math.min(defender.getHealth(), baseDamage);

            if (damage == 0 && maxHit > 3) damage = 1; // Ensure at least 1 damage on glancing blow

            return new Hit(attacker, defender, type, damage, getHitType(defender, damage), (int) maxHit, hitChance, isSpecialAttack, isSplashHit);
        }


        int damage = Math.min(defender.getHealth(), 0);
        return new Hit(attacker, defender, type, damage, getHitType(defender, damage), (int) maxHit, hitChance, isSpecialAttack, isSplashHit);

    }

    public static int getMaxHit(CombatEntity attacker, AttackType type) {
        double attackerStrBonus = getEntityModifierAmount(attacker, ModifierType.STRENGTH_BONUS);
        double attackerRangeStr = getEntityModifierAmount(attacker, ModifierType.RANGED_STRENGTH);
        double attackerMaxHitPenaltyPercent = Math.abs(getEntityModifierAmount(attacker, ModifierType.MAX_HIT_PENALTY));

        attackerRangeStr *= 1 + (getEntityModifierAmount(attacker, ModifierType.RANGED_STR_BOOST_PERCENT) / 100.0);

        if (type == AttackType.MELEE) {
            double attackerStrength = getEntitySkillLevel(attacker, SkillType.STRENGTH);

            // Melee max hit = floor(0.5 + StrengthLevel * (StrengthBonus + 64) / 640)
            double maxHit = (MAX_HIT_FLOOR_OFFSET + attackerStrength * (attackerStrBonus + BASE_BONUS) / MAX_HIT_DIVISOR) * (1 - attackerMaxHitPenaltyPercent / 100.0);

            return Math.max(2, (int) Math.ceil(maxHit));
        }

        if (type == AttackType.RANGED) {
            double attackerRanged = getEntitySkillLevel(attacker, SkillType.RANGED);
            double maxHit = (MAX_HIT_FLOOR_OFFSET + attackerRanged * (attackerRangeStr + BASE_BONUS) / MAX_HIT_DIVISOR) * (1 - attackerMaxHitPenaltyPercent / 100.0);

            return Math.max(2, (int) Math.ceil(maxHit));
        }

        if (type == AttackType.MAGIC) {
            // Not implemented, return 0
            return 0;
        }

        return 0;
    }

    // Returns Entry<isHitSuccess, chance>
    public static Map.Entry<Boolean, Double> isHitSuccess(CombatEntity attacker, CombatEntity defender, AttackType type, boolean isSpecialAttack) {
        if (type == AttackType.MELEE) {
            double attackerAttack = getEntitySkillLevel(attacker, SkillType.ATTACK);
            double defenderDefence = getEntitySkillLevel(defender, SkillType.DEFENCE);

            double atkBonus = getCombinedMeleeAttackBonus(attacker);
            double defBonus = getCombinedDefenceBonus(defender);

            double attackRoll = attackerAttack * (atkBonus + BASE_BONUS);
            double defenceRoll = defenderDefence * (defBonus + BASE_BONUS);

            return rollHitChance(attackRoll, defenceRoll, isSpecialAttack);
        }

        if (type == AttackType.RANGED) {
            double attackerRanged = getEntitySkillLevel(attacker, SkillType.RANGED);
            double defenderDefence = getEntitySkillLevel(defender, SkillType.DEFENCE);

            double meleeDefBonus = getCombinedDefenceBonus(defender);
            double defenderRangeDef = getEntityModifierAmount(defender, ModifierType.DEFENCE_RANGED);
            double attackerRangeAcc = getEntityModifierAmount(attacker, ModifierType.RANGED_ACCURACY);

            attackerRangeAcc *= 1 + (getEntityModifierAmount(attacker, ModifierType.RANGED_ACC_BOOST_PERCENT) / 100.0);

            double defBonus = 0.7 * defenderRangeDef + 0.3 * meleeDefBonus;

            double attackRoll = attackerRanged * (attackerRangeAcc + BASE_BONUS);
            double defenceRoll = defenderDefence * (defBonus + BASE_BONUS);

            return rollHitChance(attackRoll, defenceRoll, isSpecialAttack);
        }

        if (type == AttackType.MAGIC) {
            // TODO: implement magic
            return Map.entry(false, 0.0);
        }

        return Map.entry(false, 0.0);
    }

    private static Map.Entry<Boolean, Double> rollHitChance(double attackRoll, double defenceRoll, boolean isSpecialAttack) {
        double chanceToHit;

        if (attackRoll > defenceRoll) {
            chanceToHit = 1 - (defenceRoll + HIT_CHANCE_OFFSET) / (2 * (attackRoll + 1));
        } else {
            chanceToHit = attackRoll / (2 * (defenceRoll + 1));
        }

        if (isSpecialAttack) {
            chanceToHit *= 1.15;
        }

        return Map.entry(Math.random() < chanceToHit, chanceToHit);
    }


    private static double getCombinedMeleeAttackBonus(CombatEntity entity) {
        double stab = getEntityModifierAmount(entity, ModifierType.ATTACK_STAB);
        double slash = getEntityModifierAmount(entity, ModifierType.ATTACK_SLASH);
        double crush = getEntityModifierAmount(entity, ModifierType.ATTACK_CRUSH);

        return ((stab + slash + crush) / 3.0) * 1.35;
    }

    private static double getCombinedDefenceBonus(CombatEntity entity) {
        double stab = getEntityModifierAmount(entity, ModifierType.DEFENCE_STAB);
        double slash = getEntityModifierAmount(entity, ModifierType.DEFENCE_SLASH);
        double crush = getEntityModifierAmount(entity, ModifierType.DEFENCE_CRUSH);

        return (stab + slash + crush) / 3.0;
    }

}
