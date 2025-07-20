package org.zuma.examplePlugin.domain.combat;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.combat.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.combat.constants.ProjectileType;
import org.zuma.examplePlugin.domain.mob.MobRegistry;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

import javax.annotation.Nullable;

public class HitListener implements Listener {
    public static final NamespacedKey projectileType = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistenceDataKey.PROJECTILE_TYPE.asString());
    private final RunePlayerManager playerManager;
    private final MobRegistry mobRegistry;
    private final HitHandler hitHandler;

    public HitListener(HitHandler hitHandler, RunePlayerManager playerManager, MobRegistry mobRegistry) {
        this.playerManager = playerManager;
        this.mobRegistry = mobRegistry;
        this.hitHandler = hitHandler;
    }

    @Nullable
    private CombatEntity getCombatEntity(Entity entity) {
        if (entity instanceof Player player) {
            return playerManager.getRunePlayer(player);
        }

        AbstractMob matchingMob = mobRegistry.getMob(entity);
        if (matchingMob instanceof CombatEntity combatMob) {
            return combatMob;
        }

        return null;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityAttackEntityMelee(EntityDamageByEntityEvent event) {
        event.setDamage(0); // Cancel real damage

        if (!(event.getEntity() instanceof LivingEntity defenderEntity)) return;

        CombatEntity attacker = getCombatEntity(event.getDamager());
        CombatEntity defender = getCombatEntity(defenderEntity);

        if (attacker != null && defender != null) {
            hitHandler.handleMeleeHit(attacker, defender);
        }

    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityAttackEntityProjectile(EntityDamageByEntityEvent event) {
        event.setDamage(0); // Cancel real damage

        if (!(event.getEntity() instanceof LivingEntity defenderEntity)) {
            return;
        }

        if (!(event.getDamager() instanceof Projectile projectile)) {
            return;
        }

        ProjectileSource shooter = projectile.getShooter();

        if (!(shooter instanceof LivingEntity livingShooter)) {
            return;
        }

        CombatEntity attacker = getCombatEntity(livingShooter);
        CombatEntity defender = getCombatEntity(defenderEntity);

        if (attacker != null && defender != null) {
            String typeString = projectile.getPersistentDataContainer().get(projectileType, PersistentDataType.STRING);
            if (typeString == null) return; // Unknown projectile

            ProjectileType type = ProjectileType.valueOf(typeString);

            switch (type) {
                case RANGED -> hitHandler.handleRangedHit(attacker, defender);
                case MAGIC -> hitHandler.handleMagicHit(attacker, defender);
            }

        }

    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        ProjectileSource source = projectile.getShooter();

        if (!(source instanceof LivingEntity shooter)) {
            return;
        }

        CombatEntity attacker = getCombatEntity(shooter);

        if (attacker == null || attacker.getEntity() == null) {
            return;
        }

        if (projectile instanceof Arrow arrow) {
            projectile.getPersistentDataContainer().set(projectileType, PersistentDataType.STRING, ProjectileType.RANGED.toString());
            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            arrow.setCritical(false);
            arrow.setDamage(0);
            return;
        }

        projectile.getPersistentDataContainer().set(projectileType, PersistentDataType.STRING, ProjectileType.MAGIC.toString());


    }
}
