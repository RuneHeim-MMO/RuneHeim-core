package org.zuma.examplePlugin.domain.mob.impl;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.meta.CombatantMobMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class GenericHumanoidCombatant extends CombatantMob {
    String displayName;
    EquipmentSet equipmentSet;
    MobBehavior behavior;
    String skin;
    List<SkillLevelRecord> skills;
    List<Modifier> modifiers;
    double walkSpeed;
    boolean attacksOnSight, attacksWithFriends;

    private static EntityType getMobWithCorrectBehavior(boolean attacksOnSight, boolean attacksWithFriends) {
        if (attacksOnSight && attacksWithFriends) {
            // Mobs that attack on sight and attack with friends (usually melee Illagers & zombies)
            return EntityType.VINDICATOR; // Could also be RAVAGER, ZOMBIFIED_PIGLIN, or ZOMBIE
        }
        if (attacksOnSight && !attacksWithFriends) {
            // Mobs that attack on sight but act alone (could be melee or ranged)
            return EntityType.ZOMBIE; // Common solo attacker (ranged)
        }

        if (!attacksOnSight && attacksWithFriends) {
            return EntityType.ZOMBIFIED_PIGLIN;
        }
        // No matching mob
        return null;
    }

    public GenericHumanoidCombatant(String displayName, @Nullable EquipmentSet equipmentSet, List<SkillLevelRecord> skills, List<Modifier> modifiers, @Nullable MobBehavior behavior, double walkSpeed,
                                    boolean attacksOnSight, boolean attacksWithFriends,
                                    String skin) {
        super(new CombatantMobMeta(displayName, equipmentSet, skills, modifiers, skin, getMobWithCorrectBehavior(attacksOnSight, attacksWithFriends)));

        this.displayName = displayName;
        this.equipmentSet = equipmentSet;
        this.skills = skills;
        this.modifiers = modifiers;
        this.skin = skin;
        this.walkSpeed = walkSpeed;
        this.behavior = behavior;
        this.attacksOnSight = attacksOnSight;
        this.attacksWithFriends = attacksWithFriends;

        HashMap<Attribute, Double> attributes = new HashMap<>();
        attributes.put(Attribute.GENERIC_MOVEMENT_SPEED, walkSpeed);

        super.setAttributes(attributes);
        super.setBehavior(behavior);

        if (equipmentSet != null)
            super.setMobEquipment(equipmentSet.asMobEquipmentMeta());

    }


    @Override
    public AbstractMob copy() {
        return new GenericHumanoidCombatant(displayName, equipmentSet, skills, modifiers, behavior, walkSpeed, attacksOnSight, attacksWithFriends, skin);
    }


}
