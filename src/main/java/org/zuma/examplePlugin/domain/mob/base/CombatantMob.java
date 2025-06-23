package org.zuma.examplePlugin.domain.mob.base;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
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
    private int health = 10;

    public CombatantMob(CombatantMobMeta meta) {
        super(new AbstractMobMeta(true, true, Component.text(meta.displayName()), meta.baseEntity()));

        meta.skills().forEach(record -> skills.put(record.type(), new Skill(record.type(), record.getXP())));
        meta.modifiers().forEach(modifier -> modifiers.put(modifier.type(), modifier));

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
        health -= 1;
        player.getPlayer().sendMessage("You hit this entity! HP: " + health);

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
    public void onEntityAttackPlayer(EntityDamageByEntityEvent event, RunePlayer player) {
        player.getPlayer().sendMessage("Entity hit you!");
    }

    @Override
    public void onEntityDamaged(EntityDamageEvent event) {
        event.getEntity().setFireTicks(0);

        if (event.getCause() != EntityDamageEvent.DamageCause.CUSTOM) {
            event.setDamage(0);
        }


    }

    @Override
    public EnumMap<ModifierType, Modifier> getModifiers() {
        return modifiers;
    }

    @Override
    public EnumMap<SkillType, Skill> getSkills() {
        return skills;
    }

    @Override
    public int getHealth() {
        return health;
    }
}
