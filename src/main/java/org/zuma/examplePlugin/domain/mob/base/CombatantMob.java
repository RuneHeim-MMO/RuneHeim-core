package org.zuma.examplePlugin.domain.mob.base;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ModeledEntity;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.mob.meta.AbstractMobMeta;
import org.zuma.examplePlugin.domain.mob.meta.CombatantMobMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.EnumMap;

public abstract class CombatantMob extends AbstractMob implements CombatEntity {
    private final EnumMap<ModifierType, Modifier> modifiers = new EnumMap<>(ModifierType.class);
    private final EnumMap<SkillType, Skill> skills = new EnumMap<>(SkillType.class);
    private final int maxHealth, combatLvl;
    private int health;

    public CombatantMob(CombatantMobMeta meta) {
        super(new AbstractMobMeta(true, true, Component.empty(), meta.baseEntity()));
        this.health = meta.maxHealth();
        this.maxHealth = meta.maxHealth();

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

    public int getCombatLvl() {
        return this.combatLvl;
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerAttackEntity(EntityDamageByEntityEvent event, RunePlayer player) {
        player.asBukkitPlayer().sendMessage(getDisplayName() + " HP: " + health);

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
    public void onEntityDamaged(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.CUSTOM) {
            event.setDamage(0);
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
        // Remove iron golem upwards vel on attack.
        if (event.getDamager() instanceof IronGolem) {
            Player player = runePlayer.asBukkitPlayer();

            Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                Vector vel = player.getVelocity();

                if (vel.getY() > 0.3) {
                    double newY = vel.getY() - 0.5;
                    if (newY < 0) newY = 0;
                    player.setVelocity(new Vector(vel.getX(), newY, vel.getZ()));
                }
            });
        }
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

    public void takeHit(Hit hit) {
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

        // Clamp between 0.5 and maxHealth to avoid instant death or invisibility
        scaledHealth = Math.max(0.5, Math.min(maxHealth, scaledHealth));

        // Apply the scaled health to the entity
        living.setHealth(scaledHealth);
    }

}
