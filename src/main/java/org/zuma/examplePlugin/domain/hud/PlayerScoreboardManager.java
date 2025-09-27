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
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.domain.territory.Territory;
import org.zuma.examplePlugin.domain.territory.TerritoryHandler;

import javax.annotation.Nullable;

public class PlayerScoreboardManager {
    private final static int CENTERING_PADDING = 20;
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
        Territory territory = RuneQuestPlugin.getTerritoryHandler().getPlayersTerritory(player);

        if (o == null) {
            return;
        }

        if (territory != null && territory.isPvpZone()) {
            o.getScore("     ").setScore(15);
            o.getScore(TextUtils.centerText(tick ? " §c§l[PVP ZONE]" : " §4§l[PVP ZONE]", PlayerScoreboardManager.CENTERING_PADDING)).setScore(14);

            if(territory.isDropItemsOnDeath()){
                o.getScore(TextUtils.centerText(tick ? " §c§lItems Not Safe" : " §4§lItems Not Safe", PlayerScoreboardManager.CENTERING_PADDING)).setScore(13);
            }
        }


        o.getScore("").setScore(12);

        o.getScore("§7Party: " + partyString).setScore(11);
        o.getScore("§7Combat Level: §3" + TextUtils.getLegacyColorCodeForLevel(player.getCombatLvl()) + player.getCombatLvl()).setScore(10);

        o.getScore(" ").setScore(9);
        o.getScore("§7Skill Progression:").setScore(8);
        if (lastXPGainedSkillType != null) {
            o.getScore(TextUtils.centerText("§f" + lastXPGainedSkillType.getSymbol() + " §f" + lastXPGainedSkillType.getLabel() + " §f"
                    + TextUtils.getLegacyColorCodeForPercent(PlayerUtils.getLastSkillPercent(player)) + PlayerUtils.getLastSkillPercent(player) + "%", PlayerScoreboardManager.CENTERING_PADDING)).setScore(7);
        } else {
            o.getScore(TextUtils.centerText("§fNo recent skill", PlayerScoreboardManager.CENTERING_PADDING)).setScore(7);
        }

        o.getScore("  ").setScore(6);
        o.getScore("§7Current Location:").setScore(5);

        if (territory != null) {
            o.getScore("§3" + TextUtils.centerText(territory.getName(), PlayerScoreboardManager.CENTERING_PADDING)).setScore(4);
        } else {
            o.getScore("§2" + TextUtils.centerText("Wilderness", PlayerScoreboardManager.CENTERING_PADDING)).setScore(4);
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
        Objective objective = board.registerNewObjective("stats", Criteria.DUMMY, Component.text(Icon.LOGO.get()));

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
