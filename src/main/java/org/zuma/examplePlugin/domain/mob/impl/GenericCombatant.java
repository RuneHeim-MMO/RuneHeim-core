package org.zuma.examplePlugin.domain.mob.impl;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.meta.CombatantMobMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GenericCombatant extends CombatantMob {
    private final boolean attacksOnSight, attackWithGroup;

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
        this.attacksOnSight = attacksOnSight;
        this.attackWithGroup = attackWithGroup;

        HashMap<Attribute, Double> attributes = new HashMap<>();
        attributes.put(Attribute.MOVEMENT_SPEED, walkSpeed);

        super.setAttributes(attributes);
        super.setBehavior(behavior);

        if (equipmentSet != null)
            super.setMobEquipment(equipmentSet.asMobEquipmentMeta());

    }

    @Override
    public Entity spawn(UUID uuid, Location location) {

        Entity spawned = super.spawn(uuid, location);

        if (spawned instanceof LivingEntity living) {
            PotionEffectType effectType = getHostilityEffect(attacksOnSight, attackWithGroup);
            living.addPotionEffect(new PotionEffect(effectType, Integer.MAX_VALUE, 0, false, false, false));
        }

        return spawned;
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

    public static PotionEffectType getHostilityEffect(boolean attacksOnSight, boolean attackWithGroup) {
        if (attacksOnSight) {
            return PotionEffectType.FIRE_RESISTANCE;
        }

        if (attackWithGroup) {
            return PotionEffectType.WATER_BREATHING;
        }

        return PotionEffectType.HASTE;
    }

}
