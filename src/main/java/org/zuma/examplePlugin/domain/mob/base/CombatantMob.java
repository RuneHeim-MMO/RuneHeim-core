package org.zuma.examplePlugin.domain.mob.base;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ModeledEntity;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.xp.PendingXPHandler;
import org.zuma.examplePlugin.domain.mob.meta.AbstractMobMeta;
import org.zuma.examplePlugin.domain.mob.meta.CombatantMobMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.*;

public abstract class CombatantMob extends AbstractMob implements CombatEntity {
    private final EnumMap<ModifierType, Modifier> modifiers = new EnumMap<>(ModifierType.class);
    private final EnumMap<SkillType, Skill> skills = new EnumMap<>(SkillType.class);
    private final int maxHealth, combatLvl;
    private int health;

    private final PendingXPHandler xpHandler;


    public CombatantMob(CombatantMobMeta meta) {
        super(new AbstractMobMeta(true, true, Component.empty(), meta.baseEntity()));
        this.health = meta.maxHealth();
        this.maxHealth = meta.maxHealth();
        this.xpHandler = new PendingXPHandler();

        setModel(meta.model());
        setDropTableIds(meta.dropTableIds());
        meta.skills().forEach(record -> skills.put(record.type(), new Skill(record.type(), record.getXP())));

        if (meta.modifiers() != null)
            meta.modifiers().forEach(modifier -> modifiers.put(modifier.type(), modifier));

        combatLvl = XpUtils.getCombatLevelFromSkills(skills);
        String levelColorCode = TextUtils.getLegacyColorCodeForLevel(combatLvl);

        String displayName = levelColorCode + "[" + combatLvl + " Lvl] §7" + meta.displayName();
        this.setDisplayName(Component.text(displayName));


        if (meta.skin() != null && meta.disguiseType() == null) {
            setPlayerDisguise(meta.skin());
        }

        if (meta.disguiseType() != null) {
            setDisguise(meta.disguiseType());
        }
    }


    public void setBaseEntity(EntityType type) {
        super.setBaseEntity(type);
    }

    public int getCombatLvl() {
        return this.combatLvl;
    }

    public EnumMap<ModifierType, Modifier> getModifiers() {
        return modifiers;
    }

    public EnumMap<SkillType, Skill> getSkills() {
        return skills;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void registerHit(Hit hit) {
        if (!(getEntity() instanceof LivingEntity living)) {
            return;
        }

        // Get max Minecraft health for this entity
        AttributeInstance attr = living.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double maxHealth = attr != null ? attr.getValue() : 20.0; // fallback

        if (maxHealth <= 0) maxHealth = 1.0;

        // Apply the hit to the custom health system
        this.health = Math.max(0, this.health - hit.damage());

        // Scale custom health to the entity's real health bar
        double scaledHealth = maxHealth * ((double) this.health / this.maxHealth);

        scaledHealth = Math.max(0, Math.min(maxHealth, scaledHealth));

        // Set killer metadata on last hit
        if (scaledHealth == 0 && hit.attacker().getEntity() instanceof Player playerAttacker) {
            living.setMetadata("killer", new FixedMetadataValue(RuneQuestPlugin.getInstance(), playerAttacker.getUniqueId().toString()));
        }

        // Apply the scaled health to the entity
        this.xpHandler.calculatePendingXP(hit);
        living.setHealth(scaledHealth);
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        event.setCancelled(true);
    }

    @Override
    public void onEntityDamaged(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.CUSTOM) {
            event.setDamage(0);
        }

        Entity entity = this.getEntity();

        if (entity == null) {
            return;
        }

        if (health == 0) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.setHealth(0.0);
            } else {
                entity.remove();
            }
        }
    }

    @Override
    public void onEntityAttackEntity(EntityDamageByEntityEvent event, Entity attacker) {
        // Remove iron golem upwards vel on attack.
        if (event.getDamager() instanceof IronGolem) {
            Entity victim = event.getEntity();

            Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                Vector vel = victim.getVelocity();

                if (vel.getY() > 0.3) {
                    double newY = vel.getY() - 0.5;
                    if (newY < 0) newY = 0;
                    victim.setVelocity(new Vector(vel.getX(), newY, vel.getZ()));
                }
            });
        }
    }

    @Override
    public void onEntityAttackPlayer(EntityDamageByEntityEvent event, RunePlayer runePlayer) {
        Entity hit = getEntity();
        if (hit != null) {
            ModeledEntity me = ModelEngineAPI.getModeledEntity(hit);

            if (me != null) {
                me.getModels().values().forEach(am -> {
                    am.getAnimationHandler().playAnimation("attack", 0f, 0.3f, 1, true);
                });
            }

        }

    }

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        this.xpHandler.distributePendingXP();
    }

}
