package org.zuma.examplePlugin.domain.daily;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class DailyRewards {
    private final HashMap<Integer, DailyRewardEntry> rewardEntries;
    private int currentStreak;
    private long lastUpdateTimestamp;
    private boolean todayClaimed;

    public DailyRewards(int currentStreak, long lastUpdateTimestamp, boolean todayClaimed, HashMap<Integer, DailyRewardEntry> rewardEntries) {
        this.currentStreak = currentStreak;
        this.lastUpdateTimestamp = lastUpdateTimestamp;
        this.todayClaimed = todayClaimed;
        this.rewardEntries = rewardEntries;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public boolean isTodayClaimed() {
        return todayClaimed;
    }

    public HashMap<Integer, DailyRewardEntry> getRewardEntries() {
        return rewardEntries;
    }

    public boolean canClaimToday() {
        return !todayClaimed;
    }

    public void claimToday(RunePlayer player) {
        if (!canClaimToday()) {
            return;
        }

        this.todayClaimed = true;
        this.lastUpdateTimestamp = System.currentTimeMillis();

        DailyRewardEntry entry = rewardEntries.get(currentStreak);
        if (entry == null) return;

        int amount = PlayerUtils.getRandom().nextInt(entry.max() - entry.min() + 1) + entry.min();
        ItemRecord result = new ItemRecord(entry.itemId(), amount);
        player.giveItemOrDrop(result);
        SoundType.DAILY_REWARD_CLAIM.play(player, false);
    }

    public void updateIfNeeded(RunePlayer player) {
        long now = System.currentTimeMillis();

        if (this.rewardEntries.isEmpty()) {
            RuneQuestPlugin.getDailyRewardHandler().assignNewDailyRewards(this);
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Assigned new daily rewards for player " + player.getUUID() + " due to empty reward entries.");
        }

        LocalDate last = Instant.ofEpochMilli(lastUpdateTimestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate current = Instant.ofEpochMilli(now)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(last, current);
        boolean isOverRewardsBoundary = currentStreak >= rewardEntries.size();

        if (isOverRewardsBoundary || daysBetween > 1) {
            String serverMessage = isOverRewardsBoundary
                    ? "exceeding the maximum reward boundary."
                    : "inactivity of " + daysBetween + " days.";

            if (currentStreak == 1) {
                todayClaimed = false;
                lastUpdateTimestamp = now;
                return;
            }

            currentStreak = 1;
            todayClaimed = false;
            lastUpdateTimestamp = now;
            RuneQuestPlugin.getDailyRewardHandler().assignNewDailyRewards(this);
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Reset daily reward streak for player " + player.getUUID() + " due to " + serverMessage);

            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                Player bukkitPlayer = player.asBukkitPlayer();

                if (bukkitPlayer == null) {
                    return;
                }

                if (isOverRewardsBoundary) {
                    HudService.sendInfoMessage(player, Component.text("Your daily reward streak has been reset because you have exceeded the maximum streak."));
                    SoundType.DAILY_REWARD_RESET_STREAK.play(player, false);
                } else {
                    HudService.sendInfoMessage(player, Component.text("Your daily reward streak has been reset due to inactivity. Login daily to maintain your streak!"));
                    SoundType.DAILY_REWARD_LOSE_STREAK.play(player, false);
                }

            }, 60L);
            return;
        }

        if (daysBetween == 1) {
            currentStreak++;
            todayClaimed = false;
            lastUpdateTimestamp = now;

            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                Player bukkitPlayer = player.asBukkitPlayer();

                if (bukkitPlayer != null && canClaimToday()) {
                    RuneQuestPlugin.getDailyRewardHandler().sendReminder(player);
                    SoundType.DAILY_REWARD_ADVANCE_STREAK.play(player, false);
                }
            }, 60L);
        }
    }

}
