package org.zuma.examplePlugin.domain.mob.impl;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ModeledEntity;
import io.papermc.paper.event.entity.EntityKnockbackEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class CombatDummy extends GenericCombatant {
    public CombatDummy() {
        super("Combat Dummy", null, List.of(
                new SkillLevelRecord(SkillType.ATTACK, 1),
                new SkillLevelRecord(SkillType.HITPOINTS, 1),
                new SkillLevelRecord(SkillType.STRENGTH, 1),
                new SkillLevelRecord(SkillType.DEFENCE, 1)
        ), null, null, 0, false, false, 99999, null, "dummy", null, List.of());

        setMaxWalkDistance(0);
        setBaseEntity(EntityType.PIG); // Pig hides health bar
        setSilent(true);

        // Turn AI on for a bit so the gravity works.
        setSpawnRunnable((entity) -> {
            setAI(true);
            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                if (entity instanceof LivingEntity livingEntity) livingEntity.setAI(false);
            }, 10);
            return null;
        });

    }

    @Override
    public void onEntityKnockback(EntityKnockbackEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void registerHit(Hit hit) {
        hit.overrideDamage(hit.maxDamage());

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
