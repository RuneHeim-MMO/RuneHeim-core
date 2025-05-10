package org.zuma.examplePlugin.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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

        try {
            playerHandler.loadPlayerData(player.getUniqueId());
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to load player data for player " + player.getName() + ": " + e.getMessage());
            player.kick(Component.text(e.getMessage()));
            return;
        }

        player.sendMessage(Component.text("Hello, " + player.getName() + "!"));
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        try {
            playerHandler.savePlayerData(player.getUniqueId());
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to save player data " + player.getName() + ": " + e.getMessage());
            return;
        }
    }
}
