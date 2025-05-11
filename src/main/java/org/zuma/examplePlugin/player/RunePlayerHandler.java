package org.zuma.examplePlugin.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.logger.LogLevel;
import org.zuma.examplePlugin.player.events.RunePlayerLoadedEvent;
import org.zuma.examplePlugin.player.events.RunePlayerSavedEvent;
import org.zuma.examplePlugin.utils.TextUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RunePlayerHandler {
    private final Map<UUID, RunePlayer> players = new HashMap<>();
    private final RunePlayerLoadingStateHandler loadingStateHandler;

    public RunePlayerHandler() {
        loadingStateHandler = new RunePlayerLoadingStateHandler(RuneQuestPlugin.getInstance());
    }

    public @Nullable RunePlayer getRunePlayer(Player player) {
        return this.getRunePlayer(player.getUniqueId());
    }

    public @Nullable RunePlayer getRunePlayer(UUID uuid) {
        return players.get(uuid);
    }

    public void handleOnEnable() {
        Bukkit.getOnlinePlayers().forEach(this::loadPlayerData);
    }

    public void handleOnDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> savePlayerData(player, true));
    }


    public void loadPlayerData(Player player) {
        UUID uuid = player.getUniqueId();

        if (players.containsKey(uuid)) {
            player.kick(TextUtils.getErrorKickScreen("Player " + uuid + " is already online."));
            return;
        }

        loadingStateHandler.setPlayerLoading(player);

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {

                // TODO: Deserialize & initialize player
                RunePlayer runePlayer = new RunePlayer(uuid);

                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    if (Bukkit.getPlayer(uuid) == null) {
                        return;
                    }

                    players.put(uuid, runePlayer);
                    loadingStateHandler.setPlayerLoaded(player);
                    Bukkit.getServer().getPluginManager().callEvent(new RunePlayerLoadedEvent(runePlayer));
                });

            } catch (Exception e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    player.kick(TextUtils.getErrorKickScreen("Failed to load data for player " + player.getName() + "\n" + e.getMessage()));
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to load data for player " + player.getName() + " : " + e.getMessage());
                });
            }
        });

    }


    public void savePlayerData(Player player, boolean unmount) {
        UUID uuid = player.getUniqueId();

        if (!players.containsKey(uuid)) {
            player.kick(TextUtils.getErrorKickScreen("Cannot save player " + uuid + ". RuneQuestPlayer instance not found."));
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {

                // TODO: Serialize and save player

                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    if (unmount) {
                        players.remove(uuid);
                    }
                    Bukkit.getServer().getPluginManager().callEvent(new RunePlayerSavedEvent(player));
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
