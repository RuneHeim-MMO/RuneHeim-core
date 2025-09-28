package org.zuma.examplePlugin.domain.ambience.sound;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.MusicType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.*;

public class AmbientMusicHandler {
    private final HashMap<UUID, Integer> ambientMusicLoopTasks;
    private final HashMap<UUID, String> currentPlayingMusic;
    private final List<UUID> exitLoop; // Players who currently have track playing, but will exit the loop on next iteration

    public AmbientMusicHandler() {
        this.ambientMusicLoopTasks = new HashMap<>();
        this.currentPlayingMusic = new HashMap<>();
        this.exitLoop = new ArrayList<>();
    }

    public void startPlayerMusicLoop(MusicType type, RunePlayer player) {
        // If the same music is already playing, do nothing
        if (currentPlayingMusic.containsKey(player.getUUID()) && Objects.equals(currentPlayingMusic.get(player.getUUID()), type.name())) {
            return;
        }

        stopMusic(player);

        ambientMusicLoopTasks.put(player.getUUID(), new BukkitRunnable() {
            final UUID uuid = player.getUUID();

            @Override
            public void run() {
                if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline() || exitLoop.contains(uuid)) {
                    stopMusic(player);
                    this.cancel();

                    return;
                }

                // Song ended, play again
                type.play(player.asBukkitPlayer());
                currentPlayingMusic.put(uuid, type.name());


            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, type.getDurationSeconds() * 20L).getTaskId());

    }

    public void stopMusic(RunePlayer player) {
        MusicType.stopMusic(player); // Clear previous music;
        currentPlayingMusic.remove(player.getUUID());
        exitLoop.remove(player.getUUID());

        Integer taskId = ambientMusicLoopTasks.remove(player.getUUID());
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId); // Cancel music loop task for player
        }
    }

    public void removePlayerFromLoop(RunePlayer player) {
        this.exitLoop.add(player.getUUID());
    }


}
