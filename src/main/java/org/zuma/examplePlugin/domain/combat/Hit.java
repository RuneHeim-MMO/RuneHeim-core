package org.zuma.examplePlugin.domain.combat;

import org.zuma.examplePlugin.core.interfaces.CombatEntity;

public record Hit(CombatEntity attacker, CombatEntity defender, AttackType type, int damage, HitType hitType) {


}
