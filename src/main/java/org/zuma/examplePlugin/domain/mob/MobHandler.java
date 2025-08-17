package org.zuma.examplePlugin.domain.mob;

import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.ownership.ItemOwnershipHandler;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.behavior.MobIdleBehaviorHandler;
import org.zuma.examplePlugin.domain.mob.constants.MobDataKey;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;
import java.util.UUID;

public class MobHandler {
    private final MobRegistry registry;

    public MobHandler(MobRegistry registry) {
        this.registry = registry;
        MobIdleBehaviorHandler idleManager = new MobIdleBehaviorHandler(registry);
        idleManager.start();
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

    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, AbstractMob mob, RunePlayer player) {
        mob.onPlayerInteractWithEntity(event, player);
    }

    public void onPlayerAttackEntity(EntityDamageByEntityEvent event, AbstractMob mob, RunePlayer player) {
        mob.onPlayerAttackEntity(event, player);
    }

    public void onEntityAttackEntity(EntityDamageByEntityEvent event, AbstractMob mob, Entity attacker) {
        mob.onEntityAttackEntity(event, attacker);
    }

    public void onEntityAttackPlayer(EntityDamageByEntityEvent event, AbstractMob mob, RunePlayer player) {
        mob.onEntityAttackPlayer(event, player);

        if (mob.getBehavior() != null && !event.isCancelled()) {
            mob.getBehavior().playSound(event.getEntity(), MobBehavior.MobSoundType.NEGATIVE);
        }
    }

    public void onEntityDeath(EntityDeathEvent event, AbstractMob mob) {
        List<ItemStack> drops = mob.rollDropTables();
        Location dropLocation = event.getEntity().getLocation();

        drops.forEach(drop -> {
            if (event.getEntity().hasMetadata("killer")) {
                ItemOwnershipHandler.setDroppedItemPrivate(drop, UUID.fromString(event.getEntity().getMetadata("killer").getFirst().asString()));
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

        if (mob.getUUID() != null) {
            registry.removeMob(mob.getUUID());
        }

        mob.onEntityDeath(event);
    }


    public void onEntityDamaged(EntityDamageEvent event, AbstractMob mob) {
        if (mob.getBehavior() != null && !event.isCancelled()) {
            mob.getBehavior().playSound(event.getEntity(), MobBehavior.MobSoundType.HURT);
        }

        mob.onEntityDamaged(event);
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
}
