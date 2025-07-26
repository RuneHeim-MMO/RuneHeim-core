package org.zuma.examplePlugin.domain.mob.impl;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.MobUtils;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.meta.CombatantMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class CityGuardMob extends GenericCombatant {
    private final int initialWalkingDistance;
    private final double restWalkingSpeed, engagedWalkingSpeed;
    private Location beforeEngagementLocation;
    private boolean engaged = false;

    public CityGuardMob(String displayName, @Nullable String skin, @Nullable String model,
                        @Nullable DisguiseType disguiseType, int walkingDistance, double restWalkingSpeed, double engagedWalkingSpeed) {
        super(displayName, EquipmentSet.RUNE_SET_BATTLEAXE, getBaseSkills(99), null, null, restWalkingSpeed, false, false, 9999, skin, model, disguiseType, List.of());

        this.initialWalkingDistance = walkingDistance;
        this.restWalkingSpeed = restWalkingSpeed;
        this.engagedWalkingSpeed = engagedWalkingSpeed;
        setPlayerDisguise("Notch");
        setMaxWalkDistance(walkingDistance);
        setBaseEntity(EntityType.IRON_GOLEM);
        setSilent(false);
        disengage();
    }

    @Override
    public void onPlayerAttackEntity(EntityDamageByEntityEvent event, RunePlayer player) {
        event.setCancelled(true);
    }

    @Override
    public void onEntityAttackEntity(EntityDamageByEntityEvent event, Entity attacker) {
        event.setCancelled(true);
    }

    private void setLivingEntitySpeedAttr(LivingEntity entity, double speed) {
        AttributeInstance entityAttr = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);

        if (entityAttr == null) {
            return;
        }

        entityAttr.setBaseValue(speed);
    }

    public void engage(LivingEntity target) {
        Entity thisEntity = this.getEntity();

        if (!(thisEntity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (!(thisEntity instanceof Mob mob)) {
            return;
        }

        mob.setTarget(target);
        setLivingEntitySpeedAttr(livingEntity, engagedWalkingSpeed);
        this.beforeEngagementLocation = thisEntity.getLocation();
        this.setMaxWalkDistance(1024);
        this.engaged = true;
    }

    public void disengage() {
        Entity thisEntity = this.getEntity();

        if (!(thisEntity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (!(thisEntity instanceof Mob mob)) {
            return;
        }


        this.engaged = false;

        if (beforeEngagementLocation != null) {
            mob.getPathfinder().moveTo(beforeEngagementLocation);
            MobUtils.moveToWithCallback(mob, beforeEngagementLocation, 5 * 20, () -> handleEndEngagement(livingEntity), () -> handleEndEngagement(livingEntity));
            this.beforeEngagementLocation = null;

        }
    }

    private void handleEndEngagement(LivingEntity entity) {
        setLivingEntitySpeedAttr(entity, restWalkingSpeed);
        setMaxWalkDistance(initialWalkingDistance);
    }

    public int getInitialWalkingDistance() {
        return initialWalkingDistance;
    }

    public double getEngagedWalkingSpeed() {
        return engagedWalkingSpeed;
    }

    public double getRestWalkingSpeed() {
        return restWalkingSpeed;
    }

    public boolean isEngaged() {
        return engaged;
    }
}
