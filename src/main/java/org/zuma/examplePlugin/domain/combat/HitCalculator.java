package org.zuma.examplePlugin.domain.combat;

import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

public class HitCalculator {
    private static final double BASE_BONUS = 64.0;
    private static final double MAX_HIT_DIVISOR = 640.0;
    private static final double MAX_HIT_FLOOR_OFFSET = 0.5;
    private static final double HIT_CHANCE_OFFSET = 2.0;

    private static int getEntitySkillLevel(CombatEntity entity, SkillType type) {
        Skill matchingSkill = entity.getSkills().get(type);

        if (matchingSkill == null) {
            return 0;
        }

        return matchingSkill.getLevel();
    }

    private static double getEntityModifierLevel(CombatEntity entity, ModifierType type) {
        Modifier matchingModifier = entity.getModifiers().get(type);

        if (matchingModifier == null) {
            return 0;
        }

        return matchingModifier.amount();
    }

    public static Hit getHitDamage(CombatEntity attacker, CombatEntity defender, AttackType type) {
        boolean hitSuccess = isHitSuccess(attacker, defender, type);

        if (hitSuccess) {
            int maxHit = getMaxHit(attacker, type);
            int baseDamage = (int) (Math.random() * (maxHit + 1));

            return new Hit(attacker, defender, type, Math.min(defender.getHealth(), baseDamage));
        }

        return new Hit(attacker, defender, type, Math.min(defender.getHealth(), 0));

    }

    public static int getMaxHit(CombatEntity attacker, AttackType type) {
        double attackerStrBonus = getEntityModifierLevel(attacker, ModifierType.STRENGTH_BONUS);
        double attackerRangeStr = getEntityModifierLevel(attacker, ModifierType.RANGED_STRENGTH);

        if (type == AttackType.MELEE) {
            int attackerStrength = getEntitySkillLevel(attacker, SkillType.STRENGTH);

            // Melee max hit = floor(0.5 + StrengthLevel * (StrengthBonus + 64) / 640)
            double maxHit = MAX_HIT_FLOOR_OFFSET + attackerStrength * (attackerStrBonus + BASE_BONUS) / MAX_HIT_DIVISOR;

            return (int) Math.floor(maxHit);
        }

        if (type == AttackType.RANGED) {
            int attackerRanged = getEntitySkillLevel(attacker, SkillType.RANGED);

            // Ranged max hit = floor(RangedLevel * (RangedStrength + 64) / 640)
            double maxHit = attackerRanged * (attackerRangeStr + BASE_BONUS) / MAX_HIT_DIVISOR;

            return (int) Math.floor(maxHit);
        }

        if (type == AttackType.MAGIC) {
            // Not implemented, return 0
            return 0;
        }

        return 0;
    }

    public static boolean isHitSuccess(CombatEntity attacker, CombatEntity defender, AttackType type) {

        if (type == AttackType.MELEE) {
            int attackerAttack = getEntitySkillLevel(attacker, SkillType.ATTACK);
            int defenderDefence = getEntitySkillLevel(defender, SkillType.DEFENCE);

            double atkBonus = getCombinedMeleeAttackBonus(attacker);
            double defBonus = getCombinedMeleeDefenceBonus(defender);

            double attackRoll = attackerAttack * (atkBonus + BASE_BONUS);
            double defenceRoll = defenderDefence * (defBonus + BASE_BONUS);

            return rollHitChance(attackRoll, defenceRoll);
        }

        if (type == AttackType.RANGED) {
            int attackerRanged = getEntitySkillLevel(attacker, SkillType.RANGED);
            int defenderDefence = getEntitySkillLevel(defender, SkillType.DEFENCE);

            double attackerRangeStr = getEntityModifierLevel(attacker, ModifierType.RANGED_STRENGTH);

            double attackRoll = attackerRanged * (attackerRangeStr + BASE_BONUS);
            double defenceRoll = defenderDefence * BASE_BONUS;

            return rollHitChance(attackRoll, defenceRoll);
        }

        if (type == AttackType.MAGIC) {
            // TODO: implement magic
            return false;
        }

        return false;
    }

    private static boolean rollHitChance(double attackRoll, double defenceRoll) {
        double chanceToHit;

        if (attackRoll > defenceRoll) {
            chanceToHit = 1 - (defenceRoll + HIT_CHANCE_OFFSET) / (2 * (attackRoll + 1));
        } else {
            chanceToHit = attackRoll / (2 * (defenceRoll + 1));
        }

        return Math.random() < chanceToHit;
    }


    private static double getCombinedMeleeAttackBonus(CombatEntity entity) {
        double stab = getEntityModifierLevel(entity, ModifierType.ATTACK_STAB);
        double slash = getEntityModifierLevel(entity, ModifierType.ATTACK_SLASH);
        double crush = getEntityModifierLevel(entity, ModifierType.ATTACK_CRUSH);

        return (stab + slash + crush) / 3.0;
    }

    private static double getCombinedMeleeDefenceBonus(CombatEntity entity) {
        double stab = getEntityModifierLevel(entity, ModifierType.DEFENCE_STAB);
        double slash = getEntityModifierLevel(entity, ModifierType.DEFENCE_SLASH);
        double crush = getEntityModifierLevel(entity, ModifierType.DEFENCE_CRUSH);

        return (stab + slash + crush) / 3.0;
    }

}
