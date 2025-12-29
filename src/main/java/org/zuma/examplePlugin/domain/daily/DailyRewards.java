package org.zuma.examplePlugin.domain.daily;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.dailies.DailyGrandPrizeClaim;
import org.zuma.examplePlugin.domain.effect.effects.dailies.DailyRarePrizeClaim;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.DailyMegaRareClaimEvent;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;

public class DailyRewards {
    public static List<ItemRarity> MEGA_RARE_TIERS = List.of(ItemRarity.LEGENDARY, ItemRarity.MYTHIC, ItemRarity.DIVINE);
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
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(entry.itemId());
        if (type == null) return;

        int amount = PlayerUtils.getRandom().nextInt(entry.max() - entry.min() + 1) + entry.min();
        ItemRecord result = new ItemRecord(entry.itemId(), amount);
        player.giveItemOrDrop(result);
        SoundType.DAILY_REWARD_CLAIM.play(player, false);

        if (MEGA_RARE_TIERS.contains(type.rarity)) {
            EffectHandler.playAt(player.asBukkitPlayer().getLocation(), new DailyRarePrizeClaim(player));
            Bukkit.getPluginManager().callEvent(new DailyMegaRareClaimEvent(new DailyMegaRareClaimEvent.Payload(player.asBukkitPlayer().getName(), type.displayName, type.rarity, amount)));
            AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.DAILY_MEGA_RARES_CLAIMED, 1);
        }

        if (currentStreak >= rewardEntries.size()) {
            EffectHandler.playAt(player.asBukkitPlayer().getLocation(), new DailyGrandPrizeClaim(player));
        }

        HudService.sendSuccessMessage(player, Component.text("You have claimed your daily reward: ", Color.SUCCESS.asColor()).append(Component.text(amount + "x " + type.displayName, Color.ACCENT.asColor()).append(Component.text("!", Color.SUCCESS.asColor()))));
        HudService.sendSuccessMessage(player, Component.text("Come back tomorrow to claim your next reward!", Color.SUCCESS.asColor()));
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Player " + player.asBukkitPlayer().getName() + " claimed daily reward: " + amount + "x " + type.displayName + " for streak day " + currentStreak + ".");
        AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.DAILY_REWARDS_CLAIMED, 1);
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
        boolean isNextOverRewardsBoundary = currentStreak + 1 > rewardEntries.size();
        boolean shouldSetNewRewardsIfStreakLost = currentStreak >= 4;

        if ((isNextOverRewardsBoundary && daysBetween == 1) || daysBetween > 1) {
            String serverMessage = isNextOverRewardsBoundary && daysBetween == 1
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
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Reset daily reward streak for player " + player.getUUID() + " due to " + serverMessage);

            if (isNextOverRewardsBoundary && daysBetween == 1) {
                SoundType.DAILY_REWARD_RESET_STREAK.play(player, false);
                RuneQuestPlugin.getDailyRewardHandler().assignNewDailyRewards(this);
            } else {
                if (shouldSetNewRewardsIfStreakLost) {
                    RuneQuestPlugin.getDailyRewardHandler().assignNewDailyRewards(this);
                }
            }

            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                Player bukkitPlayer = player.asBukkitPlayer();

                if (bukkitPlayer == null) {
                    return;
                }

                if (isNextOverRewardsBoundary && daysBetween == 1) {
                    HudService.sendInfoMessage(player, Component.text("Your daily reward streak has been reset because you have exceeded the maximum streak."));

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
