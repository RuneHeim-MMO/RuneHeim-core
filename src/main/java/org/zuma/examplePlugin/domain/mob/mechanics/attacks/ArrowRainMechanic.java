package org.zuma.examplePlugin.domain.mob.mechanics.attacks;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.combat.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.combat.constants.ProjectileType;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;

import java.util.concurrent.ThreadLocalRandom;

public class ArrowRainMechanic implements MobMechanic {
    public static final NamespacedKey projectileType = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistenceDataKey.PROJECTILE_TYPE.asString());
    private CombatantMob mob;
    private final int totalArrows, arrowsPerTick, delayBetweenTicks, spread;

    public ArrowRainMechanic(int totalArrows, int arrowsPerTick, int delayBetweenTicks, int spread) {
        this.totalArrows = totalArrows;
        this.arrowsPerTick = arrowsPerTick;
        this.delayBetweenTicks = delayBetweenTicks;
        this.spread = spread;
    }

    @Override
    public void setParent(AbstractMob mob) {
        if (!(mob instanceof CombatantMob combatantMob)) {
            throw new IllegalArgumentException("SpecialAttackMechanic can only be applied to CombatantMob");
        }

        this.mob = combatantMob;
    }

    @Override
    public void execute(LivingEntity target) {
        if (mob == null || mob.getEntity() == null || target == null || target.isDead()) return;

        final World world = target.getWorld();
        final Vector downward = new Vector(0, -1, 0);
        final ProjectileSource shooter = mob.getEntity() instanceof ProjectileSource ps ? ps : null;
        final ThreadLocalRandom random = ThreadLocalRandom.current();

        new BukkitRunnable() {
            int spawned = 0;

            @Override
            public void run() {
                if (spawned >= totalArrows || mob == null || mob.getEntity() == null || !mob.getEntity().isValid()) {
                    cancel();
                    return;
                }

                final Location baseLoc = target.getLocation().clone().add(0, 15, 0);

                for (int i = 0; i < arrowsPerTick && spawned < totalArrows; i++) {
                    double spreadX = (random.nextDouble() - 0.5) * 2 * spread;
                    double spreadZ = (random.nextDouble() - 0.5) * 2 * spread;

                    Location arrowLoc = baseLoc.clone().add(spreadX, 0, spreadZ);
                    Arrow arrow = world.spawnArrow(arrowLoc, downward, 2.0f, 0.0f);

                    arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                    if (shooter != null) arrow.setShooter(shooter);
                    arrow.setGravity(true);
                    arrow.setPersistent(false);
                    arrow.getPersistentDataContainer().set(projectileType, PersistentDataType.STRING, ProjectileType.RANGED.name());

                    spawned++;
                }
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, delayBetweenTicks);
    }

    @Override
    public void cleanup() {
        this.mob = null;
    }

}
