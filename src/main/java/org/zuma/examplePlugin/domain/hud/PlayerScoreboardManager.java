package org.zuma.examplePlugin.domain.hud;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.combat.HitListener;
import org.zuma.examplePlugin.domain.modifier.scopes.server.ServerModifierType;
import org.zuma.examplePlugin.domain.party.Party;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.domain.territory.Territory;

import javax.annotation.Nullable;
import java.util.HashMap;

public class PlayerScoreboardManager {
    private final static int CENTERING_PADDING = 20;

    private final RunePlayer player;
    private int ticks = 0;
    private boolean tick = false; // Ticktock counter

    private BukkitTask activeTask = null;
    private @Nullable Scoreboard scoreboard = null;

    private HashMap<ServerModifierType, Integer> activeModifiers;
    private @Nullable Integer visibleModifierIndex;

    public PlayerScoreboardManager(RunePlayer player) {
        this.player = player;
        this.activeModifiers = new HashMap<>();
        this.visibleModifierIndex = null;

        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
            initializeScoreboard();
            startDefaultLoop();
        });
    }

    private void updateVisibleModifier() {
        this.activeModifiers = RuneQuestPlugin.getServerModifierHandler().getModifiersWithDurationLeft();

        if (!activeModifiers.isEmpty() && visibleModifierIndex == null) {
            visibleModifierIndex = 0;
        }

        if (ticks % 20 == 0) {
            if (!activeModifiers.isEmpty()) {
                int index = visibleModifierIndex + 1;

                if (index >= activeModifiers.size()) {
                    index = 0;
                }
                visibleModifierIndex = index;
            } else {
                visibleModifierIndex = null;
            }
        }
    }


    private void setDefaultScoreboardContent(Scoreboard scoreboard) {
        Objective o = scoreboard.getObjective("stats");
        String partyString = player.getParty() == null ? "§fNone" : "§6" + player.getParty().getOwnerUsername();
        SkillType lastXPGainedSkillType = player.getLastXPGainedSkillType();
        Territory territory = RuneQuestPlugin.getTerritoryHandler().getPlayersTerritory(player);
        int lineIndex = 15;

        boolean safetyWarning = false;
        boolean modifierActive = visibleModifierIndex != null;;

        updateVisibleModifier();

        if (o == null) {
            return;
        }

        if (territory != null && territory.isPvpZone()) {
            if (!HitListener.isPlayerInPVPDisabledRegion(player.asBukkitPlayer())) {
                o.getScore(TextUtils.centerText(tick ? " §c§l[PVP ZONE]" : " §4§l[PVP ZONE]", PlayerScoreboardManager.CENTERING_PADDING)).setScore(lineIndex--);
                safetyWarning = true;
            }

            if (territory.isDropItemsOnDeath()) {
                o.getScore(TextUtils.centerText(tick ? " §c§lItems Not Safe" : " §4§lItems Not Safe", PlayerScoreboardManager.CENTERING_PADDING)).setScore(lineIndex--);
                safetyWarning = true;
            }
        }

        if (modifierActive) {
            o.getScore("         ").setScore(lineIndex--);
            ServerModifierType[] types = activeModifiers.keySet().toArray(new ServerModifierType[0]);
            ServerModifierType visibleType = types[visibleModifierIndex];
            Integer minutesLeft = activeModifiers.get(visibleType);
            String effectLabel = visibleType.getLabel().replace(" Bomb", "");

            o.getScore(TextUtils.centerText("§a" + minutesLeft + "m " + visibleType.getColor() + effectLabel, PlayerScoreboardManager.CENTERING_PADDING)).setScore(lineIndex--);
        }

        o.getScore("      ").setScore(lineIndex--);
        o.getScore("§7Party: " + partyString).setScore(lineIndex--);
        o.getScore("§7Combat Level: §3" + TextUtils.getLegacyColorCodeForLevel(player.getCombatLvl()) + player.getCombatLvl()).setScore(lineIndex--);

        o.getScore(" ").setScore(lineIndex--);
        o.getScore("§7Skill Progression:").setScore(lineIndex--);
        if (lastXPGainedSkillType != null) {
            o.getScore(TextUtils.centerText("§f" + lastXPGainedSkillType.getSymbol() + " §f" + lastXPGainedSkillType.getLabel() + " §f"
                    + TextUtils.getLegacyColorCodeForPercent(PlayerUtils.getLastSkillPercent(player)) + PlayerUtils.getLastSkillPercent(player) + "%", PlayerScoreboardManager.CENTERING_PADDING)).setScore(lineIndex--);
        } else {
            o.getScore(TextUtils.centerText("§fNo recent skill", PlayerScoreboardManager.CENTERING_PADDING)).setScore(lineIndex--);
        }

        o.getScore("  ").setScore(lineIndex--);
        o.getScore("§7Current Location:").setScore(lineIndex--);

        if (territory != null) {
            o.getScore("§3" + TextUtils.centerText(territory.getName(), PlayerScoreboardManager.CENTERING_PADDING)).setScore(lineIndex--);
        } else {
            o.getScore("§2" + TextUtils.centerText("Wilderness", PlayerScoreboardManager.CENTERING_PADDING)).setScore(lineIndex--);
        }

        o.getScore("           ").setScore(lineIndex--);

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

    private Team getPlayerTeam() {
        return getPlayerTeam(this.player);
    }

    private Team getPlayerTeam(RunePlayer target) {
        if (scoreboard == null) return null;

        Player bukkitPlayer = target.asBukkitPlayer();
        if (bukkitPlayer == null) return null;

        String teamName = bukkitPlayer.getName();

        Team team = scoreboard.getTeam(teamName);

        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }

        return team;
    }


    private void attachNametag(RunePlayer target) {
        Team team = getPlayerTeam(target);

        if (team == null) return;

        Party targetParty = RuneQuestPlugin.getPartyManager().getPlayersParty(target);
        Component base = Component.text(target.getRole().getSupporterRoleIcon(target)).append(target.getRole().getPrefix(target));

        if (targetParty != null && targetParty.getMemberUUIDs().contains(player.getUUID())) {
            team.prefix(Icon.PARTY.asComponent().append(base));
        } else {
            team.prefix(base);
        }

        team.addEntry(target.asBukkitPlayer().getName());
    }

    public void setPlayerCollision(boolean enabled) {
        Player bukkitPlayer = player.asBukkitPlayer();
        Team team = getPlayerTeam(player);

        if (team == null) return;
        team.addEntry(bukkitPlayer.getName());
        team.setOption(Team.Option.COLLISION_RULE, enabled ? Team.OptionStatus.ALWAYS : Team.OptionStatus.NEVER);
    }

    public void reloadNametags() {
        // Attach all online player nametags
        RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(otherPlayer -> {
            player.getScoreboardManager().attachNametag(otherPlayer);
            otherPlayer.getScoreboardManager().attachNametag(player);
        });
    }

}
