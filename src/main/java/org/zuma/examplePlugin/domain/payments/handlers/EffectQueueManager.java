package org.zuma.examplePlugin.domain.payments.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.*;

public class EffectQueueManager {
    private static final Map<UUID, Queue<AnimatedEffect>> effectQueues = new HashMap<>();
    private static final Map<UUID, Integer> activeTasks = new HashMap<>(); // Track scheduled tasks
    private static final Set<UUID> activePlayers = new HashSet<>();

    public static void startEffect(RunePlayer player, AnimatedEffect effect) {
        if (!isPlayerValid(player)) return;

        UUID uuid = player.getUUID();
        effectQueues.computeIfAbsent(uuid, k -> new LinkedList<>()).offer(effect);

        if (!activePlayers.contains(uuid)) {
            playNextEffect(player);
        }
    }

    public static void cancelAllEffects(RunePlayer player) {
        if (player == null) return;

        UUID uuid = player.getUUID();

        // Cancel any scheduled task
        Integer taskId = activeTasks.remove(uuid);
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId);
        }

        // Clear queues
        Queue<AnimatedEffect> queue = effectQueues.remove(uuid);
        if (queue != null) {
            queue.clear();
        }

        activePlayers.remove(uuid);
    }

    public static void cleanupPlayer(UUID uuid) {
        // Clean up when player leaves
        cancelAllEffects(getRunePlayer(uuid));
    }

    private static void playNextEffect(RunePlayer player) {
        if (!isPlayerValid(player)) {
            cleanupPlayer(player != null ? player.getUUID() : null);
            return;
        }

        UUID uuid = player.getUUID();
        Queue<AnimatedEffect> queue = effectQueues.get(uuid);

        if (queue == null || queue.isEmpty()) {
            activePlayers.remove(uuid);
            activeTasks.remove(uuid);
            return;
        }

        AnimatedEffect effect = queue.poll();
        activePlayers.add(uuid);

        try {
            EffectHandler.playAt(player.asBukkitPlayer().getLocation(), effect);

            // Schedule the next effect after the current one finishes
            int taskId = Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                activeTasks.remove(uuid); // Remove task ID when executed
                playNextEffect(player);
            }, effect.getDurationTicks()).getTaskId();

            activeTasks.put(uuid, taskId);

        } catch (Exception e) {
            // If effect fails, continue with next one after a short delay
            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                playNextEffect(player);
            }, 20L); // 1 second delay on error
        }
    }

    private static boolean isPlayerValid(RunePlayer player) {
        return player != null && !player.isOnline();
    }

    private static RunePlayer getRunePlayer(UUID uuid) {
        Player bukkitPlayer = Bukkit.getPlayer(uuid);
        return bukkitPlayer != null ?
                RuneQuestPlugin.getPlayerManager().getRunePlayer(bukkitPlayer) : null;
    }

    // Utility methods
    public static boolean hasQueuedEffects(RunePlayer player) {
        if (player == null) return false;
        Queue<AnimatedEffect> queue = effectQueues.get(player.getUUID());
        return queue != null && !queue.isEmpty();
    }

    public static int getQueueSize(RunePlayer player) {
        if (player == null) return 0;
        Queue<AnimatedEffect> queue = effectQueues.get(player.getUUID());
        return queue != null ? queue.size() : 0;
    }
}