package org.zuma.examplePlugin.core.handlers.core;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.TextUtils;

import java.util.concurrent.TimeUnit;

public class ScheduledRestartHandler implements Listener {
    private static BukkitTask restartTask = null;
    private static Long restartAt = null;
    private static String restartReason = null;

    public static void scheduleRestart(Long restartAt, String reason) {
        if (restartTask != null) {
            return;
        }

        ScheduledRestartHandler.restartAt = restartAt;
        ScheduledRestartHandler.restartReason = reason;

        taskTick();
    }

    private static void cancelRestart() {
        if (restartTask != null) {
            restartTask.cancel();
        }

        ScheduledRestartHandler.restartAt = null;
        ScheduledRestartHandler.restartReason = null;
        ScheduledRestartHandler.restartTask = null;
    }

    private static void taskTick() {
        if (restartReason == null || restartAt == null) {
            return;
        }

        if (System.currentTimeMillis() > restartAt) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.kick(TextUtils.getBrandKickScreen(
                        Component.text("Server is restarting, please rejoin soon!\n\nRestart reason: " + restartReason, Color.INFO.asColor())
                ));
            }

            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), Bukkit::shutdown, 60L);
        }

        restartTask = Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), ScheduledRestartHandler::taskTick, isMoreThanOneMinute(restartAt - 1000L /* compensation */) ? 20L * 60L : 60L);
        broadcastRestartMessage(restartAt, restartReason);

    }

    private static void broadcastRestartMessage(Long restartAt, String reason) {
        Bukkit.broadcast(Component.text(TextUtils.repeatString("-", 53)).color(Color.WARNING.asColor()).decorate(TextDecoration.STRIKETHROUGH));
        Bukkit.broadcast(Component.empty());
        Bukkit.broadcast(Icon.INFO.asComponent().append(Component.text("Restart has been scheduled and will occur in " + formatTimeLeft(restartAt), Color.WARNING.asColor())));
        Bukkit.broadcast(Component.empty());
        Bukkit.broadcast(Component.text("Reason: " + reason, Color.WARNING.asColor()));
        Bukkit.broadcast(Component.empty());
        Bukkit.broadcast(Component.text(TextUtils.repeatString("-", 53)).color(Color.WARNING.asColor()).decorate(TextDecoration.STRIKETHROUGH));
    }

    private static String formatTimeLeft(long futureTimestampMillis) {
        long now = System.currentTimeMillis();
        long diff = futureTimestampMillis - now;

        if (diff <= 0) {
            return "0 seconds"; // already passed
        }

        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60;

        if (hours > 0) {
            // Show hours + minutes
            return String.format("%d hour%s %d minute%s",
                    hours, (hours == 1 ? "" : "s"),
                    minutes, (minutes == 1 ? "" : "s"));
        } else if (minutes > 0) {
            // Show only minutes
            return String.format("%d minute%s",
                    minutes, (minutes == 1 ? "" : "s"));
        } else {
            // Show only seconds
            return String.format("%d second%s",
                    seconds, (seconds == 1 ? "" : "s"));
        }
    }

    private static boolean isMoreThanOneMinute(long futureTimestampMillis) {
        long now = System.currentTimeMillis();
        long diff = futureTimestampMillis - now;
        return diff > TimeUnit.MINUTES.toMillis(1);
    }

}
