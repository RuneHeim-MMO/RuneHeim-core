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
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;

public class PlayerScoreboardManager {
    private final RunePlayer player;
    private BukkitTask activeTask = null;
    private boolean tick = false; // Ticktock counter
    int ticks = 0;

    @Nullable
    Scoreboard scoreboard = null;

    public PlayerScoreboardManager(RunePlayer player) {
        this.player = player;

        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
            initializeScoreboard();
            startDefaultLoop();
        });
    }


    private void setDefaultScoreboardContent(Scoreboard scoreboard) {
        Objective o = scoreboard.getObjective("stats");
        String partyString = player.getParty() == null ? "§fNone" : "§6" + player.getParty().getOwnerUsername();
        SkillType lastXPGainedSkillType = player.getLastXPGainedSkillType();

        if (o == null) {
            return;
        }

        o.getScore("").setScore(15);

        o.getScore("§7Party: " + partyString).setScore(14);
        o.getScore("§7Combat Level: §3" + TextUtils.getLegacyColorCodeForLevel(player.getCombatLvl()) + player.getCombatLvl()).setScore(13);

        o.getScore(" ").setScore(12);
        o.getScore("§7Skill Progression:").setScore(11);
        if (lastXPGainedSkillType != null) {

            o.getScore("  §f" + lastXPGainedSkillType.getSymbol() + " §7" + lastXPGainedSkillType.getLabel() + " §f"
                    + TextUtils.getLegacyColorCodeForPercent(PlayerUtils.getLastSkillPercent(player)) + PlayerUtils.getLastSkillPercent(player) + "%  ").setScore(10);
        } else {
            o.getScore("   §fNo recent skill   ").setScore(10);
        }

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
        this.player.asBukkitPlayer().setScoreboard(board);
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
                Player p = player.asBukkitPlayer();

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
