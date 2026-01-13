package org.zuma.examplePlugin.domain.daily;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.daily.persistence.DailyRewardRepository;
import org.zuma.examplePlugin.domain.droptable.DropTable;
import org.zuma.examplePlugin.domain.droptable.DropTableEntry;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.territory.Territory;
import org.zuma.examplePlugin.events.drops.DailyMegaRareClaimEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DailyRewardHandler {
    private final static long NOTIFY_PERIOD_MS = 1000L * 60 * 10; // 10 minutes;
    private static long notifiedTimes;
    private final DailyRewardRepository repository;
    private final Map<Integer, DropTable> rewardPool;

    public DailyRewardHandler(DailyRewardRepository repository, List<DropTable> dailyRewards) {
        this.repository = repository;
        this.rewardPool = IntStream.range(0, dailyRewards.size()).boxed().collect(Collectors.toMap(i -> i + 1, dailyRewards::get));
        DailyRewardHandler.notifiedTimes = 0;

        if (dailyRewards.size() != 15) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Daily rewards pool does not contain 15 entries (2 weeks + grand prize)! Current entries: " + dailyRewards.size());
            throw new IllegalArgumentException("Daily rewards pool must contain 15 entries!");
        }

        startLoop();
    }

    private void startLoop() {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), () -> {
            RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(player -> {
                DailyRewards dailyRewards = player.getDailyRewards();
                if (dailyRewards == null) {
                    return;
                }

                @Nullable Territory currentTerritory = RuneQuestPlugin.getTerritoryHandler().getPlayersTerritory(player);

                if (currentTerritory != null && currentTerritory.getId().equals("tutorial-island")) {
                    // Do not notify on tutorial island
                    return;
                }

                if (dailyRewards.canClaimToday()) {
                    sendReminder(player);
                }

                // Update their daily rewards data every hour while they are online and unclaimed
                if (notifiedTimes % 6 == 0) {
                    player.getDailyRewards().updateIfNeeded(player);
                }

            });

            notifiedTimes++;
        }, 0L, NOTIFY_PERIOD_MS / 50 /* Convert ms to ticks */);
    }

    public void handleMegaRareClaim(DailyMegaRareClaimEvent.Payload payload) {
        RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(player -> {
            HudService.sendAnnouncementMessage(player, Component.text(payload.playerName + " has just claimed a mega rare daily reward ", Color.WARNING.asColor())
                    .append(payload.tier.getComponent())
                    .append(Component.text(" " + payload.amount + "x ", Color.ACCENT.asColor()))
                    .append(Component.text(payload.itemName, TextColor.fromHexString(payload.tier.getHexColor())))
                    .append(Component.text("!", Color.ACCENT.asColor())));
        });

    }

    public void sendReminder(RunePlayer player) {
        DailyRewardEntry todayReward = player.getDailyRewards().getRewardEntries().get(player.getDailyRewards().getCurrentStreak());

        if (todayReward == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Daily reward entry not found for streak day: " + player.getDailyRewards().getCurrentStreak() + " for player: " + player.asBukkitPlayer().getName());
            return;
        }

        AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(todayReward.itemId());

        if (itemType == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Daily reward item type not found for id: " + todayReward.itemId() + " for player: " + player.asBukkitPlayer().getName());
            return;
        }

        HudService.sendSuccessMessage(player, Component.text("Your daily reward (", Color.SUCCESS.asColor())
                .append(Component.text(todayReward.min() + "x - " + todayReward.max() + "x " + itemType.displayName, Color.ACCENT.asColor())
                        .append(Component.text(") is waiting to be claimed!", Color.SUCCESS.asColor()))));
        SoundType.NOTIFICATION.play(player, false);
    }

    public void savePlayerDailyRewardData(RunePlayer player, boolean async) {
        DailyRewards dailyRewards = player.getDailyRewards();

        if (dailyRewards == null) {
            return;
        }

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
                repository.upsertRewardData(player.getUUID(), dailyRewards);
            });
            return;
        }
        repository.upsertRewardData(player.getUUID(), dailyRewards);

    }

    public DailyRewards getPlayerDailyRewardDataSync(UUID uuid) {
        return this.repository.getByUUID(uuid);
    }

    public void assignNewDailyRewards(DailyRewards dailyRewards) {
        dailyRewards.getRewardEntries().clear();

        rewardPool.forEach((index, dropTable) -> {
            DropTableEntry randomEntry = null;

            while (randomEntry == null) {
                for (DropTableEntry drop : dropTable.getDrops()) {
                    boolean isMatch = PlayerUtils.getRandom().nextInt(drop.oneInNChance) == 0;

                    if (!isMatch || randomEntry != null) {
                        continue;
                    }

                    randomEntry = drop;
                }
            }

            dailyRewards.getRewardEntries().put(index, new DailyRewardEntry(randomEntry.id, randomEntry.min, randomEntry.max));
        });
    }
}
