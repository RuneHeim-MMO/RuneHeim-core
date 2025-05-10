package org.zuma.examplePlugin.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.ExamplePlugin;
import org.zuma.examplePlugin.interfaces.Callback;
import org.zuma.examplePlugin.logger.LogLevel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RuneQuestPlayerHandler {
    private final ExamplePlugin plugin;
    private final Map<UUID, RuneQuestPlayer> players;


    public RuneQuestPlayerHandler(ExamplePlugin pluginInstance) {
        plugin = pluginInstance;
        players = new HashMap<>();
    }

    public void handleOnEnable() {
        Bukkit.getOnlinePlayers().forEach(this::loadPlayerData);
    }

    public void handleOnDisable() {
        Bukkit.getOnlinePlayers().forEach(this::savePlayerData);
    }

    public void loadPlayerData(Player player) {
        loadPlayerData(player, Callback.noop());
    }

    public void loadPlayerData(Player player, Callback<RuneQuestPlayer> cb) {
        UUID uuid = player.getUniqueId();

        if (players.containsKey(uuid)) {
            player.kick(Component.text("Player " + uuid + " is already online."));
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {

                // TODO: Deserialize & initialize player
                RuneQuestPlayer runeQuestPlayer = new RuneQuestPlayer(uuid);

                Bukkit.getScheduler().runTask(plugin, () -> {
                    players.put(uuid, runeQuestPlayer);
                    cb.onComplete(runeQuestPlayer);
                });

            } catch (Exception e) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    player.kick(Component.text("Failed to load data for player " + player.getName() + " : " + e.getMessage()).color(NamedTextColor.RED));
                    plugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to load data for player " + player.getName() + " : " + e.getMessage());
                });
            }
        });

    }

    public void savePlayerData(Player player) {
        loadPlayerData(player, Callback.noop());
    }

    public void savePlayerData(Player player, Callback<Void> cb) {
        UUID uuid = player.getUniqueId();

        if (!players.containsKey(uuid)) {
            player.kick(Component.text("Cannot save player " + uuid + ". RuneQuestPlayer instance not found."));
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {

                // TODO: Serialize and save player

                Bukkit.getScheduler().runTask(plugin, () -> {
                    players.remove(uuid);
                    cb.onComplete(null);
                });

            } catch (Exception e) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    player.kick(Component.text("Failed to save player data " + player.getName() + " :" + e.getMessage()).color(NamedTextColor.RED));
                    plugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to save player data " + player.getName() + " :" + e.getMessage());
                });
            }
        });


    }
}
