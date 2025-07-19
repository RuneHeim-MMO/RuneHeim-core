package org.zuma.examplePlugin.domain.mob;

import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.cooldown.CooldownHandler;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;

public class MobListener implements Listener {
    private static final long INTERACTION_THROTTLE_INTERVAL = 1000L;
    private final CooldownHandler interactionSpeedHandler;

    private final RunePlayerManager playerHandler;
    private final MobHandler mobHandler;
    private final MobRegistry mobRegistry;

    public MobListener(RunePlayerManager playerManager, MobHandler mobHandler, MobRegistry mobRegistry) {
        this.interactionSpeedHandler = new CooldownHandler();

        this.playerHandler = playerManager;
        this.mobHandler = mobHandler;
        this.mobRegistry = mobRegistry;
    }

    private void withMobAndPlayer(Player bukkitPlayer, Entity entity, BiConsumer<AbstractMob, RunePlayer> consumer) {
        if (entity == null) return;

        AbstractMob mob = mobRegistry.getMob(entity);
        if (mob == null) return;

        RunePlayer player = playerHandler.getRunePlayer(bukkitPlayer);
        if (player == null) return;

        consumer.accept(mob, player);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event) {
        if (interactionSpeedHandler.isCooledDown(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        interactionSpeedHandler.startCooldown(event.getPlayer(), INTERACTION_THROTTLE_INTERVAL);

        withMobAndPlayer(event.getPlayer(), event.getRightClicked(), (mob, player) -> {
            mobHandler.onPlayerInteractWithEntity(event, mob, player);
        });

    }

    @EventHandler(priority = EventPriority.LOW) // To handle damage sounds
    public void onPlayerAttackEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player bukkitPlayer)) {
            return;
        }

        withMobAndPlayer(bukkitPlayer, event.getEntity(), (mob, player) -> {
            mobHandler.onPlayerAttackEntity(event, mob, player);
        });
    }

    @EventHandler
    public void onEntityAttackPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            return;
        }

        if (!(event.getEntity() instanceof Player defender)) {
            return;
        }

        withMobAndPlayer(defender, event.getDamager(), (mob, player) -> {
            mobHandler.onEntityAttackPlayer(event, mob, player);
        });
    }

    @EventHandler
    public void onEntityDamaged(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        AbstractMob mob = mobRegistry.getMob(entity);
        if (mob == null) return;

        mobHandler.onEntityDamaged(event, mob);

    }


    @EventHandler
    public void onEntityRemove(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        AbstractMob mob = mobRegistry.getMob(entity);
        if (mob == null) return;

        mobHandler.onEntityDeath(event, mob);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Set<UUID> seen = new HashSet<>();

        Arrays.stream(event.getChunk().getEntities()).forEach(entity -> {
            if (seen.contains(entity.getUniqueId())) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Duplicate entity uuid on chunk load! " + entity.getUniqueId());
                return;
            }

            seen.add(entity.getUniqueId());

            AbstractMob mob = mobRegistry.getMob(entity);
            if (mob == null) return;


            mobRegistry.removeMob(entity.getUniqueId());
            entity.remove();
            // mobHandler.onChunkLoad(event, mob);
        });
    }

  /*  @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        Arrays.stream(event.getChunk().getEntities()).forEach(entity -> {

            AbstractMob mob = mobRegistry.getMob(entity);
            if (mob == null) return;

            mobRegistry.removeMob(mob.getUUID());
            entity.remove();
        });
    }*/

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        mobHandler.handleRedisguisingOnPlayerJoin(event);
    }


    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if (event.getEntity() instanceof Zombie) {
            event.setCancelled(true); // Prevent burning in sunlight
        }
    }
}
