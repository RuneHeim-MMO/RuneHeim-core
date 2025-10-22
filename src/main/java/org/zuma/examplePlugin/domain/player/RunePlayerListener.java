package org.zuma.examplePlugin.domain.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;

public class RunePlayerListener implements Listener {
    private final RunePlayerManager playerHandler;

    public RunePlayerListener(RunePlayerManager playerHandler) {
        this.playerHandler = playerHandler;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        playerHandler.loadPlayerData(player);

        // TODO: Regular players have creative mode on login, no idea why...
        event.getPlayer().setGameMode(GameMode.SURVIVAL);
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }

    // Cancel real deaths, handle by teleporting to city
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerFinalDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        double finalHealth = player.getHealth() - event.getFinalDamage();
        if (finalHealth <= 0) {
            event.setCancelled(true);
            PlayerUtils.teleportToClosestCity(player);
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerHandler.handlePlayerDisconnect(player);
    }

    @EventHandler
    public void onRunePlayerLoaded(RunePlayerLoadedEvent event) {
        event.getBukkitPlayer().sendMessage(Component.text(TextUtils.repeatString("-", 53)).color(Color.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));
        event.getBukkitPlayer().sendMessage(Component.empty());
        event.getBukkitPlayer().sendMessage(Component.text(TextUtils.centerText("Players data successfully loaded!", 70)).color(Color.SUCCESS.asColor()));
        event.getBukkitPlayer().sendMessage(Component.empty());
        event.getBukkitPlayer().sendMessage(Component.text(TextUtils.repeatString("-", 53)).color(Color.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (RuneQuestPlugin.getGlobalModifierHandler().getActiveModifiers().isEmpty() || event.getPlayer() == null)
                return;

            if (event.getPlayer().asBukkitPlayer().isOnline()) {
                HudService.globalModifiersActivated(event.getPlayer(), RuneQuestPlugin.getGlobalModifierHandler().getActiveModifiers().stream().toList());
                SoundType.GLOBAL_EFFECT_ACTIVATED.play(event.getPlayer(), true);
            }
        }, 20L);
    }

    @EventHandler
    public void onPlayerActionInterruptedByMovement(PlayerMoveEvent event) {
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(event.getPlayer());

        if (runePlayer == null || !runePlayer.hasPendingActions()) {
            return;
        }

        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getBlockX() == to.getBlockX()
                && from.getBlockY() == to.getBlockY()
                && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        runePlayer.cancelActionTask(true);
    }

    @EventHandler
    public void onPlayerActionInterruptedByDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null || !runePlayer.hasPendingActions()) {
            return;
        }


        runePlayer.cancelActionTask(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setKeepInventory(true);
        event.getDrops().clear();
        event.setKeepLevel(true);
        event.setDroppedExp(0);
    }
}
