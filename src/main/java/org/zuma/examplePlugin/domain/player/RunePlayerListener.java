package org.zuma.examplePlugin.domain.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.utils.TextUtils;
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

    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        playerHandler.handlePlayerDisconnect(player);
        playerHandler.savePlayerDataAsync(player, player.getName(), true);

    }

    @EventHandler
    public void onRunePlayerLoaded(RunePlayerLoadedEvent event) {
        event.getBukkitPlayer().sendMessage(Component.text(TextUtils.repeatString("-", 53)).color(Color.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));
        event.getBukkitPlayer().sendMessage(Component.empty());
        event.getBukkitPlayer().sendMessage(Component.text(TextUtils.centerText("Players data successfully loaded!", 70)).color(Color.SUCCESS.asColor()));
        event.getBukkitPlayer().sendMessage(Component.empty());
        event.getBukkitPlayer().sendMessage(Component.text(TextUtils.repeatString("-", 53)).color(Color.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));
    }

    @EventHandler
    public void onPlayerActionInterruptedByMovement(PlayerMoveEvent event) {
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(event.getPlayer());

        if (runePlayer == null || !runePlayer.isBusy()) {
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

        if (runePlayer == null || !runePlayer.isBusy()) {
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
