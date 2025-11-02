package org.zuma.examplePlugin.domain.mob;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import io.papermc.paper.event.entity.EntityKnockbackEvent;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.domain.item.ownership.ItemOwnershipHandler;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.behavior.MobIdleBehaviorHandler;
import org.zuma.examplePlugin.domain.mob.constants.MetadataKeys;
import org.zuma.examplePlugin.domain.mob.constants.MobDataKey;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MobHandler {
    private final MobRegistry registry;
    private static final long TARGET_SWITCH_SWEEP_TICK_INTERVAL = 30L;
    private static final double SEARCH_RADIUS = 12.0;

    public MobHandler(MobRegistry registry) {
        this.registry = registry;
        MobIdleBehaviorHandler idleManager = new MobIdleBehaviorHandler(registry);
        idleManager.start();
        startTargetSyncForMountedPlayersLoop();
        startTickingLoop();
    }

    public void startTickingLoop() {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), () -> {
            new ArrayList<>(registry.getAll()).forEach(m -> {
                if (m.getEntity() == null || !m.getEntity().isValid() || m.getEntity().isDead())
                    return;
                m.tick();
            });
        }, 0L, 20L);

    }

    // Clears all mobs with MOB_UUID data keys.
    public void cleanup() {
        for (World world : Bukkit.getWorlds()) {
            world.getEntities().forEach(entity -> {
                NamespacedKey key = new NamespacedKey(RuneQuestPlugin.getInstance(), MobDataKey.MOB_UUID.asString());
                String value = entity.getPersistentDataContainer().get(key, PersistentDataType.STRING);

                if (value != null) {
                    AbstractMob mob = registry.getByUUID(UUID.fromString(value));

                    if (mob != null) {
                        mob.onDisable();
                    }

                    registry.removeMob(UUID.fromString(value));
                    entity.remove();
                }

            });
        }

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "All mobs cleared.");
    }

    public void startTargetSyncForMountedPlayersLoop() {
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Starting target sync for mounted players loop. Running every " + TARGET_SWITCH_SWEEP_TICK_INTERVAL + " ticks.");
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    maintainTargetsForMountedPlayers();
                } catch (Throwable t) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Target sweep error:", t);
                }
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, TARGET_SWITCH_SWEEP_TICK_INTERVAL);
    }

    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, AbstractMob mob, RunePlayer player) {
        mob.onPlayerInteractWithEntity(event, player);
    }

    public void onPlayerAttackEntity(EntityDamageByEntityEvent event, AbstractMob mob, RunePlayer player) {
        mob.onPlayerAttackEntity(event, player);
    }

    public void onEntityTargetPlayer(EntityTargetEvent event, AbstractMob mob, RunePlayer player) {
        mob.onEntityTargetPlayer(event, player);


        mob.getMobMechanics().values().forEach(m -> m.onEntityTargetPlayer(event, player));
    }

    public void onEntityAttackEntity(EntityDamageByEntityEvent event, AbstractMob mob, Entity attacker) {
        mob.onEntityAttackEntity(event, attacker);
    }

    public void onEntityKnockback(EntityKnockbackEvent event, AbstractMob mob) {
        mob.onEntityKnockback(event);
    }

    public void onEntityAttackPlayer(EntityDamageByEntityEvent event, AbstractMob mob, RunePlayer player) {
        mob.onEntityAttackPlayer(event, player);

        if (mob.getBehavior() != null && !event.isCancelled()) {
            mob.getBehavior().playSound(event.getEntity(), MobBehavior.MobSoundType.NEGATIVE);
        }
    }

    public void onEntityDeath(EntityDeathEvent event, AbstractMob mob) {
        boolean killedByPlayer = event.getEntity().hasMetadata(MetadataKeys.MOST_DAMAGING_PLAYER_UUID.asString());

        @Nullable
        RunePlayer killer = killedByPlayer ? RuneQuestPlugin.getPlayerManager().getRunePlayer(UUID.fromString(event.getEntity().getMetadata(MetadataKeys.MOST_DAMAGING_PLAYER_UUID.asString()).getFirst().asString())) : null;

        // Roll drops only if killed by a player.
        List<ItemStack> drops = killedByPlayer ? mob.rollDropTables(killer) : List.of();
        Location dropLocation = event.getEntity().getLocation();

        drops.forEach(drop -> {
            if (event.getEntity().hasMetadata(MetadataKeys.MOST_DAMAGING_PLAYER_UUID.asString())) {
                ItemOwnershipHandler.setDroppedItemPrivate(drop, UUID.fromString(event.getEntity().getMetadata(MetadataKeys.MOST_DAMAGING_PLAYER_UUID.asString()).getFirst().asString()));
            }

            dropLocation.getWorld().dropItem(dropLocation, drop);
        });

        if (mob.getBehavior() != null && !event.isCancelled()) {
            mob.getBehavior().playSound(event.getEntity(), MobBehavior.MobSoundType.DEATH);
        }

        if (mob.getEntity() != null) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }

        mob.onEntityDeath(event);
        mob.handleMechanicsCleanup();

        if (mob.getUUID() != null) {
            registry.removeMob(mob.getUUID());
        }

    }


    public void onEntityDamaged(EntityDamageEvent event, AbstractMob mob) {
        if (mob.getBehavior() != null && !event.isCancelled()) {
            mob.getBehavior().playSound(event.getEntity(), MobBehavior.MobSoundType.HURT);
        }

        mob.onEntityDamaged(event);
    }

    public void onShootProjectile(ProjectileLaunchEvent event, AbstractMob mob) {
        if (mob.getBehavior() != null && !event.isCancelled()) {
            mob.getBehavior().playSound(event.getEntity(), MobBehavior.MobSoundType.NEGATIVE);
        }

        mob.onEntityShootProjectile(event);
    }

    public void handleRedisguisingOnPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            for (Entity entity : player.getWorld().getEntities()) {
                AbstractMob mob = registry.getMob(entity);
                if (mob != null && mob.getDisguise() != null) {
                    DisguiseAPI.disguiseToAll(entity, mob.getDisguise().clone());
                    mob.reapplyMetaToEntity(null);
                }
            }
        }, 20L);
    }

    // Keep target on player, regardless if hes on a mount or dismounts it.
    private void maintainTargetsForMountedPlayers() {
        // iterate only combatant mobs
        for (AbstractMob e : registry.getAll()) {
            if (!(e.getEntity() instanceof Mob mob) || !(e instanceof CombatantMob)) continue;
            if (!mob.isValid() || mob.isDead()) continue;

            LivingEntity target = mob.getTarget();
            if (target == null) continue;

            // CASE A: mob targeting a player who is mounted -> switch to the mount
            if (target instanceof Player player) {
                if (player.isInsideVehicle()) {
                    Entity vehicle = player.getVehicle();
                    if (vehicle instanceof LivingEntity vehicleLiving) {
                        // only change if different (avoid thrashing)
                        if (mob.getTarget() != vehicleLiving) {
                            mob.setTarget(vehicleLiving);
                        }
                    }
                }
                continue;
            }

            // CASE B: mob targeting an AbstractHorse (horse-like) but the horse is empty -> switch to a player
            if (target instanceof AbstractHorse horse) {
                if (horse.getPassengers().isEmpty()) {
                    Player nearest = MathUtils.findNearestSurvivalPlayer(horse, SEARCH_RADIUS);
                    if (nearest != null) {
                        if (mob.getTarget() != nearest) {
                            mob.setTarget(nearest);
                        }
                    }
                }
            }

        }
    }

}
