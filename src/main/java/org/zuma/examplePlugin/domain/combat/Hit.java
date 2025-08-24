package org.zuma.examplePlugin.domain.combat;

import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.combat.constants.HitType;

public record Hit(CombatEntity attacker, CombatEntity defender, AttackType type, int damage, HitType hitType,
                  int maxDamage, double chance) {


}
