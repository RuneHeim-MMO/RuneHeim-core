package org.zuma.examplePlugin.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RuneQuestPlayerHandler {
    private final Map<UUID, RuneQuestPlayer> players;

    public RuneQuestPlayerHandler() {
        players = new HashMap<>();
    }

    public void handleReload() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            try {
                loadPlayerData(player.getUniqueId());
                player.sendMessage("Player reloaded 123");
            } catch (Exception e) {
                Bukkit.getLogger().warning("Failed to load player data for player " + player.getName() + ": " + e.getMessage());
                player.kick(Component.text(e.getMessage()));
            }
        });
    }

    public void loadPlayerData(UUID uuid) throws Exception {

        if (players.containsKey(uuid)) {
            throw new Exception("Player " + uuid + " is already online.");
        }


        RuneQuestPlayer player = new RuneQuestPlayer(uuid);
        players.put(uuid, player);

    }

    public void savePlayerData(UUID uuid) throws Exception {

        if (!players.containsKey(uuid)) {
            throw new Exception("Cannot save player " + uuid + ". RuneQuestPlayer instance was not initialized.");
        }

        // TODO: Saving

        players.remove(uuid);
    }
}
