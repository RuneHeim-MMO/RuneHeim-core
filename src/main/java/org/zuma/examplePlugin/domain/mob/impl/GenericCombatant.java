package org.zuma.examplePlugin.domain.mob.impl;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.meta.CombatantMobMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class GenericCombatant extends CombatantMob {

    public GenericCombatant(String displayName,
                            @Nullable EquipmentSet equipmentSet,
                            List<SkillLevelRecord> skills,
                            List<Modifier> modifiers,
                            @Nullable MobBehavior behavior,
                            @Nullable String model,
                            double walkSpeed,
                            boolean attacksOnSight,
                            boolean attackWithGroup,
                            int health,
                            List<Integer> dropTableIds, boolean safeSpottable) {
        this(displayName, equipmentSet, skills, modifiers, behavior, walkSpeed, attacksOnSight, attackWithGroup, health, null, model, null, dropTableIds, safeSpottable);
    }


    public GenericCombatant(String displayName,
                            @Nullable EquipmentSet equipmentSet,
                            List<SkillLevelRecord> skills,
                            List<Modifier> modifiers,
                            @Nullable MobBehavior behavior,
                            double walkSpeed,
                            boolean attacksOnSight,
                            boolean attackWithGroup,
                            int health,
                            @Nullable String skin,
                            List<Integer> dropTableIds, boolean safeSpottable) {
        this(displayName, equipmentSet, skills, modifiers, behavior, walkSpeed, attacksOnSight, attackWithGroup, health, skin, null, null, dropTableIds, safeSpottable);
    }

    public GenericCombatant(String displayName,
                            @Nullable EquipmentSet equipmentSet,
                            List<SkillLevelRecord> skills,
                            List<Modifier> modifiers,
                            @Nullable MobBehavior behavior,
                            double walkSpeed,
                            boolean attacksOnSight, boolean attackWithGroup, int health,
                            @Nullable String skin,
                            @Nullable String model,
                            @Nullable DisguiseType disguiseType,
                            List<Integer> dropTableIds, boolean safeSpottable) {
        super(new CombatantMobMeta(displayName, equipmentSet, skills, modifiers, skin, model, disguiseType, dropTableIds, safeSpottable, health, getMobWithCorrectBehavior(attacksOnSight, attackWithGroup)));

        HashMap<Attribute, Double> attributes = new HashMap<>();
        attributes.put(Attribute.MOVEMENT_SPEED, walkSpeed);

        super.setAttributes(attributes);
        super.setBehavior(behavior);

        if (equipmentSet != null)
            super.setMobEquipment(equipmentSet.asMobEquipmentMeta());

    }

    private static EntityType getMobWithCorrectBehavior(boolean attacksOnSight, boolean attackWithGroup) {
        if (attacksOnSight) {
            return EntityType.ZOMBIE;
        }

        if (attackWithGroup) {
            return EntityType.ZOMBIFIED_PIGLIN;
        }

        return EntityType.POLAR_BEAR;
    }

}
