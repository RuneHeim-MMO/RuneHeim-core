package org.zuma.examplePlugin.domain.mob;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

import java.util.function.BiConsumer;

public class MobListener implements Listener {
    private final RunePlayerManager playerHandler;
    private final MobHandler mobHandler;
    private final MobRegistry mobRegistry;

    public MobListener(RunePlayerManager playerManager, MobHandler mobHandler, MobRegistry mobRegistry) {
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

    @EventHandler
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event) {
        withMobAndPlayer(event.getPlayer(), event.getRightClicked(), (mob, player) -> {
            mobHandler.onPlayerInteractWithEntity(event, mob, player);
        });

    }

    @EventHandler
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

        if (!(event.getEntity() instanceof Player bukkitPlayer)) {
            return;
        }


        withMobAndPlayer(bukkitPlayer, event.getEntity(), (mob, player) -> {
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
}
