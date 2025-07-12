package org.zuma.examplePlugin.domain.mob;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.model.ModeledEntity;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.behavior.MobIdleBehaviorHandler;
import org.zuma.examplePlugin.domain.mob.constants.MobDataKey;
import org.zuma.examplePlugin.domain.player.RunePlayer;

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

                    entity.remove();
                }

            });
        }
    }

    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, AbstractMob mob, RunePlayer player) {
        mob.onPlayerInteractWithEntity(event, player);
    }

    public void onPlayerAttackEntity(EntityDamageByEntityEvent event, AbstractMob mob, RunePlayer player) {
        mob.onPlayerAttackEntity(event, player);

        if (mob.getBehavior() != null && !event.isCancelled()) {
            mob.getBehavior().playSound(event.getEntity(), MobBehavior.MobSoundType.HURT);
        }
    }

    public void onEntityAttackPlayer(EntityDamageByEntityEvent event, AbstractMob mob, RunePlayer player) {
        mob.onEntityAttackPlayer(event, player);
    }

    public void onEntityDeath(EntityDeathEvent event, AbstractMob mob) {
        if (mob.getBehavior() != null && !event.isCancelled()) {
            mob.getBehavior().playSound(event.getEntity(), MobBehavior.MobSoundType.DEATH);
        }

        if (mob.getEntity() != null) {
            mob.getEntity().remove();
        }

        if (mob.getUUID() != null) {
            registry.removeMob(mob.getUUID());
        }
    }


    public void onEntityDamaged(EntityDamageEvent event, AbstractMob mob) {
        mob.onEntityDamaged(event);
    }

    public void onChunkLoad(ChunkLoadEvent event, AbstractMob mob) {
        mob.onChunkLoaded(event);
    }

    public void handleRedisguisingOnPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            for (Entity entity : player.getWorld().getEntities()) {
                AbstractMob mob = registry.getMob(entity);
                if (mob != null && mob.getDisguise() != null) {
                    DisguiseAPI.disguiseToAll(entity, mob.getDisguise().clone());
                    mob.onChunkLoaded(null);
                }
            }
        }, 20L);
    }
}
