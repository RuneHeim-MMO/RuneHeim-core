package org.zuma.examplePlugin.domain.mob.impl;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.MobUtils;
import org.zuma.examplePlugin.domain.dialog.Dialog;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class CityGuardMob extends GenericCombatant {
    private final int initialWalkingDistance;
    private final double restWalkingSpeed, engagedWalkingSpeed;
    private boolean engaged = false;
    private boolean walkingBack = false;

    public CityGuardMob(String displayName, @Nullable String skin, @Nullable String model,
                        @Nullable DisguiseType disguiseType, int walkingDistance, double restWalkingSpeed, double engagedWalkingSpeed) {
        super(displayName, EquipmentSet.RUNE_SET_BATTLEAXE, List.of(
                new SkillLevelRecord(SkillType.ATTACK, 99),
                new SkillLevelRecord(SkillType.HITPOINTS, 99),
                new SkillLevelRecord(SkillType.STRENGTH, 99),
                new SkillLevelRecord(SkillType.DEFENCE, 99)
        ), null, null, restWalkingSpeed, false, false, 9999, skin, model, disguiseType, List.of());

        this.initialWalkingDistance = walkingDistance;
        this.restWalkingSpeed = restWalkingSpeed;
        this.engagedWalkingSpeed = engagedWalkingSpeed;
        setPlayerDisguise("guard_1");
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

    public void engage(LivingEntity target) {
        if (this.engaged) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> SoundType.GUARD_TARGETED_MOB_SOUND.play(target.getLocation()), ThreadLocalRandom.current().nextInt(0, 21));
        Entity thisEntity = this.getEntity();

        if (!(thisEntity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (!(thisEntity instanceof Mob mob)) {
            return;
        }

        mob.setTarget(target);
        setLivingEntitySpeedAttr(engagedWalkingSpeed);
        this.setMaxWalkDistance(1024);
        this.engaged = true;
        SoundType.GUARD_ENGAGE.play(thisEntity.getLocation());
    }

    public void disengage() {
        if (!this.engaged) {
            return;
        }

        Entity thisEntity = this.getEntity();

        if (!(thisEntity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (!(thisEntity instanceof Mob mob)) {
            return;
        }


        this.engaged = false;
        walkingBack = true;
        mob.setTarget(null);

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> SoundType.GUARD_DISENGAGE.play(thisEntity.getLocation()), ThreadLocalRandom.current().nextInt(0, 21));

        if (initialWalkingDistance > 10) {
            handleArrivedAtSpawn();
            return;
        }

        MobUtils.moveToWithCallback(mob, this.getSpawnLocation(), 10 * 20L,
                this::handleArrivedAtSpawn,
                () -> {
                    if (this.getSpawnLocation() == null) {
                        return;
                    }
                    thisEntity.teleport(this.getSpawnLocation());
                    this.handleArrivedAtSpawn();
                });


    }

    private void handleArrivedAtSpawn() {
        walkingBack = false;
        this.setMaxWalkDistance(initialWalkingDistance);
        this.setLivingEntitySpeedAttr(restWalkingSpeed);

        if (this.getEntity() != null) {
            SoundType.GUARD_DISENGAGE_REST.play(this.getEntity().getLocation());
        }


    }

    public void onWitnessAttack(LivingEntity attacker) {
        if (this.engaged || this.walkingBack) {
            return;
        }

        this.engage(attacker);
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

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        Supplier<DialogBuilder> dialogTemplate;

        if (this.engaged) {
            dialogTemplate = () -> DialogBuilder.create("city-guard-dialog")
                    .passiveStep(p -> p.text("I'm busy!"));

            this.playSound(MobBehavior.MobSoundType.NEGATIVE);
        } else {
            dialogTemplate = () -> DialogBuilder.create("city-guard-dialog")
                    .passiveStep(p -> p.randomTextMultiline(List.of(
                            List.of("Halt! You’ve got my attention."),
                            List.of("No funny business, traveler."),
                            List.of("I used to be an adventurer like you...",
                                    "Then I took an arrow in the knee."),
                            List.of("Watch your step around here."),
                            List.of("Stay out of trouble, or else!"),
                            List.of("By the Nine Divines, keep your hands where I can see them.")
                    )));

            this.playSound(MobBehavior.MobSoundType.IDLE);
        }

        Dialog dialog = dialogTemplate.get().build(this.getUUID());
        RuneQuestPlugin.getDialogHandler().startDialog(player, dialog, null);

    }
}
