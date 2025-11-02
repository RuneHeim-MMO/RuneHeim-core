package org.zuma.examplePlugin.domain.mob.projectiles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.EntityUtils;
import org.zuma.examplePlugin.domain.combat.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.combat.constants.ProjectileType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RuneProjectile implements Listener {
    public static final NamespacedKey projectileType = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistenceDataKey.PROJECTILE_TYPE.asString());

    private final ProjectileType type;
    private final List<UUID> projectiles;
    private final EntityType visualType;
    private final double speed;

    public RuneProjectile(ProjectileType type, EntityType visualType, double speed) {
        this.type = type;
        this.projectiles = new ArrayList<>();
        this.visualType = visualType;
        this.speed = speed;
    }

    public static boolean isCustomProjectile(Projectile projectile) {
        return projectile.hasMetadata("custom_projectile");
    }

    public Projectile shootProjectileAtTarget(LivingEntity shooter, LivingEntity target) {
        return this.shootProjectileAtTarget(shooter, target, this.speed);
    }

    public Projectile shootProjectileAtTarget(LivingEntity shooter, LivingEntity target, double speed) {
        Projectile projectile = visualType == EntityType.ARROW ?
                shooter.getWorld().spawn(shooter.getEyeLocation(), Arrow.class) :
                shooter.getWorld().spawn(shooter.getEyeLocation(), Snowball.class);

        projectile.setShooter(shooter);
        projectile.setSilent(true);
        projectile.setInvisible(true);

        if (!visualType.equals(EntityType.ARROW)) {
            Entity visual = spawnVisual(projectile);
            projectile.addPassenger(visual);
        }

        Vector direction = target.getEyeLocation().toVector()
                .subtract(shooter.getEyeLocation().toVector())
                .normalize()
                .multiply(speed);

        projectile.setVelocity(direction);
        handleProjectileLaunch(projectile);

        return projectile;
    }

    public Projectile replaceProjectile(Projectile projectile) {
        if (projectile.getType().equals(visualType)) {
            handleProjectileLaunch(projectile);
            return projectile;
        }

        // Store original properties
        Vector velocity = projectile.getVelocity();
        Location location = projectile.getLocation();
        World world = projectile.getWorld();
        ProjectileSource shooter = projectile.getShooter();

        // Calculate direction for rotation
        Vector direction = velocity.clone().normalize();

        // Remove original projectile
        projectile.remove();

        // Spawn new projectile
        Snowball snowball = world.spawn(location, Snowball.class);
        snowball.setVelocity(velocity);
        snowball.setShooter(shooter);

        // Rotate snowball to match direction
        Location loc = snowball.getLocation();
        loc.setDirection(direction);
        snowball.teleport(loc);

        // Spawn visual entity and rotate
        Entity visual = spawnVisual(snowball);
        Location visualLoc = visual.getLocation();
        visualLoc.setDirection(direction);
        visual.teleport(visualLoc);

        snowball.addPassenger(visual);

        handleProjectileLaunch(snowball);
        return snowball;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!this.projectiles.contains(event.getEntity().getUniqueId())) return;

        handleProjectileHit(event, event.getEntity());
    }

    private void handleProjectileHit(ProjectileHitEvent event, Projectile baseProjectile) {
        HandlerList.unregisterAll(this);
        this.projectiles.remove(baseProjectile.getUniqueId());
        onProjectileHit(event, baseProjectile);
    }

    private void handleProjectileLaunch(Projectile baseProjectile) {
        baseProjectile.setMetadata("custom_projectile", new FixedMetadataValue(RuneQuestPlugin.getInstance(), true));
        baseProjectile.getPersistentDataContainer().set(projectileType, PersistentDataType.STRING, type.name());

        Bukkit.getServer().getPluginManager().registerEvents(this, RuneQuestPlugin.getInstance());
        this.projectiles.add(baseProjectile.getUniqueId());
        onProjectileLaunch(baseProjectile);
    }

    public void onProjectileHit(ProjectileHitEvent event, Projectile baseProjectile) {

    }

    public void onProjectileLaunch(Projectile baseProjectile) {

    }


    private Entity spawnVisual(Projectile baseProjectile) {
        Entity visual = baseProjectile.getWorld().spawnEntity(
                baseProjectile.getLocation(),
                visualType
        );

        visual.setRotation(baseProjectile.getLocation().getYaw(), baseProjectile.getLocation().getPitch());

        if (visual instanceof LivingEntity living) {
            living.setAI(false);
            living.setCollidable(false);
        }


        visual.setInvulnerable(true);
        visual.setGravity(false);
        visual.setSilent(true);
        visual.setNoPhysics(true);

        return visual;
    }


}
