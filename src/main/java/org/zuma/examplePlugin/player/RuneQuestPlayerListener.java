package org.zuma.examplePlugin.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.utils.TextUtils;

public class RuneQuestPlayerListener implements Listener {
    private final RuneQuestPlayerHandler playerHandler;

    public RuneQuestPlayerListener(RuneQuestPlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        playerHandler.loadPlayerData(player, (data) -> {
            player.sendMessage(Component.text(TextUtils.repeatString("-", 53)).color(Colors.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));
            player.sendMessage(TextUtils.centerText("Players data successfully loaded!", 72).color(Colors.SUCCESS.asColor()));
            player.sendMessage(Component.text(TextUtils.repeatString("-", 53)).color(Colors.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));
        });

    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        playerHandler.handlePlayerDisconnect(player);
        playerHandler.savePlayerData(player);

    }
}
