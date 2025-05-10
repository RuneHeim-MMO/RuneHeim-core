package org.zuma.examplePlugin.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RuneQuestPlayerListener implements Listener {
    private final RuneQuestPlayerHandler playerHandler;

    public RuneQuestPlayerListener(RuneQuestPlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        playerHandler.loadPlayerData(player, (data) -> {
            player.sendMessage("Successfully loaded player data.");
            player.sendMessage("Welcome, " + player.getName() + "!");
        });

    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        playerHandler.savePlayerData(player);

    }
}
