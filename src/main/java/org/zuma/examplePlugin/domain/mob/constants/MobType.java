package org.zuma.examplePlugin.domain.mob.constants;

import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.impl.GenericHumanoidCombatant;
import org.zuma.examplePlugin.domain.mob.impl.GenericShopkeeper;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public enum MobType {
    CHESTPLATE_SHOP(new GenericShopkeeper("Chestplate Shop 0", EquipmentSet.SHOPKEEPER_1, MobBehaviors.VILLAGER_MAN_1.getBehavior(), "shopkeeper", 0, false)),
    AGGRESSIVE_BANDIT(new GenericHumanoidCombatant("Bandit", null,
            List.of(new Skill(SkillType.ATTACK, 5000)),
            List.of(new Modifier(ModifierType.ATTACK_SLASH, 500)), MobBehaviors.VILLAGER_MAN_1.getBehavior(), 0.23, false, true, "bandit"));

    private final AbstractMob mob;

    MobType(AbstractMob mob) {
        this.mob = mob;
    }


    public AbstractMob getNewInstance() {
        return mob.copy();
    }
}
