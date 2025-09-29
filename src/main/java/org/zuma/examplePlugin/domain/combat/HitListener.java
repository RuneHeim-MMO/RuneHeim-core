package org.zuma.examplePlugin.domain.combat;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
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
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.territory.Territory;

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

    public static boolean isPlayerInPVPDisabledRegion(Player player) {
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
        if (runePlayer == null) return true;

        LocalPlayer localAttacker = WorldGuardPlugin.inst().wrapPlayer(player);
        Location loc = player.getLocation();

        boolean WGPVPDisabled = !WorldGuard.getInstance()
                .getPlatform()
                .getRegionContainer()
                .createQuery()
                .testState(BukkitAdapter.adapt(loc), localAttacker, Flags.PVP);

        Territory playerTerritory = RuneQuestPlugin.getTerritoryHandler().getPlayersTerritory(runePlayer);
        boolean territoryPVPDisabled = playerTerritory == null || !playerTerritory.isPvpZone();

        return WGPVPDisabled || territoryPVPDisabled;
    }

    // Check if PVP is disabled: only allow PVP if both are NOT in a PVP-disabled region
    private boolean isPVPHitDisabled(CombatEntity attacker, CombatEntity defender) {
        if (!(attacker.getEntity() instanceof Player attackerPlayer) || !(defender.getEntity() instanceof Player defenderPlayer)) {
            return false; // Only care about player vs player
        }

        return isPlayerInPVPDisabledRegion(attackerPlayer) || isPlayerInPVPDisabledRegion(defenderPlayer);
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

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityAttackEntityMelee(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player && player.isOp() && player.getGameMode() == GameMode.CREATIVE)) {
            event.setDamage(0);
        }

        Entity defenderEntity = event.getEntity();

        if (defenderEntity instanceof AbstractHorse horse && !horse.getPassengers().isEmpty()) {
            defenderEntity = horse.getPassengers().getFirst();
        }

        if (!(defenderEntity instanceof LivingEntity livingDefenderEntity)) return;

        CombatEntity attacker = getCombatEntity(event.getDamager());
        CombatEntity defender = getCombatEntity(livingDefenderEntity);
        @Nullable Hit hit = null;

        if (attacker == null || defender == null || isPVPHitDisabled(attacker, defender)) {
            return;
        }

        hit = hitHandler.handleMeleeHit(attacker, defender);

        if (hit != null && hit.getDamage() == 0) {
            event.setCancelled(true); // Cancel knockback if hit is a miss
        }

        // Set mobs target to attacker.
        if (defender.getEntity() instanceof Mob mob && attacker.getEntity() instanceof LivingEntity livingAttacker) {
            if (!(livingAttacker instanceof Player)) {
                return;
            }

            // If player is in a vehicle, target vehicle
            if (livingAttacker.isInsideVehicle() && livingAttacker.getVehicle() instanceof LivingEntity livingVehicle) {
                mob.setTarget(livingVehicle);
                return;
            }

            mob.setTarget(livingAttacker);
        }

    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityAttackEntityProjectile(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player && player.isOp() && player.getGameMode() == GameMode.CREATIVE)) {
            event.setDamage(0);
        }

        Entity defenderEntity = event.getEntity();

        if (defenderEntity instanceof AbstractHorse horse && !horse.getPassengers().isEmpty()) {
            defenderEntity = horse.getPassengers().getFirst();
        }

        if (!(defenderEntity instanceof LivingEntity livingDefenderEntity)) {
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
        CombatEntity defender = getCombatEntity(livingDefenderEntity);

        if (attacker != null && defender != null) {
            String typeString = projectile.getPersistentDataContainer().get(projectileType, PersistentDataType.STRING);
            if (typeString == null) return; // Unknown projectile

            if (isPVPHitDisabled(attacker, defender)) {
                return;
            }

            ProjectileType type = ProjectileType.valueOf(typeString);
            @Nullable Hit hit = null;

            switch (type) {
                case RANGED -> {
                    hit = hitHandler.handleRangedHit(attacker, defender);
                }
                case MAGIC -> {
                    hit = hitHandler.handleMagicHit(attacker, defender);
                }
            }

            if (!(livingDefenderEntity instanceof Mob mob)) {
                return;
            }

            projectile.remove();

            if (hit != null && hit.getDamage() == 0) {
                event.setCancelled(true); // Cancel knockback if hit is a miss
            }
            AttributeInstance followRange = mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE);
            if (followRange != null) followRange.setBaseValue(100);

            // If player is in a vehicle, target vehicle
            if (livingShooter.isInsideVehicle() && livingShooter.getVehicle() instanceof LivingEntity livingVehicle) {
                mob.setTarget(livingVehicle);
                return;
            }

            mob.setTarget(livingShooter);


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
            return;
        }

        projectile.getPersistentDataContainer().set(projectileType, PersistentDataType.STRING, ProjectileType.MAGIC.toString());


    }
}
