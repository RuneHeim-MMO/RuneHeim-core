package org.zuma.examplePlugin.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.player.events.RunePlayerLoadedEvent;
import org.zuma.examplePlugin.utils.TextUtils;

public class RunePlayerListener implements Listener {
    private final RunePlayerHandler playerHandler;

    public RunePlayerListener(RunePlayerHandler playerHandler) {
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
}
