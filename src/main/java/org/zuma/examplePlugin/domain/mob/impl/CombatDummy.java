package org.zuma.examplePlugin.domain.mob.impl;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ModeledEntity;
import io.papermc.paper.event.entity.EntityKnockbackEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.combat.constants.HitType;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class CombatDummy extends GenericCombatant {
    public CombatDummy(int yawDegrees) {
        super("Combat Dummy", null, List.of(
                new SkillLevelRecord(SkillType.ATTACK, 1),
                new SkillLevelRecord(SkillType.HITPOINTS, 1),
                new SkillLevelRecord(SkillType.STRENGTH, 1),
                new SkillLevelRecord(SkillType.DEFENCE, 1)
        ), null, null, 0, false, false, 99999, null, "dummy", null, List.of(), true);

        setMaxWalkDistance(0);
        setBaseEntity(EntityType.PIG); // Pig hides health bar
        setYaw(yawDegrees);
        setSilent(true);

        // Turn AI on for a bit so the gravity works.
        setSpawnRunnable((entity) -> {
            setAI(true);
            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                if (entity instanceof LivingEntity livingEntity) livingEntity.setAI(false);

                // Find the solid ground below current position (for indoor placement)
                Location currentLoc = entity.getLocation();
                Location groundLoc = currentLoc.clone();
                // Scan downwards to find solid ground, max 5 blocks down
                for (int y = currentLoc.getBlockY(); y >= currentLoc.getBlockY() - 5; y--) {
                    groundLoc.setY(y);
                    if (groundLoc.getBlock().getType().isSolid() &&
                            groundLoc.clone().add(0, 1, 0).getBlock().getType().isAir()) {
                        groundLoc.setY(y + 1.0); // Place on top of solid block
                        break;
                    }
                }

                groundLoc.setYaw(getYaw());
                groundLoc.setPitch(currentLoc.getPitch());

                entity.teleport(groundLoc);
            }, 5);
            return null;
        });

    }

    @Override
    public void onEntityKnockback(EntityKnockbackEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void registerHit(Hit hit) {
        hit.overrideDamage(hit.getMaxDamage());

        Entity thisEntity = getEntity();
        if (thisEntity == null) {
            return;
        }

        playHitSound(thisEntity);
        ModeledEntity me = ModelEngineAPI.getModeledEntity(thisEntity);

        if (me != null) {
            me.getModels().values().forEach(am -> {
                am.getAnimationHandler().playAnimation("hit", 0f, 0.3f, 1, true);
            });

            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                CombatEntity attacker = hit.getAttacker();

                if (attacker.getEntity() == null) return;
                Location dummyLocation = thisEntity.getLocation();
                Location attackerLocation = attacker.getEntity().getLocation();

                if (MathUtils.getLocationsDistanceXZ(dummyLocation, attackerLocation) < 1) {
                    Hit combatDummyHit = new Hit(this, attacker, AttackType.MELEE, 1, HitType.REGULAR, 1, 100, false, true);

                    attacker.registerHit(combatDummyHit);
                    HudService.showDamageBubble(attacker, combatDummyHit);
                    SoundType.HIT_MELEE_REGULAR.play(attackerLocation);
                    attacker.getEntity().setVelocity(attacker.getEntity().getVelocity().add(new Vector(0, 0.2, 0)));
                    AnalyticsUtils.incrementServerAndPlayer((RunePlayer) attacker, AnalyticsKey.COMBAT_DUMMY_HITS, 1);
                }

            }, 6);
        }

    }

    private void playHitSound(Entity entity) {
        double min = -0.3;
        double max = 0.1;
        float randomPitch = (float) (min + Math.random() * (max - min));

        SoundType.COMBAT_DUMMY_HURT.play(entity.getLocation(), randomPitch);
        SoundType.COMBAT_DUMMY_IMPACT.play(entity.getLocation(), randomPitch);

    }


}
