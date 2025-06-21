package org.zuma.examplePlugin.domain.hud;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;

public class ScoreboardManager {
    private final RunePlayer player;
    private BukkitTask activeTask = null;
    private boolean tick = false; // Ticktock counter
    int ticks = 0;

    @Nullable
    Scoreboard scoreboard = null;

    public ScoreboardManager(RunePlayer player) {
        this.player = player;

        initializeScoreboard();
        startDefaultLoop();
    }


    private void setDefaultScoreboardContent(Scoreboard scoreboard) {
        Objective o = scoreboard.getObjective("stats");

        if (o == null) {
            return;
        }

        o.getScore("").setScore(15);
        o.getScore("§6Deaths: §f" + ticks + " ".repeat(14)).setScore(14);
        o.getScore("§6Coins: §f" + ticks + " ".repeat(13)).setScore(13);
        o.getScore("§6Kills: §f" + ticks + " ".repeat(12)).setScore(12);
        o.getScore("§6Deaths: §f" + ticks + " ".repeat(11)).setScore(11);
        o.getScore("§6Coins: §f" + ticks + " ".repeat(10)).setScore(10);
        o.getScore("§6Kills: §f" + ticks + " ".repeat(9)).setScore(9);
        o.getScore("§6Deaths: §f" + ticks + " ".repeat(8)).setScore(8);
        o.getScore("§6Coins: §f" + ticks + " ".repeat(7)).setScore(7);
        o.getScore("§6Kills: §f" + ticks + " ".repeat(6)).setScore(6);
        o.getScore("§6Deaths: §f" + ticks + " ".repeat(5)).setScore(5);
        o.getScore("§6Coins: §f" + ticks + " ".repeat(4)).setScore(4);
        o.getScore("§6Kills: §f" + ticks + " ".repeat(3)).setScore(3);
        o.getScore("§6Deaths: §f" + ticks + " ".repeat(2)).setScore(2);
        o.getScore("§6Coins: §f" + ticks + " ".repeat(1)).setScore(1);

        ticks += 1;
    }

    private void clearScoreboard() {
        if (scoreboard == null) {
            return;
        }

        scoreboard.getEntries().forEach(scoreboard::resetScores);
    }

    private void initializeScoreboard() {
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("stats", Criteria.DUMMY, Component.text("◾"));

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.player.getPlayer().setScoreboard(board);
        scoreboard = board;
    }


    private void startDefaultLoop() {
        if (scoreboard == null) {
            throw new IllegalStateException("Cannot start default loop for scoreboard, its null!");
        }

        if (activeTask != null) {
            activeTask.cancel();
        }

        activeTask = new BukkitRunnable() {

            @Override
            public void run() {
                Player p = player.getPlayer();

                if (p == null || !p.isOnline()) {
                    this.cancel();
                    return;
                }

                clearScoreboard();
                setDefaultScoreboardContent(scoreboard);
                tick = !tick;

            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, 10L);

    }

}
