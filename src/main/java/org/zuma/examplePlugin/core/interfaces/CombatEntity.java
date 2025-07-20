package org.zuma.examplePlugin.core.interfaces;

import org.bukkit.entity.Entity;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.EnumMap;

public interface CombatEntity {
    EnumMap<ModifierType, Modifier> getModifiers();

    EnumMap<SkillType, Skill> getSkills();

    void takeHit(Hit hit);

    int getHealth();

    int getMaxHealth();

    Entity getEntity();

}
