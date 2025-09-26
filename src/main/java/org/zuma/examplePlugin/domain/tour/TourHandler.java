package org.zuma.examplePlugin.domain.tour;

import com.melluh.servertours.api.ServerToursAPI;
import com.melluh.servertours.api.object.Route;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TourHandler {
    private HashMap<UUID, Runnable> viewingPlayers;

    public TourHandler() {
        this.viewingPlayers = new HashMap<>();
    }

    public void startTour(RunePlayer player, String tour, @Nullable Runnable onFinish) {
        if (viewingPlayers.containsKey(player.getUUID())) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to start tour for player " + player.getUUID() + ": Already viewing a tour");
            return;
        }

        if (player.isInCutscene()) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to start tour for player " + player.getUUID() + ": Already in cutscene");
            return;
        }

        try {
            Route route = ServerToursAPI.getRouteManager().getRoute(tour);
            player.setInCutscene(player.asBukkitPlayer().getLocation());

            ServerToursAPI.getPlaybackManager().showTour(player.asBukkitPlayer(), route);
            viewingPlayers.put(player.getUUID(), onFinish);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to start tour for player " + player.getUUID() + ": " + e.getMessage());
        }
    }

    public void handleTourEnd(@Nullable UUID playerUUID) {
        // Clean up all offline players if no playerUUID is provided
        if (playerUUID == null) {
            List<UUID> offlineUUIDs = new ArrayList<>();

            for (UUID uuid : viewingPlayers.keySet()) {
                Player player = Bukkit.getPlayer(uuid);

                if (player == null || !player.isOnline()) {
                    offlineUUIDs.add(uuid);

                    Runnable runnable = viewingPlayers.get(uuid);
                    if (runnable != null) {
                        runnable.run();
                    }

                }
            }

            for (UUID uuid : offlineUUIDs) {
                viewingPlayers.remove(uuid);
            }
            return;
        }

        RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(playerUUID);
        if (player != null && viewingPlayers.containsKey(player.getUUID())) {
            player.setOutOfCutscene();

            Runnable runnable = viewingPlayers.get(player.getUUID());
            if (runnable != null) {
                runnable.run();
            }
        }

        viewingPlayers.remove(playerUUID);
    }

}
