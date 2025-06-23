package org.zuma.examplePlugin.domain.mob.base;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
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
    private final int maxHealth;
    private int health;

    public CombatantMob(CombatantMobMeta meta) {
        super(new AbstractMobMeta(true, true, Component.empty(), meta.baseEntity()));
        this.health = meta.maxHealth();
        this.maxHealth = meta.maxHealth();

        meta.skills().forEach(record -> skills.put(record.type(), new Skill(record.type(), record.getXP())));
        meta.modifiers().forEach(modifier -> modifiers.put(modifier.type(), modifier));

        this.setDisplayName(Component.text("§6[" + XpUtils.getCombatLevelFromSkills(skills) + " Lvl] §7" + meta.displayName()));

        if (meta.skin() != null) {
            setPlayerDisguise(meta.skin());
        }
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
                livingEntity.damage(1000.0);
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
        this.health -= Math.min(this.health, hit.damage());
    }

}
