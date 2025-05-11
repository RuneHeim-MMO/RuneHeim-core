package org.zuma.examplePlugin.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.interfaces.Callback;
import org.zuma.examplePlugin.logger.LogLevel;
import org.zuma.examplePlugin.utils.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RuneQuestPlayerHandler {
    private final Map<UUID, RuneQuestPlayer> players = new HashMap<>();
    private final RunePlayerLoadingStateHandler loadingStateHandler;

    public RuneQuestPlayerHandler() {
        loadingStateHandler = new RunePlayerLoadingStateHandler(RuneQuestPlugin.getInstance());
    }

    public RuneQuestPlayer getRunePlayer(Player player) {
        return players.get(player.getUniqueId());
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
            player.kick(TextUtils.getErrorKickScreen("Player " + uuid + " is already online."));
            return;
        }

        loadingStateHandler.setPlayerLoading(player);

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {

                // TODO: Deserialize & initialize player
                RuneQuestPlayer runeQuestPlayer = new RuneQuestPlayer(uuid);

                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    if (Bukkit.getPlayer(uuid) == null) {
                        return;
                    }

                    players.put(uuid, runeQuestPlayer);
                    loadingStateHandler.setPlayerLoaded(player);
                    cb.onComplete(runeQuestPlayer);
                });

            } catch (Exception e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    player.kick(TextUtils.getErrorKickScreen("Failed to load data for player " + player.getName() + "\n" + e.getMessage()));
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to load data for player " + player.getName() + " : " + e.getMessage());
                });
            }
        });

    }


    public void savePlayerData(Player player) {
        savePlayerData(player, Callback.noop());
    }

    public void savePlayerData(Player player, Callback<Void> cb) {
        UUID uuid = player.getUniqueId();

        if (!players.containsKey(uuid)) {
            player.kick(TextUtils.getErrorKickScreen("Cannot save player " + uuid + ". RuneQuestPlayer instance not found."));
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {

                // TODO: Serialize and save player

                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    players.remove(uuid);
                    cb.onComplete(null);
                });

            } catch (Exception e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    player.kick(TextUtils.getBrandKickScreen(Component.text("Failed to save player data " + player.getName() + "\n" + e.getMessage()).color(Colors.ERROR.asColor())));
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to save player data " + player.getName() + " :" + e.getMessage());
                });
            }
        });


    }

    public void handlePlayerDisconnect(Player player) {
        loadingStateHandler.setPlayerLoaded(player); // Restore effects before disconnection if player disconnected while he was loading.
    }
}
