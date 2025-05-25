package org.zuma.examplePlugin.domain.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.core.constants.Colors;
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
        playerHandler.savePlayerData(player, true);

    }

    @EventHandler
    public void onRunePlayerLoaded(RunePlayerLoadedEvent event) {
        event.getBukkitPlayer().sendMessage(Component.text(TextUtils.repeatString("-", 53)).color(Colors.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));
        event.getBukkitPlayer().sendMessage(TextUtils.centerText("Players data successfully loaded!", 72).color(Colors.SUCCESS.asColor()));
        event.getBukkitPlayer().sendMessage(Component.text(TextUtils.repeatString("-", 53)).color(Colors.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));
    }

    @EventHandler
    public void onPlayerBusyStateInterruptedByMovement(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getBlockX() == to.getBlockX()
                && from.getBlockY() == to.getBlockY()
                && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        playerHandler.handleBusyStateInterruption(event.getPlayer());
    }

    @EventHandler
    public void onPlayerBusyStateInterruptedByDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        playerHandler.handleBusyStateInterruption(player);
    }
}
